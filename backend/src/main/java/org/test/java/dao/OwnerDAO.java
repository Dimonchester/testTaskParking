package org.test.java.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.test.java.models.Owner;

import java.util.List;

@Repository
public class OwnerDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Owner> ownerRowMapper = new BeanPropertyRowMapper<>(Owner.class);

    @Autowired
    public OwnerDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Owner> findAll(){
        return jdbcTemplate.query("SELECT * FROM owners;", ownerRowMapper);
    }

    public List<Owner> findByQuery(String query){
        String search = "%" + query + "%";
        return jdbcTemplate.query("SELECT * FROM owners WHERE last_name ILIKE ? OR first_name ILIKE ? OR middle_name ILIKE ? OR phone_number ILIKE ?",
                ownerRowMapper, search, search, search, search);
    }

    public void save(Owner owner){
        jdbcTemplate.update("INSERT INTO owners(first_name, last_name, middle_name, phone_number) VALUES (?, ?, ?, ?);", owner.getFirstName(),
                owner.getLastName(), owner.getMiddleName(), owner.getPhoneNumber());
    }

    public void update(int id, Owner updatedOwner){
        jdbcTemplate.update("UPDATE owners SET first_name = ?, last_name = ?, middle_name = ?, phone_number = ? WHERE id = ?;", updatedOwner.getFirstName(), updatedOwner.getLastName(),
                updatedOwner.getMiddleName(), updatedOwner.getPhoneNumber(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM owners WHERE id = ?;", id);
    }
}
