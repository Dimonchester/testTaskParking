<template>
  <div>
    <div class="header-actions">
      <el-input
        v-model="searchQuery"
        placeholder="Поиск автомобиля по госномеру..."
        style="width: 300px; margin-right: 15px;"
        clearable
        @keyup.enter="handleSearch"
      >
        <template #append>
            <el-button @click="handleSearch" :loading="store.loading">Поиск</el-button>
        </template>
      </el-input>

      <el-button type="primary" @click="openDialog(null)">
        Добавить автомобиль
      </el-button>
    </div>

    <el-table 
      :data="store.cars" 
      v-loading="store.loading" 
      style="width: 100%; margin-top: 20px" 
      border
    >
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="carNumber" label="Госномер" width="150" />
      <el-table-column prop="brand" label="Марка / Модель" min-width="180" />

      <el-table-column prop="ownerFio" label="ФИО Владельца" width="300">

      </el-table-column>
      
      <el-table-column label="Действия" width="180" fixed="right">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="openDialog(scope.row)">
            Редактировать
          </el-button>
          
          <el-popconfirm
            title="Вы уверены, что хотите удалить автомобиль?"
            @confirm="handleDelete(scope.row.id)"
          >
            <template #reference>
              <el-button link type="danger" size="small">Удалить</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="currentCar.id ? 'Редактировать автомобиль' : 'Новый автомобиль'" width="500px">
      <el-form :model="currentCar" label-width="120px">
        
        <el-form-item label="Госномер">
          <el-input v-model="currentCar.carNumber" placeholder="A000AA777" />
        </el-form-item>
        
        <el-form-item label="Марка">
          <el-input v-model="currentCar.brand" placeholder="Toyota Camry" />
        </el-form-item>



        <el-form-item label="Выберите ФИО">
          <el-select 
            v-model="currentCar.idOwner" 
            placeholder="ФИО Владельца" 
            style="width: 100%"
            @change="handleOwnerChange"
          >
            <el-option
              v-for="fio in allFio"
              :key="fio.idOwner"
              :label="`${fio.idOwner}, ${fio.ownerFio}`"
              :value="fio.idOwner"
            />
          </el-select>
        </el-form-item>

      </el-form>

      <template #footer>
        <el-button @click="dialogVisible = false">Отмена</el-button>
        <el-button type="primary" @click="handleSave">Сохранить</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive, computed } from 'vue';
import { useManagementStore, type Car} from '../stores/managementStore';
import { ElMessage } from 'element-plus';

const store = useManagementStore();
const searchQuery = ref('');
const dialogVisible = ref(false);
const allFio = ref<any[]>([]);

const initialCarState: Car = { carNumber: '', brand: '', ownerFio: '', idOwner: 0 };
const currentCar = reactive<Car>({ ...initialCarState });

const handleSearch = () => {
  store.fetchCars(searchQuery.value);
};

const openDialog = (car: Car | null) => {
  if (car) {
    Object.assign(currentCar, car);
  } else {
    Object.assign(currentCar, initialCarState); 
  }
  dialogVisible.value = true;

  const carsMap = new Map<number, Car>();
  
  store.cars.forEach(carItem => {
    if (carItem.idOwner && !carsMap.has(carItem.idOwner)) {
      carsMap.set(carItem.idOwner, carItem);
    }
  });
  
  allFio.value = Array.from(carsMap.values()).sort((a: Car, b: Car) => 
      (a.ownerFio || '').localeCompare(b.ownerFio || '', 'ru')
  );

};

const handleSave = async () => {
  if (!currentCar.carNumber || !currentCar.brand || !currentCar.ownerFio) {
    ElMessage.warning('Заполните все поля');
    return;
  }
  try {
    await store.saveCar({ ...currentCar });
    ElMessage.success('Автомобиль успешно сохранен.');
    dialogVisible.value = false;
  } catch (error) {
    ElMessage.error('Ошибка сохранения данных.');
  }
};

const handleDelete = async (id: number | undefined) => {
    if (id) {
        try {
            await store.deleteCar(id);
            ElMessage.success('Автомобиль удален.');
        } catch (error) {
            ElMessage.error('Не удалось удалить автомобиль. Проверьте связи с бронированиями.');
        }
    }
};

onMounted(() => {
  store.fetchCars();
});

const handleOwnerChange = (selectedId: number) => {
  const selectedItem = allFio.value.find(item => item.idOwner === selectedId);
  if (selectedItem) {
    currentCar.ownerFio = selectedItem.ownerFio;
  }
};
</script>

<style scoped>
.header-actions {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}
</style>