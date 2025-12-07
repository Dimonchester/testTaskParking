<template>
    <div class="container">
        <h1 class="text-center"> Cars List </h1>
        <table class="table table-striped">
            <thead>
                <th> Car Id</th>
                <th> Car number </th>
                <th> Brand </th>
                <th> ID owner </th>
            </thead>
            <tbody>
                <tr v-for="car in cars" v-bind:key="car.id">
                    <td>{{car.id}}</td>
                    <td>{{car.carNumber}}</td>
                    <td>{{car.brand}}</td>
                    <td>{{car.idOwner}}</td>
                </tr>
            </tbody>
        </table>
    </div>
    
</template>

<script>
import CarService from '../services/CarService';

    export default {
        name: 'carList',
        data(){
            return {cars : []}
        },
        methods: {
            getCars(){
                CarService.getCars().then((response) => { this.cars = response.data;
                });
            }
        },
        created(){
            this.getCars()
        }
    }
</script>

<style>

</style>