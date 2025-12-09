<template>
  <div id="app">
    <el-menu 
      :default-active="currentRoute" 
      mode="horizontal" 
      router 
      :ellipsis="false"
      class="header-menu"
    >
      <el-menu-item index="/">
        Парковка | Админка
      </el-menu-item>
      <div class="flex-grow" /> <el-menu-item index="/management">
        <router-link to="/management" class="nav-link">
          Учет данных (Страница 1)
        </router-link>
      </el-menu-item>

      <el-menu-item index="/bookings">
        <router-link to="/bookings" class="nav-link">
          Бронирование и Оплата (Страница 2)
        </router-link>
      </el-menu-item>
    </el-menu>

    <main class="content-container">
      <router-view></router-view>
    </main>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const currentRoute = computed(() => route.path);
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin-top: 0; 
  text-align: left;
}

.header-menu {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); 
}

.flex-grow {
  flex-grow: 1;
}

.nav-link {
    text-decoration: none;
    color: inherit;
    display: block;
}

.content-container {
    padding: 10px;
}
</style>