# CalorieTrackerAPI
CalorieTrackerAPI is the backend for an application help you track your daily calorie intake.

## Running the API
To run the application you need a runnin ginstance of mongodb. When using docker you can start an instance of mongodb and mongo-express with the docker-compose.yml file by running the 

```bash
docker-compose up -d
```

command in the calorie-tracker-api folder. Running the docker-compose.yml file will start a mongodb instance on port:27017 and start mongo-express on port:8081. When opening http://localhost:8081/ you'll be asked to enter a username and password. The defaut values in the docker-compose.yml are username: admin, and password:admin123.

### Creating a database user
to interact with the mongodb instance you have to create a database and a database user the application can use. To create a database log into your mongodb instance and run the use command

```bash
use calorie-tracker-api
```

To create a database user create a new user within the admin database.

```bash
use admin

db.createUser(
    {
        user: "cta_user",
        pwd:  "test",
        roles:[
            {role: "userAdmin" , db:"calorie-tracker-api"},
            {role: "readWrite" , db:"calorie-tracker-api"},
            ]
    }
)
```

after setting up the database and user make sure to set the applicationsproperties correctly

```
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.authentication-database = admin
spring.data.mongodb.database=calorie-tracker-api
spring.data.mongodb.username=cta_user
spring.data.mongodb.password=test
```

## Running the Appliation
To run the Application you can use the run-local.sh script in the calorie-tracker-api folder.

```bash
./run-local.sh
```

whis script will run the application on localhost:8080 