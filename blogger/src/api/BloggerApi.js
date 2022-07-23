import axios from "axios";

export default axios.create({
    baseURL: "http://172.31.159.191:8080/"
});