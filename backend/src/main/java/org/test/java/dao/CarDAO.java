package org.test.java.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.test.java.dto.CarDTO;
import org.test.java.models.Car;

import java.util.List;

@Repository
public class CarDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<CarDTO> carDTORowMapper = ((rs, rowNum) -> {
        CarDTO carDTO = new CarDTO();
        carDTO.setId(rs.getInt("id"));
        carDTO.setCarNumber(rs.getString("car_number"));
        carDTO.setBrand(rs.getString("brand"));
        String fio = rs.getString("last_name") + " " + rs.getString("first_name") + " " +  rs.getString("middle_name");
        carDTO.setIdOwner(rs.getInt("id_owner"));
        carDTO.setOwnerFio(fio);
        return carDTO;
    });

    @Autowired
    public CarDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CarDTO> findAll(){
        return jdbcTemplate.query("SELECT cars.id, cars.car_number, cars.brand, owners.first_name, owners.last_name, owners.middle_name, cars.id_owner FROM cars JOIN owners ON (cars.id_owner = owners.id);", carDTORowMapper);
    }

    public List<CarDTO> findByNumber(String carNumber){
        return jdbcTemplate.query("SELECT * FROM cars WHERE car_number ILIKE ?;", carDTORowMapper, "%" + carNumber + "%");
    }

    public void save(CarDTO carDTO){
        jdbcTemplate.update("INSERT INTO cars(car_number, brand, id_owner) VALUES (?, ?, (SELECT Distinct owners.id \n" +
                                "FROM owners JOIN cars ON (owners.id = cars.id_owner)\n" +
                                "WHERE CONCAT(last_name, ' ', first_name, ' ', middle_name) = ?));",
                carDTO.getCarNumber(), carDTO.getBrand(), carDTO.getOwnerFio());
    }

    public void update(int id, CarDTO carDTO){
        jdbcTemplate.update("UPDATE cars SET car_number = ?, brand = ?, id_owner = (SELECT Distinct owners.id \n" +
                                "FROM owners JOIN cars ON (owners.id = cars.id_owner) \n" +
                                "WHERE CONCAT(last_name, ' ', first_name, ' ', middle_name) = ?) WHERE id = ?;",
                carDTO.getCarNumber(), carDTO.getBrand(), carDTO.getOwnerFio(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM cars WHERE id = ?;", id);
    }
}
