## This Application is reference model for processing live twitter stream data using Spring boot & ReactJS application.

Check out this repository, 
This has two project one is server (analyser) module developed using Springboot and UI module (viewer) developed using ReactJS

| Sever Application Tech stack |
|---|
| Spring boot|
| Webflux |
| Maven 3+|
| MongoDB |
| Java 8+ |

| Client Application tech statck |
|---|
| ReactJS | 
| npm |


Recommendation to clear previous data from the Mongo DB data everytime.

Login into mongo shell and execute bellow commands.

```
use tweetstream;
db.dropDatabase();
db.createCollection("tweets", {capped:true, max:1500, size:1000000});
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
http://localhost:3000/
```
### Application sample screenshot
![Tweet stream app](http://ranraj.github.io/app_screenshots/covid_tweet_analyser.png)
