import axios from 'axios';

export const HTTP = axios.create({
    baseURL: `http://129.21.70.129:8081/`,
    headers: {
        Authorization: 'Bearer {token}'
    }
})