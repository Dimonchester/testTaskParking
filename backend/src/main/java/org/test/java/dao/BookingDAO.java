package org.test.java.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.test.java.dto.BookingDTO;
import org.test.java.models.Booking;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BookingDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<BookingDTO> bookingDtoMapper = (rs, rowNum) -> {
        BookingDTO dto = new BookingDTO();
        dto.setId(rs.getInt("id"));
        dto.setPaid(rs.getBoolean("is_paid"));
        dto.setActive(rs.getBoolean("is_active"));
        dto.setStartDate(rs.getString("start_date"));
        dto.setEndDate(rs.getString("end_date"));
        dto.setCarNumber(rs.getString("car_number"));
        dto.setCarBrand(rs.getString("brand"));
        String fio = rs.getString("last_name") + " " + rs.getString("first_name");
        dto.setOwnerFio(fio);
        dto.setSpotNumber(rs.getInt("spot_number"));
        return dto;
    };

    public BookingDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BookingDTO> findAll(){
        return jdbcTemplate.query("SELECT b.id, b.is_paid, b.start_date, b.end_date, b.is_active,\n" +
                "       c.car_number, c.brand,\n" +
                "       o.last_name, o.first_name,\n" +
                "       s.spot_number\n" +
                "FROM bookings b\n" +
                "JOIN cars c ON b.id_car = c.id\n" +
                "JOIN owners o ON c.id_owner = o.id\n" +
                "JOIN spots s ON b.id_spot = s.id\n" +
                "WHERE b.is_active = true", bookingDtoMapper);
    }

    public List<BookingDTO> findBySearch(String carNumber, String ownerName){
        String sqlCar = "%" + carNumber + "%";
        String sqlOwner = "%" + ownerName + "%";


        return jdbcTemplate.query("SELECT b.id, b.is_paid, b.start_date, b.end_date, b.is_active,\n" +
                        "       c.car_number, c.brand,\n" +
                        "       o.last_name, o.first_name,\n" +
                        "       s.spot_number\n" +
                        "FROM bookings b\n" +
                        "JOIN cars c ON b.id_car = c.id\n" +
                        "JOIN owners o ON c.id_owner = o.id\n" +
                        "JOIN spots s ON b.id_spot = s.id\n" +
                        "WHERE b.is_active = true \n" +
                        "AND (c.car_number ILIKE ?) \n" +
                        "AND (" +
                        "   o.last_name ILIKE ? OR " +
                        "   o.first_name ILIKE ? OR " +
                        "   (o.last_name || ' ' || o.first_name) ILIKE ? OR " +
                        "   (o.first_name || ' ' || o.last_name) ILIKE ? " +
                        ")",
                bookingDtoMapper, sqlCar, sqlOwner, sqlOwner, sqlOwner, sqlOwner);
    }

    public void save(Booking booking){
        jdbcTemplate.update("INSERT INTO bookings(id_car, id_spot, start_date, end_date, is_paid, is_active) VALUES (?, ?, ?, ?::timestamp, ?, ?);",
                booking.getIdCar(), booking.getIdSpot(),
                booking.getStartDate(), booking.getEndDate(), booking.getPaid(), booking.getActive());
    }

    public void updatePaymentStatus(int id, boolean isPaid){
        jdbcTemplate.update("UPDATE bookings SET is_paid = ? WHERE id = ?;", isPaid, id);
    }

    public void deactivate(int id){
        jdbcTemplate.update("UPDATE bookings SET is_active = false, end_date = ? WHERE id = ?;", LocalDateTime.now(), id);

        Integer spotId = jdbcTemplate.queryForObject("SELECT id_spot FROM bookings WHERE id = ?", Integer.class, id);
        if(spotId != null) {
            jdbcTemplate.update("UPDATE spots SET is_available = true WHERE id = ?", spotId);
        }
    }

    public void occupySpot(int spotId) {
        jdbcTemplate.update("UPDATE spots SET is_available = false WHERE id = ?", spotId);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM bookings WHERE id = ?;", id);
    }
    public void update(int id, Booking updatedBooking){
        jdbcTemplate.update("UPDATE bookings SET id_car = ?, id_spot = ?, start_date = ?, end_date = ?, is_paid = ?, is_active = ? WHERE id = ?;",
                updatedBooking.getIdCar(), updatedBooking.getIdSpot(), updatedBooking.getStartDate(), updatedBooking.getEndDate(), updatedBooking.getPaid(),
                updatedBooking.getActive(), id);
    }
}
