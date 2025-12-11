import { defineStore } from 'pinia';
import axios from 'axios';
import { ref } from 'vue';

export interface Owner {
    id?: number;
    firstName: string;
    lastName: string;
    middleName: string;
    phoneNumber: string;
}

export interface Car {
    id?: number;
    carNumber: string;
    brand: string;
    ownerFio: string;
    idOwner: number;
}

export interface Spot {
    id?: number;
    spotNumber: number;
    isAvailable: boolean;
}

const API_BASE_URL = 'http://localhost:8080/api';

export const useManagementStore = defineStore('management', () => {
    const owners = ref<Owner[]>([]);
    const cars = ref<Car[]>([]);
    const spots = ref<Spot[]>([]);
    const loading = ref(false);

    const fetchOwners = async (query: string = '') => {
        loading.value = true;
        const response = await axios.get(`${API_BASE_URL}/owners`, { params: { query } });
        owners.value = response.data;
        loading.value = false;
    };

    const saveOwner = async (owner: Owner) => {
        if (owner.id) {
            await axios.put(`${API_BASE_URL}/owners/${owner.id}`, owner);
        } else {
            await axios.post(`${API_BASE_URL}/owners`, owner);
        }
        await fetchOwners();
    };

    const deleteOwner = async (id: number) => {
        await axios.delete(`${API_BASE_URL}/owners/${id}`);
        await fetchOwners();
    };

    const fetchCars = async (carNumber: string = '') => {
        loading.value = true;
        const response = await axios.get(`${API_BASE_URL}/cars`, { params: { carNumber } });
        cars.value = response.data;
        loading.value = false;
    };

    const saveCar = async (car: Car) => {
        if (car.id) {
            await axios.put(`${API_BASE_URL}/cars/${car.id}`, car);
        } else {
            await axios.post(`${API_BASE_URL}/cars`, car);
        }
        await fetchCars();
    };

    const deleteCar = async (id: number) => {
        await axios.delete(`${API_BASE_URL}/cars/${id}`);
        await fetchCars();
    };
    
    const fetchSpots = async () => {
        loading.value = true;
        const response = await axios.get(`${API_BASE_URL}/spots`);
        spots.value = response.data;
        loading.value = false;
    };

    const saveSpot = async (spot: Spot) => {
        if (spot.id) {
            await axios.put(`${API_BASE_URL}/spots/${spot.id}`, spot);
        } else {
            await axios.post(`${API_BASE_URL}/spots`, spot);
        }
        await fetchSpots();
    };

    const updateSpotStatus = async (spot: Spot) => {
        try {
            await axios.put(`${API_BASE_URL}/spots/${spot.id}`, spot);
        } catch (error) {
            console.error('Ошибка при обновлении статуса места:', error);
            throw error;
        }
    };

    const deleteSpot = async (id: number) => {
        await axios.delete(`${API_BASE_URL}/spots/${id}`);
        await fetchSpots();
    };

    return {
        owners, cars, spots, loading,
        fetchOwners, saveOwner, deleteOwner,
        fetchCars, saveCar, deleteCar,
        fetchSpots, saveSpot, deleteSpot, updateSpotStatus,
    };
})