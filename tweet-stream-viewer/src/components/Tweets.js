import React, { Component } from 'react';
import Tweet from '../components/Tweet.js'

const TWEET_LIVE_URL = process.env.TWEET_LIVE_URL || "http://localhost:8080/stream/tweets";

class Tweets extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: []
        };
        this.eventSource = new EventSource(TWEET_LIVE_URL);
    }
    componentDidMount() {
        this.eventSource.onmessage = e =>
            this.updateTweets(JSON.parse(e.data));
        this.eventSource.onerror = e =>
            this.updateTweets(JSON.parse(e));
    }

    updateTweets(tweets) {
        console.log(tweets)
        let newData = [tweets, ...this.state.data]        
        this.setState(Object.assign({}, { data: newData.slice(0,20) }));
    }
    render() {
        const renderTweet = tweet => <Tweet key={tweet.id} content={tweet}></Tweet>;
        return (
            <div className="card">
                <div className="card-body">
                    {this.state.data.map(renderTweet)}
                </div>
            </div>
        );
    }
}

export default Tweets;