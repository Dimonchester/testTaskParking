import { defineStore } from 'pinia';
import axios from 'axios';
import { ref } from 'vue';
import { ElMessage } from 'element-plus';

export interface BookingView {
    id: number;
    carNumber: string;
    carBrand: string;
    ownerFio: string;
    spotNumber: number;
    startDate: string;
    endDate: string;
    isPaid: boolean;
    isActive: boolean;
}

export interface NewBooking {
    carId: number | null;
    spotId: number | null;
    endDate: string;
}

const API_BOOKING_URL = 'http://localhost:8080/api/bookings';

export const useBookingStore = defineStore('booking', () => {
    const bookings = ref<BookingView[]>([]);
    const loading = ref(false);

    const fetchBookings = async (searchCar: string = '', searchName: string = '') => {
        loading.value = true;
            const response = await axios.get(API_BOOKING_URL, {
                params: { carNumber: searchCar, ownerName: searchName }
        });
        bookings.value = response.data;
        loading.value = false;
    };

    const createBooking = async (booking: NewBooking) => {
        await axios.post(API_BOOKING_URL, booking);
        ElMessage.success('Место успешно забронировано');
        await fetchBookings();
    };

    const togglePayment = async (id: number, currentStatus: boolean) => {
        await axios.put(`${API_BOOKING_URL}/${id}/pay`, { isPaid: !currentStatus });
        ElMessage.success(currentStatus ? 'Оплата отменена' : 'Оплата принята');
        const item = bookings.value.find(b => b.id === id);
            if (item) item.isPaid = !currentStatus;
    };

    const releaseSpot = async (id: number) => {
        await axios.put(`${API_BOOKING_URL}/${id}/complete`);
        ElMessage.success('Парковка завершена, место свободно');
        await fetchBookings();
    };

    return { bookings, loading, fetchBookings, createBooking, togglePayment, releaseSpot };
})