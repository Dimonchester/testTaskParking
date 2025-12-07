import axios from "axios";

const CAR_API_BASE_URL = "http://localhost:8080/api/cars"

class CarService{
    getCars(){
        return axios.get(CAR_API_BASE_URL);
    }
}

export default new CarService();