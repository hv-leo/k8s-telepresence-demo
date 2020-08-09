import React, { useState, useEffect } from 'react';
import axios from 'axios';
import logo from './logo.svg';
import './App.css';

const App = () => {
  const [message, setMessage] = useState(undefined);
  useEffect(() => {
    axios.get('http://localhost/server')
        .then(response => setMessage(response.data));
  }, []);

  const [services, setServices] = useState( );
  const listServices = ( ) => {
    axios.get('http://localhost/server/services')
        .then(response => setServices(response.data));
  }

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p> Front-end: Hi buddy, I'm at 🌌! </p>
        <p> Back-end: {message} </p>
        <button type="button" onClick={listServices}>
            List k8s services!
        </button>
        <ul dangerouslySetInnerHTML={{__html: services}} />
      </header>
    </div>
  );
};

export default App;
