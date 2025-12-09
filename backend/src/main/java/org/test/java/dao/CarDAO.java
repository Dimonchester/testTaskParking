package org.test.java.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.test.java.models.Car;

import java.util.List;

@Component
public class CarDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Car> carRowMapper = new BeanPropertyRowMapper<>(Car.class);

    @Autowired
    public CarDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Car> findAll(){
        return jdbcTemplate.query("SELECT * FROM cars;", carRowMapper);
    }

    public List<Car> findByNumber(String carNumber){
        return jdbcTemplate.query("SELECT * FROM cars WHERE car_number LIKE ?;", carRowMapper, carNumber);
    }

    public void save(Car car){
        jdbcTemplate.update("INSERT INTO cars(car_number, brand, id_owner) VALUES (?, ?, ?);", car.getCarNumber(), car.getBrand(), car.getIdOwner());
    }

    public void update(int id, Car updatedCar){
        jdbcTemplate.update("UPDATE cars SET car_number = ?, brand = ?, id_owner = ? WHERE id = ?;", updatedCar.getCarNumber(), updatedCar.getBrand(), updatedCar.getIdOwner(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM cars WHERE id = ?;", id);
    }
}
