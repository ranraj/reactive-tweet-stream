import React from 'react';
import Moment from 'moment';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faExternalLinkAlt } from '@fortawesome/free-solid-svg-icons'

const Tweet = props => {
  const tweet = props.content;
  const createdDate = new Date(tweet.created_at);
  console.log(tweet.created_at);
  console.log(createdDate);
  const colorMap = {
    "VeryPositive": "very_possitive",
    "Positive": "possitive",
    "Neutral": "neutral",
    "Negative": "negative",
    "VeryNegative": "very_negative"
  }
  let boardColor = colorMap["Neutral"];
  const color = colorMap[tweet.sentiment.sentiment_type];

  if (color !== "") {
    boardColor = color;
  }


  return (
    <div className={"card row " + boardColor}>

      <li className={"tweet" + (tweet.active ? ' active' : '')}>
        <img className="avatar" src={tweet.user.profile_image_url}></img>
        <blockquote>
          <cite>
            <a href={"http://www.twitter.com/" + tweet.user.screen_name}>{tweet.user.name}</a>
            <span className="screen-name">| @{tweet.user.screen_name} |</span>
          </cite>
          <small> {Moment(tweet.created_at).format('DD MMM HH:mm:ss A')} </small>
          <div className="card-body"><span className="content">{tweet.text}</span></div>
           
          <small >
            <a className="goto" href={"https://twitter.com/" + tweet.user.screen_name + "/status/" + tweet.id}><FontAwesomeIcon className={boardColor} icon={faExternalLinkAlt} /></a>
            &nbsp; {tweet.sentiment.sentiment_type}
          </small>
        </blockquote>
      </li>
    </div>
  );
}
export default Tweet;