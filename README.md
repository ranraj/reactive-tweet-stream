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


> :warning: ** This appliation clears previous execution data from configured database on every new run. Please keep your data backup, incase if it is valueable to you.

Login into mongo shell and execute bellow commands to view tweets data.

```
use tweetstream;
db.find.tweets();
```
### Configure Twitter oauth properties

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
