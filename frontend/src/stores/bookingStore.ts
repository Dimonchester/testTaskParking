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
}

export interface NewBooking {
    idCar: number | null;
    idSpot: number | null;
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
        await axios.put(`${API_BOOKING_URL}/${id}/pay`, { getPaid: !currentStatus });
        ElMessage.success(currentStatus ? 'Оплата отменена' : 'Оплата принята');
        const item = bookings.value.find(b => b.id === id);
            if (item) item.isPaid = !currentStatus;
    };

    const deleteBooking = async (id:number) =>{
        try {
        await axios.delete(`${API_BOOKING_URL}/${id}`); 
        ElMessage.success('Бронирование успешно удалено.');
        await fetchBookings();
    } catch (e) {
        console.error('Ошибка при удалении бронирования:', e);
        ElMessage.error('Не удалось удалить бронирование.');
    }
    };

    return { bookings, loading, fetchBookings, createBooking, togglePayment, deleteBooking };
})