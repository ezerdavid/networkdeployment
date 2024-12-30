## How to run
Project uses Java 21.<br>
The service can be started from command line with command `mvn spring-boot:run` or using wrapper `./mvnw spring-boot:run`
## API
By default, the server run on port `8080`
### Example requests
#### POST /devices
```shell
curl --request POST \
  --url http://localhost:8080/devices \
  --header 'content-type: application/json' \
  --data '{
  "deviceType": "Gateway",
  "macAddress": "00-B0-D0-63-C2-52",
  "upLinkMacAddress": null
}'
```
#### GET /devices
```shell
curl --request GET \
  --url http://localhost:8080/devices
```
#### GET /devices/{macAddress}
```shell
curl --request GET \
  --url http://localhost:8080/devices/00-B0-D0-63-C2-52
```
#### GET /devices/topology
```shell
curl --request GET \
  --url http://localhost:8080/devices/topology
```
#### Get /devices/topology/{macAddress}
```shell
curl --request GET \
  --url http://localhost:8080/devices/topology/00-B0-D0-63-C2-52
```
# Databse
Using in-memory h2 database. The DB console can be accessed here http://localhost:8080/h2-console/

```
JDBC_URL: jdbc:h2:file:./nddb
username: sa
password: password 
```