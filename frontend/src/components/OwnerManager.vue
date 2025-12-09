<template>
  <div>
    <div class="header-actions">
      <el-input
        v-model="searchQuery"
        placeholder="Поиск владельца по ФИО (например, Иванов Иван Иванович)"
        style="width: 400px; margin-right: 15px;"
        clearable
        @keyup.enter="handleSearch"
      >
        <template #append>
            <el-button @click="handleSearch" :loading="store.loading">Поиск</el-button>
        </template>
      </el-input>

      <el-button type="primary" @click="openDialog(null)">
        Добавить владельца
      </el-button>
    </div>

    <el-table 
      :data="store.owners" 
      v-loading="store.loading" 
      style="width: 100%; margin-top: 20px" 
      border
    >
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column label="ФИО" min-width="250">
        <template #default="scope">
          {{ scope.row.lastName }} {{ scope.row.firstName }} {{ scope.row.middleName }}
        </template>
      </el-table-column>
      <el-table-column prop="phoneNumber" label="Телефон" width="150" />
      
      <el-table-column label="Действия" width="180" fixed="right">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="openDialog(scope.row)">
            Редактировать
          </el-button>
          
          <el-popconfirm
            title="Вы уверены, что хотите удалить владельца?"
            @confirm="store.deleteOwner(scope.row.id)"
          >
            <template #reference>
              <el-button link type="danger" size="small">Удалить</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="currentOwner.id ? 'Редактировать владельца' : 'Новый владелец'" width="500px">
      <el-form :model="currentOwner" label-width="120px">
        <el-form-item label="Фамилия">
          <el-input v-model="currentOwner.lastName" />
        </el-form-item>
        <el-form-item label="Имя">
          <el-input v-model="currentOwner.firstName" />
        </el-form-item>
        <el-form-item label="Отчество">
          <el-input v-model="currentOwner.middleName" />
        </el-form-item>
        <el-form-item label="Телефон">
          <el-input v-model="currentOwner.phoneNumber" />
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
import { ref, onMounted, reactive } from 'vue';
import { useManagementStore, type Owner } from '../stores/managementStore';
import { ElMessage } from 'element-plus';

const store = useManagementStore();

const searchQuery = ref('');
const dialogVisible = ref(false);

const initialOwnerState: Owner = { firstName: '', lastName: '', middleName: '', phoneNumber: '' };
const currentOwner = reactive<Owner>({ ...initialOwnerState });

const handleSearch = () => {
  store.fetchOwners(searchQuery.value);
};

const openDialog = (owner: Owner | null) => {
  if (owner) {
    Object.assign(currentOwner, owner);
  } else {
    Object.assign(currentOwner, initialOwnerState);
  }
  dialogVisible.value = true;
};

const handleSave = async () => {
  if (!currentOwner.lastName || !currentOwner.firstName) {
    ElMessage.error('Поля Имя и Фамилия обязательны.');
    return;
  }
  
  try {
      await store.saveOwner({ ...currentOwner });
      ElMessage.success('Данные успешно сохранены.');
      dialogVisible.value = false;
  } catch (e) {
      ElMessage.error('Ошибка сохранения данных.');
  }
};

onMounted(() => {
  store.fetchOwners();
});
</script>

<style scoped>
.header-actions {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}
</style>