import { createRouter, createWebHistory } from 'vue-router';
import ManagementPage from '../views/ManagementPage.vue';
import BookingPage from '../views/BookingPage.vue';

const routes = [
  {
    path: '/',
    redirect: '/management'
  },
  {
    path: '/management',
    name: 'Management',
    component: ManagementPage
  },
  {
    path: '/bookings',
    name: 'Bookings',
    component: BookingPage
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;