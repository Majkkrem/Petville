import axios from "axios";

export const api = await axios.create({
    baseURL: "http://localhost:3000/",
    headers: {
        "Content-Type": "application/json",
    },
    withCredentials: true,});
