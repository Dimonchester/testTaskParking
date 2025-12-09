package org.test.java.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.test.java.models.Spot;

import java.util.List;

@Component
public class SpotDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Spot> spotRowMapper = new BeanPropertyRowMapper<>(Spot.class);

    @Autowired
    public SpotDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Spot> findAll(){
        return jdbcTemplate.query("SELECT * FROM spots", spotRowMapper);
    }

    public void save(Spot spot){
        jdbcTemplate.update("INSERT INTO spots(spot_number, is_available) VALUES (?, ?) ", spot.getSpotNumber(), spot.getIsAvailable());
    }

    public void update(int id, Spot updatedSpot){
        jdbcTemplate.update("UPDATE spots SET spot_number = ?, is_available = ? WHERE id = ?", updatedSpot.getSpotNumber(), updatedSpot.getIsAvailable(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM spots WHERE id = ?", id);
    }
}
