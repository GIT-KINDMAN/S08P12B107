import axios from 'axios';

// const BASE_URL = 'https://i8b107.p.ssafy.io/api'


const api = axios.create({
    // baseURL: BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
    withCredentials: true
});


api.interceptors.request.use(function (config) {
    const accessToken = localStorage.getItem('userToken');
    if (!accessToken) {
        window.location.href = '/login';
        return
    }
    config.headers.authorization = `${accessToken}`;
    
    return config;
});

// api.interceptors.response.use(
//     function (response) {
//         return response;
//     },
//     async function (error) {
//         const { config, response } = error;
//         const originalRequest = config;

//         if (response && response.data.error.code === 'ACCESS_TOKEN_EXPIRED') {
//             await axios
//                 .post(
//                     `${BASE_URL}/refresh`,
//                 )
//                 .then(res => {
//                     if (res.data === 'REFRESH_SUCCEED') {
//                         const newAccessToken = res.headers.authorization;

//                         originalRequest.headers.authorization = newAccessToken;
//                         localStorage.setItem('accessToken', newAccessToken);

//                         return axios(originalRequest);
//                     }
//                 })
//                 .catch(err => {
//                     if ( err.response.data.error.code === 'REFRESH_TOKEN_EXPIRED' ) {
//                         localStorage.removeItem('accessToken');
//                         window.location.replace = '/login';
//                     }
//                 });
//         }
        
//         return Promise.reject(error);
//     },
// );

export default api
