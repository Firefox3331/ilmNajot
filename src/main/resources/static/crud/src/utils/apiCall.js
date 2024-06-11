import axios from "axios";


export function apiCall(url, method, data, headers = {Authorization: localStorage.getItem('token')}){
    return axios({
        baseURL: "http://localhost:8080",
        url,
        method,
        data,
        headers
    })
}