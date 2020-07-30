import React from 'react';
import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.scss';

import Tweets from './components/Tweets.js' 
function App() {
  
  return (
    <div className="App">
      <Tweets/>             
    </div>
  );
}

export default App;
