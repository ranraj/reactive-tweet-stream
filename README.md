## This Application is demo project for processing live twitter stream data using Spring boot & ReactJS application.

Check out this repository, 
This has two project one is server (analyser) module developed using Springboot and UI module (viewer) developed using ReactJS

| Sever Application Tech stack |
|---|
| Spring boot|
| Webflux |
| Maven 3+|
| MongoDB |
| Java 8+ |
| Stanford corenlp|

| Client Application tech statck |
|---|
| ReactJS | 
| npm |

### Configure Mongo DB collection

This tweets table is capped collection with limited size, this is effective when handling infinite stream of data.
> :warning: Mongo db overwrites the old document, when it find no space for new document. This is the default behaviour for capped collection. Make nessary changes, if you need the persisted data useful.
Recommended to clear previous collected data on every run, this would provide more readablity.


Login into mongo shell and execute bellow commands to setup collection.

```
use tweetstream;
db.dropDatabase();
db.createCollection("tweets", {capped:true, max:1500, size:1000000});
db.find.tweets();
```


### Configure Twitter oAuth properties

- Login to https://apps.twitter.com
- Create a New Application and note down the Consumer Key, Consumer Secret, Access Token and Access Token Secret.
- Edit the /src/main/resources/application-secret.properties and add values to below properties.
```
twitter.consumer-key=
twitter.consumer-secret=
twitter.token=
twitter.secret=
```


### Spring boot server Application
Compile
```
cd tweet-stream-analyser
mvn install
```
Run
```
java -jar tweet-stream-analyser-application/target/tweet-stream-analyser-application-0.0.1-SNAPSHOT.jar "Covid19"
```

### ReactJS client Application 
Install
```
cd tweet-stream-viewer/
npm install
```
Run
```
npm start
```
This above command automatically start the browser to render the application, incase that is not working use below default url,
```
(http://localhost:3000/)[http://localhost:3000/]
```
### Application sample screenshot
![Tweet stream app](http://ranraj.github.io/app_screenshots/covid_tweet_analyser_medium.png)

### APIs
|Name| findAllTweets |
|---|---|
|URL | http://localhost:8080/tweets|
|Method | GET |
|Content-Type | application/json|
|Notes|| 
|Name| findAllTweets (event stream) |
|---|---|
|URL | http://localhost:8080/stream/tweets|
|Method | GET |
|Content-Type | text/evet-stream|
|Notes|Infinite stream of Server Sent Events using tailable cursor to find Tweets collection|