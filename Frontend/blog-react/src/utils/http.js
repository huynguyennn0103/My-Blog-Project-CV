import axios from "axios"
const baseURL = process.env.REACT_APP_backend_URL

export const request = async (method, url, data, isAuthenticate = false, headers = {}) => {
    let res = null;
    if(!isAuthenticate){
        res = await axios.request({
            method,
            url,
            baseURL,
            data,
            headers: {
                ...headers,
                'x-api-key' : 'huynguyenn0103'
            }
        })
    } else {
        const token = localStorage.getItem('token')
        res = await axios.request({
            method,
            url,
            baseURL,
            data,
            headers:{
                ...headers,
                'Authorization': `Bearer ` + JSON.parse(token),
                'x-api-key' : 'huynguyenn0103'
            }
        })
    }
    return res;
}

