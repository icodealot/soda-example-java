# Cache JSON Responses Using SODA

This is an example of using the SODA endpoint included with ORDS to cache 
JSON responses in the database. For example, this could be used with NOAA
(`api.weather.gov`) `points` responses that are used repeatedly, but for 
which values do not change from one request to the next. You could build
a weather application and store your data in JSON collections.

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