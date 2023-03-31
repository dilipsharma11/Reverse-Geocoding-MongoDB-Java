// Import the 'fs' module
const fs = require('fs');

// Use destructuring assignment to import only the 'map()' function from underscore library 
const { map } = require('underscore');

// Get the filename from command line arguments
const filename = process.argv[2];

// If the filename is not provided, output an error message and exit the program
if (!filename) {
  console.log('File not found');
  process.exit(1);
}

// Read the file content asynchronously using the Promise API and async/await
const readCitiesFromFile = async (filePath) => {

  // Use await with the 'readFile()' method of the 'promises' object in the 'fs' module 
  const data = await fs.promises.readFile(filePath, 'utf-8');

  // Split the data into an array of strings representing each city, filter out any empty cities, and parse each valid string into a city object.
  return data.split('\n').filter(city => city.trim().length > 0).map(city => {
      const [id, country, region, names, latitude, longitude] = city.split('\t');
      const name = names.split(',')[0];
      return { id, name, country, region, coordinates: [Number(latitude), Number(longitude)] };
    });
};

// Use try/catch block to handle any errors that might occur while reading the file
(async () => {
  try {
    const cities = await readCitiesFromFile(filename);
    const outputData = { gps: cities };
    fs.writeFileSync('RU.json', JSON.stringify(outputData));
  } catch (error) {
    console.error(error);
  }
})();