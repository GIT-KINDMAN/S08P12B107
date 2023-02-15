import { Route, Routes } from "react-router-dom";
import Register from "pages/Register";
import logo from './logo.ico'
import 'App.css';
import Layout from 'pages/Layout';
import AuthLayout from 'pages/AuthLayout';
import Home from 'pages/Home';
import MyPage from 'pages/MyPage'
import Cash from 'pages/Cash'
import Map from 'pages/Map'
import Login from 'pages/Login';
import ChangeAccount from 'pages/ChangeAccount';
// import VideoChat from "pages/VideoChat";
import RegistrationDetail from "pages/RegistrationDetail";
import HangUp from "pages/HangUp"
import React, { lazy, Suspense, useState } from 'react';
import { fetchToken, onMessageListener } from './api/firebase';
import { Toast } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';


const VideoChat = lazy(() => import('pages/VideoChat'));

function App() {

  const [show, setShow] = useState(false);
  const [notification, setNotification] = useState({title: '', body: ''});
  const [isTokenFound, setTokenFound] = useState(false);
  // 

  onMessageListener().then(payload => {
    setNotification({title: payload.notification.title, body: payload.notification.body})
    setShow(true);
    console.log(payload);
  }).catch(err => console.log('failed: ', err));

  return (
    <div>
      <Routes>
          <Route element={<Layout />}>
            <Route path="/" element={<Home />}/>
            <Route path="/mypage" element={<MyPage />} />
            <Route path="/cash" element={<Cash />} />
            <Route path="/change-account" element={<ChangeAccount/>} />
            <Route path="/registration-detail" element={<RegistrationDetail />} />
            <Route path="/map" element={<Map />} />
            <Route path="/room/:roomName/:idx" element={<Suspense fallback={<div><h1>렌더링 중입니다!!!</h1></div>}><VideoChat /></Suspense>} />
            <Route path="/hangup" element={<HangUp />} />
          </Route>
          
          <Route element={<AuthLayout />}>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
          </Route>
      </Routes>
      <Toast onClose={() => setShow(false)} show={show} delay={3000} autohide animation style={{
                position: 'absolute',
                top: 20,
                right: 20,
                minWidth: 200
              }}>
                <Toast.Header>
                  <strong className="mr-auto">{notification.title}</strong>
                  <small>just now</small>
                </Toast.Header>
                <Toast.Body>{notification.body}</Toast.Body>
      </Toast>
    </div>

  );
}

export default App;
