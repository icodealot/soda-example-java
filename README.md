# Example using NOAA with SODA for REST

This is an example of using the Simple Oracle Document Access (SODA) with
Oracle REST Data Services (ORDS) to cache JSON responses in the database.

In the example SODA is used to cache JSON responses from weather.gov. Data
from (`api.weather.gov`) `points` responses are used repeatedly, but this
data does not change from one request to the next. With SODA you could build
a full weather application or simply incorporate transient JSON payloads
into your application.

Note, in Database Actions you can find SODA in `JSON`. When you create a
new collection under JSON, you are already using SODA!

You can learn more here:

- SODA: https://docs.oracle.com/en/cloud/paas/autonomous-json-database/ajdug/ords-overview-using-soda-rest.html
- ORDS: https://oracle.com/rest
- NOAA: https://www.weather.gov/documentation/services-web-api

## Setup

This project assumes you have Internet access, `maven`, and `JDK17+`. This 
also assumes you have an Autonomous Database setup with a schema that has 
web / REST access as well as basic authentication enabled. This is just an 
example so many shortcuts were taken to focus on using JSON collections.

1. Create a JSON collection called `points` in ADB and ensure your user can talk to SODA
```
curl --user YOURSCHEMA:YOURSECRET -X GET https://your.adb.us-phoenix-1.oraclecloudapps.com/ords/yourschema/soda/latest
```
2. Clone the repo
3. Update `Main.java` with the correct SODA URL and authentication details
```
... 
SODA_ENDPOINT_POINTS = "https://your.adb.us-phoenix-1.oraclecloudapps.com/ords/yourschema/soda/latest/points";
SODA_AUTH_HEADER = "Basic yourauthdetailsbase64encoded==";
```
4. Run `mvn clean install` -or- run `Main.main()` in your IDE to test

Note, base64 encoding is not encryption. It works suitably well with HTTP `Basic auth`
but you should not rely on base64 encoded credentials to be secure. If you are new to
base64 here are some tips for generating a suitable string to place in `Main.java`.

### On macOS: (using bash)
```
$ printf 'yourschema:yourpassword' | base64
```

### On Windows: (using powershell)
```
> [convert]::ToBase64String([System.Text.Encoding]::UTF8.GetBytes("yourschema:yourpassword"))
```

If these don't work for you, or if you can't find the tools on your operating
system, or need more ideas a search engine should be your next stop. (Search
`how to encode base64 strings on xyz os` or similar)


## Testing

If all goes well hopefully you will see something similar to this:

```
> mvn clean install
...
> java -jar target/SampleJsonCacheInADB-1.0-SNAPSHOT.jar
Point stored in SODA:
        https://your.adb.us-phoenix-1.oraclecloudapps.com/ords/yourschema/soda/latest/points/734BB2EBED844B7A99419F35AB5E2A3C
Point retrieved from SODA:
        {"geometry":"POINT(-87.685 41.837)","cwa":"LOT","forecastOffice":"https://api.weather.gov/offices/LOT","gridId":"LOT","gridX":73,"gridY":70,"forecast":"https://api.weather.gov/gridpoints/LOT/73,70/forecast","forecastHourly":"https://api.weather.gov/gridpoints/LOT/73,70/forecast/hourly","observationStations":"https://api.weather.gov/gridpoints/LOT/73,70/stations","forecastGridData":"https://api.weather.gov/gridpoints/LOT/73,70","forecastZone":"https://api.weather.gov/zones/forecast/ILZ104","county":"https://api.weather.gov/zones/county/ILC031","fireWeatherZone":"https://api.weather.gov/zones/fire/ILZ014","timeZone":"America/Chicago","radarStation":"KLOT","@id":"https://api.weather.gov/points/41.837,-87.685","@type":"wx:Point"}
Point deleted from SODA:
        https://your.adb.us-phoenix-1.oraclecloudapps.com/ords/yourschema/soda/latest/points/734BB2EBED844B7A99419F35AB5E2A3C
```
