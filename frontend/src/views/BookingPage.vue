<template>
  <div class="booking-page">
    <el-card shadow="never">
      <template #header>
        <div class="page-header">
          <h2>Управление парковкой (Бронирование)</h2>
          <el-button type="success" size="large" @click="openCreateDialog">
             + Занять место
          </el-button>
        </div>
      </template>

      <div class="filters">
        <el-input
          v-model="searchCar"
          placeholder="Поиск по номеру авто..."
          prefix-icon="Search"
          style="width: 250px"
          clearable
          @input="handleSearch"
        />
        <el-input
          v-model="searchName"
          placeholder="Поиск по ФИО владельца..."
          prefix-icon="User"
          style="width: 250px"
          clearable
          @input="handleSearch"
        />
      </div>

      <el-table 
        :data="paginatedBookings" 
        v-loading="bookingStore.loading" 
        style="width: 100%; margin-top: 20px"
        stripe
        border
        :row-key="(row) => row.id"
      >
        <el-table-column label="Автомобиль" width="220">
          <template #default="{ row }">
            <strong>{{ row.carNumber }}</strong><br/>
            <span style="color: gray; font-size: 12px">{{ row.carBrand }}</span>
          </template>
        </el-table-column>

        <el-table-column prop="ownerFio" label="Владелец" min-width="180" />
        
        <el-table-column label="Место" width="100" align="center">
            <template #default="{ row }">
                <el-tag effect="dark" size="large">{{ row.spotNumber }}</el-tag>
            </template>
        </el-table-column>

        <el-table-column label="Период" width="200">
          <template #default="{ row }">
             <div>C: {{ formatDate(row.startDate) }}</div>
             <div>До: {{ formatDate(row.endDate) }}</div>
          </template>
        </el-table-column>

        <el-table-column label="Оплата" width="150" align="center">
          <template #default="{ row }">
             <el-switch
                v-model="row.isPaid"
                active-text="Да"
                inactive-text="Нет"
                inline-prompt
                style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                @change="() => handlePaymentChange(row)" 
             />
          </template>
        </el-table-column>

        <el-table-column label="Действия" width="150" align="center">
            <template #default="{ row }">
                <el-popconfirm 
                    title="Завершить парковку и освободить место?"
                    confirm-button-text="Да"
                    cancel-button-text="Отмена"
                    @confirm="bookingStore.deleteBooking(row.id)"
                >
                    <template #reference>
                        <el-button type="danger" plain size="small">Освободить</el-button>
                    </template>
                </el-popconfirm>
            </template>
        </el-table-column>
      </el-table>

    <div class="pagination-container">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="totalBookings"
        :disabled="bookingStore.loading"
        layout="prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    </el-card>

    <el-dialog v-model="dialogVisible" title="Новое бронирование" width="500px">
        <el-form label-position="top">
            <el-form-item label="Выберите автомобиль">
                <el-select 
                    v-model="newBooking.idCar" 
                    placeholder="Поиск по номеру..." 
                    filterable 
                    remote
                    :remote-method="searchCarsRemote"
                    :loading="loadingCars"
                    style="width: 100%"
                >
                    <el-option
                        v-for="car in availableCars"
                        :key="car.id"
                        :label="`${car.carNumber} (${car.brand})`"
                        :value="car.id"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="Выберите свободное место">
                <el-select v-model="newBooking.idSpot" placeholder="Номер места" style="width: 100%">
                    <el-option
                        v-for="spot in freeSpots"
                        :key="spot.id"
                        :label="`Место №${spot.spotNumber}`"
                        :value="spot.id"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="Дата окончания брони">
                <el-date-picker
                    v-model="newBooking.endDate"
                    type="datetime"
                    placeholder="Выберите дату и время"
                    style="width: 100%"
                    value-format="YYYY-MM-DDTHH:mm:ss" 
                />
            </el-form-item>
        </el-form>
        <template #footer>
            <el-button @click="dialogVisible = false">Отмена</el-button>
            <el-button type="primary" @click="submitBooking">Забронировать</el-button>
        </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed  } from 'vue';
import { useBookingStore, type NewBooking, type BookingView } from '../stores/bookingStore';
import { useManagementStore } from '../stores/managementStore';
import { ElMessage } from 'element-plus';
import dayjs from 'dayjs';

const bookingStore = useBookingStore();
const managementStore = useManagementStore(); 

const searchCar = ref('');
const searchName = ref('');

const dialogVisible = ref(false);
const loadingCars = ref(false);
const availableCars = ref<any[]>([]);
const freeSpots = ref<any[]>([]);

const currentPage = ref(1);
const pageSize = ref(10);
const totalBookings = computed(() => bookingStore.bookings.length);

const paginatedBookings = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return bookingStore.bookings.slice(start, end);
});


const newBooking = reactive<NewBooking>({
    idCar: null,
    idSpot: null,
    endDate: ''
});

const handleSearch = () => {
    bookingStore.fetchBookings(searchCar.value, searchName.value);
    currentPage.value = 1;
};

const handlePaymentChange = (row: BookingView) => {
    bookingStore.togglePayment(row.id, !row.isPaid); 
};

const openCreateDialog = async () => {
    dialogVisible.value = true;
    newBooking.idCar = null;
    newBooking.idSpot = null;
    newBooking.endDate = '';
    
    await managementStore.fetchSpots(); 
    freeSpots.value = managementStore.spots.filter((s: any) => s.isAvailable);

    if (freeSpots.value.length === 0){
        ElMessage.warning('Нет свободный мест для бронирования');
        dialogVisible.value = false;
        return;
    }

    searchCarsRemote(''); 
};

const searchCarsRemote = async (query: string) => {
    loadingCars.value = true;
    await managementStore.fetchCars(query);
    availableCars.value = managementStore.cars;
    loadingCars.value = false;
};

const submitBooking = async () => {
    if (!newBooking.idCar || !newBooking.idSpot || !newBooking.endDate) {
        ElMessage.warning('Заполните все поля');
        return;
    }
    const selectedSpot = managementStore.spots.find((s: any) => s.id === newBooking.idSpot);

    try {
        await bookingStore.createBooking(newBooking);
        ElMessage.success('Бронирование создано');
        dialogVisible.value = false;
        currentPage.value = 1;
    } catch (error) {
        ElMessage.error('Ошибка при создании бронирования');
    }
};

const formatDate = (dateStr: string) => {
    return dayjs(dateStr).format('DD.MM.YYYY HH:mm');
};

const handleSizeChange = (val: number) => {
  pageSize.value = val;
  currentPage.value = 1;
};

const handleCurrentChange = (val: number) => {
  currentPage.value = val;
};

onMounted(() => {
    bookingStore.fetchBookings();
});
</script>

<style scoped>
.page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.filters {
    display: flex;
    gap: 15px;
    margin-bottom: 10px;
}
.booking-page {
    padding: 20px;
    max-width: 1200px;
    margin: 0 auto;
}
</style>