package com.turvo.document.heremaps;

import com.turvo.document.heremaps.utils.QueryingHereMaps;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.turvo.map.MapUtils;
import com.turvo.map.here.HereMapUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@SpringBootApplication
public class HereMapsApplication {

	private static final int noOfThreads=2;
	public static int count=0;
	private static ApplicationContext applicationContext;

	private static ExecutorService executorService = new ThreadPoolExecutor(2, 2, 50L, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>());

	@Autowired
	private static QueryingHereMaps queryingHereMaps;

	private static List<String> CountryFilenames = new ArrayList<>();


	@Bean
	public MapUtils hereMapUtils() {
		return new HereMapUtils("xxx", "xxx");
	} // put your appId and appCode here

	public static void main(String[] args) throws IOException, InterruptedException {

		applicationContext = SpringApplication.run(HereMapsApplication.class, args);
		function();
	}

	public static void function(){

		queryingHereMaps = applicationContext.getBean(QueryingHereMaps.class);
		CountryFilenames = queryingHereMaps.GetAllFileNamesInTheFolder();
		executorService = Executors.newFixedThreadPool(noOfThreads);

		Runnable runnableTask = () -> {
			try {
				queryingHereMaps.QueryMongoForlatlong(CountryFilenames.get(count++));
				if (Thread.interrupted()) {
					// Executor has probably asked us to stop

				}
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();

			}
		};


		while(count<noOfThreads) {
			executorService.execute(runnableTask);
		}
		executorService.shutdown();
		try {
			if (!executorService.awaitTermination(6, TimeUnit.SECONDS)) {
				executorService.shutdownNow();
			}
		} catch (InterruptedException ex) {
			executorService.shutdownNow();
			Thread.currentThread().interrupt();
		}

	}

}
