<template>
  <div class="management-page">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>Учет данных (Владельцы, Автомобили, Места)</span>
        </div>
      </template>

      <el-tabs v-model="activeTab">
        <el-tab-pane label="Владельцы" name="owners">
          <OwnerManager />
        </el-tab-pane>

        <el-tab-pane label="Автомобили" name="cars">
          <CarManager />
        </el-tab-pane>

        <el-tab-pane label="Парковочные места" name="spots">
          <SpotManager />
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, defineAsyncComponent } from 'vue';
const OwnerManager = defineAsyncComponent(() => import('../components/OwnerManager.vue'));
const CarManager = defineAsyncComponent(() => import('../components/CarManager.vue'));
const SpotManager = defineAsyncComponent(() => import('../components/SpotManager.vue'));

const activeTab = ref('owners'); 
</script>

<style scoped>
.management-page {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}
</style>