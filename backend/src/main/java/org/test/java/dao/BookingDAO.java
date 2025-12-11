package org.test.java.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.test.java.dto.BookingDTO;
import org.test.java.models.Booking;

import java.util.List;

@Repository
public class BookingDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<BookingDTO> bookingDtoMapper = (rs, rowNum) -> {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(rs.getInt("id"));
        bookingDTO.setPaid(rs.getBoolean("is_paid"));
        bookingDTO.setActive(rs.getBoolean("is_active"));
        bookingDTO.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
        bookingDTO.setEndDate(rs.getTimestamp("end_date").toLocalDateTime());
        bookingDTO.setCarNumber(rs.getString("car_number"));
        bookingDTO.setCarBrand(rs.getString("brand"));
        String fio = rs.getString("last_name") + " " + rs.getString("first_name") + " " + rs.getString("middle_name");
        bookingDTO.setOwnerFio(fio);
        bookingDTO.setSpotNumber(rs.getInt("spot_number"));
        return bookingDTO;
    };

    public BookingDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BookingDTO> findAll(){
        return jdbcTemplate.query("SELECT b.id, b.is_paid, b.start_date, b.end_date, b.is_active,\n" +
                "       c.car_number, c.brand,\n" +
                "       o.last_name, o.first_name, o.middle_name,\n" +
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
                        "       o.last_name, o.first_name, o.middle_name,\n" +
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
                        "   o.middle_name ILIKE ? OR " +
                        "   (o.last_name || ' ' || o.first_name) ILIKE ? OR " +
                        "   (o.first_name || ' ' || o.last_name) ILIKE ? OR " +
                        "   (o.middle_name || ' ' || o.last_name) ILIKE ? OR " +
                        "   (o.last_name || ' ' || o.middle_name) ILIKE ? OR " +
                        "   (o.middle_name || ' ' || o.first_name) ILIKE ? OR " +
                        "   (o.first_name || ' ' || o.middle_name) ILIKE ?" +
                        ")",
                bookingDtoMapper, sqlCar, sqlOwner, sqlOwner, sqlOwner, sqlOwner, sqlOwner, sqlOwner, sqlOwner, sqlOwner, sqlOwner);
    }

    public Booking findById(int id){
        return jdbcTemplate.queryForObject("SELECT * FROM bookings WHERE id = ?", new BeanPropertyRowMapper<>(Booking.class), id);
    }

    public void save(Booking booking){
        jdbcTemplate.update("INSERT INTO bookings(id_car, id_spot, start_date, end_date, is_paid, is_active) VALUES (?, ?, ?, ?::timestamp, ?, ?);",
                booking.getIdCar(), booking.getIdSpot(),
                booking.getStartDate(), booking.getEndDate(), booking.getPaid(), booking.getActive());
    }

    public void updatePaymentStatus(int id, boolean isPaid){
        jdbcTemplate.update("UPDATE bookings SET is_paid = ? WHERE id = ?;", isPaid, id);
    }

    public void occupySpot(int idSpot) {
        jdbcTemplate.update("UPDATE spots SET is_available = false WHERE id = ?", idSpot);
    }

    public void freeSpot(int idSpot) {
        jdbcTemplate.update("UPDATE spots SET is_available = true WHERE id = ?", idSpot);
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
