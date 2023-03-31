package com.turvo.document.heremaps.utils;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;
import com.turvo.document.heremaps.model.GeoPointAddressMap;
import com.turvo.map.MapUtils;
import com.turvo.map.dto.Address;
import com.turvo.map.dto.LatLong;

@Component
public class QueryingHereMaps {
	
    @Autowired
    private MapUtils hereMapUtils;

    private DBCollection collection;

   //@Bean
    public void QueryMongoForlatlong(String CountryFileName) throws IOException, InterruptedException {

       MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));
       DB database = client.getDB("turvo");
       collection = database.getCollection("turvoGeoPointsEurope");

      // List<String> filenames = GetAllFileNamesInTheFolder();
      // for(String file : filenames) {
           Gson gson = new Gson();
           Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/GeonamesFiles/"+CountryFileName));
           System.out.println("Importing data for :"+CountryFileName);
           Map<String, Object> CoordinatesHashMap = gson.fromJson(reader, Map.class);
           List<LinkedTreeMap> gpsList = (List<LinkedTreeMap>) CoordinatesHashMap.get("gps");
           System.out.println("Size of the geonames dataset is :" + gpsList.size());
           for (LinkedTreeMap coordinates : gpsList) {
               List<Double> latLon = (List<Double>) coordinates.get("coordinates");
               Double lon = latLon.get(0);
               Double lat = latLon.get(1);
               GetHereMapsAddress(lat, lon);
           }

    }

    void GetHereMapsAddress(double lat, double lon) throws InterruptedException {
        Thread.sleep(20);
        try{
            Address address = hereMapUtils.getAddress(new LatLong(lat,lon));
            GeoPointAddressMap addressMap = convertHereMapAddress(address, lat, lon);
            StoreAddressToDB(addressMap);
        }
        catch (Exception e){
                e.printStackTrace();
            }
    }

    public GeoPointAddressMap convertHereMapAddress(Address address, double lat, double lon)
    {
       GeoPointAddressMap AddressMap = new GeoPointAddressMap();

          AddressMap.getGeoname_gps().getCoordinates().add(lon);
          AddressMap.getGeoname_gps().getCoordinates().add(lat);
          AddressMap.getGeoname_gps().setType("Point");

          AddressMap.getAddress().setState(address.getState());
          AddressMap.getAddress().setCountry(address.getCountry());
          AddressMap.getAddress().setLabel(address.getName());
          AddressMap.getAddress().setPostalCode(address.getPostalCode());
          AddressMap.getAddress().setCity(address.getCity());
          AddressMap.getAddress().setCounty(address.getCounty());

          AddressMap.getGps().getCoordinates().add(lon);
          AddressMap.getGps().getCoordinates().add(lat);
          AddressMap.getGps().setType("Point");

          return AddressMap;
    }


    private void StoreAddressToDB(GeoPointAddressMap addressMap)
    {
        Gson gson = new Gson();
        String json = gson.toJson(addressMap);
        DBObject dbObject = (DBObject) JSON.parse(json);
        collection.insert(dbObject);

    }

    public List<String> GetAllFileNamesInTheFolder() {
        List<String> results = new ArrayList<String>();

        File[] files = new File("src/main/resources/GeonamesFiles").listFiles();

        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }
        return results;
    }
}
