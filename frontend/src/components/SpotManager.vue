<template>
  <div>
    <div class="header-actions">
      <el-button type="primary" @click="openDialog(null)">
        Добавить парковочное место
      </el-button>
    </div>

    <el-table 
      :data="paginatedSpots" 
      v-loading="store.loading" 
      style="width: 100%; margin-top: 20px" 
      border
      :row-key="(row) => row.id"
    >
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="spotNumber" label="Номер места" width="150" align="center">
          <template #default="{ row }">
            <el-tag size="large">{{ row.spotNumber }}</el-tag>
          </template>
      </el-table-column>
      
      <el-table-column label="Статус" width="150" align="center">
        <template #default="{ row }">
            <el-switch
                disabled 
                v-model="row.isAvailable" 
                active-text="Свободно"
                inactive-text="Занято"
                inline-prompt
                style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
            />
        </template>
      </el-table-column>
      
      <el-table-column label="Действия" width="180" fixed="right">
        <template #default="scope">
          
          <el-popconfirm
            title="Вы уверены, что хотите удалить место?"
            @confirm="handleDelete(scope.row.id)"
          >
            <template #reference>
              <el-button link type="danger" size="small">Удалить</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="totalSpots"
        :disabled="store.loading"
        layout="prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

        <el-dialog v-model="dialogVisible" :title="'Новое парковочное место'" width="500px">
      <el-form :model="currentSpot" label-width="120px">
        
        <el-form-item label="Номер места">
          <el-input v-model="currentSpot.spotNumber" placeholder="10" />
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
import { useManagementStore, type Spot } from '../stores/managementStore';
import { ElMessage } from 'element-plus';

const store = useManagementStore();
const dialogVisible = ref(false);

const initialSpotState: Spot = { spotNumber: 0, isAvailable: true };
const currentSpot = reactive<Spot>({ ...initialSpotState });

const currentPage = ref(1);
const pageSize = ref(10);
const totalSpots = computed(() => store.spots.length);

const paginatedSpots = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return store.spots.slice(start, end);
});

const openDialog = (spot: Spot | null) => {
  if (spot) {
    Object.assign(currentSpot, spot);
  } else {
    Object.assign(currentSpot, initialSpotState);
    currentSpot.spotNumber = 0;
  }
  dialogVisible.value = true;
};

const handleStatusChange = async (row: Spot) => {
    try {
        await store.updateSpotStatus(row);
        ElMessage.success(`Статус места №${row.spotNumber} успешно изменен.`);
    } catch (error) {
        ElMessage.error(`Не удалось изменить статус места №${row.spotNumber}.`);
        row.isAvailable = !row.isAvailable;
    }
};

const handleSave = async () => {
  if (currentSpot.spotNumber <= 0) {
    ElMessage.warning('Номер места должен быть указан.');
    return;
  }
  try {
    if (!currentSpot.id) {
        currentSpot.isAvailable = true;
    }
    await store.saveSpot({ ...currentSpot });
    ElMessage.success('Парковочное место сохранено.');
    dialogVisible.value = false;
    currentPage.value = 1;
  } catch (error) {
    ElMessage.error('Ошибка сохранения данных. Возможно, номер места уже существует.');
  }
};

const handleDelete = async (id: number | undefined) => {
    if (id) {
        try {
            await store.deleteSpot(id);
            ElMessage.success('Место удалено.');
        } catch (error) {
            ElMessage.error('Не удалось удалить место. Оно может быть забронировано.');
        }
    }
};

const handleSizeChange = (val: number) => {
  pageSize.value = val;
  currentPage.value = 1;
};

const handleCurrentChange = (val: number) => {
  currentPage.value = val;
};

onMounted(() => {
  store.fetchSpots();
});
</script>

<style scoped>
.header-actions {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}
</style>