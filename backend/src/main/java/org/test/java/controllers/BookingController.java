package org.test.java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.test.java.dao.BookingDAO;
import org.test.java.dao.SpotDAO;
import org.test.java.dto.BookingDTO;
import org.test.java.models.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {
    private final BookingDAO bookingDAO;
    private final SpotDAO spotDAO;

    @Autowired
    public BookingController(BookingDAO bookingDAO, SpotDAO spotDAO){
        this.bookingDAO = bookingDAO;
        this.spotDAO = spotDAO;
    }

    @GetMapping
    public List<BookingDTO> find(@RequestParam(required = false) String carNumber,
                                 @RequestParam(required = false) String ownerName) {
        if ((carNumber != null && !carNumber.isEmpty()) || (ownerName != null && !ownerName.isEmpty())) {
            String searchCar = carNumber != null ? carNumber : "";
            String searchOwner = ownerName != null ? ownerName : "";
            return bookingDAO.findBySearch(searchCar, searchOwner);
        }
        return bookingDAO.findAll();
    }

    @PostMapping
    public void createBooking(@RequestBody Booking booking) {
        if (!spotDAO.isSpotAvailable(booking.getIdSpot())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Место уже занято");
        }

        booking.setStartDate(LocalDateTime.now());
        booking.setActive(true);
        booking.setPaid(false);

        bookingDAO.save(booking);
        bookingDAO.occupySpot(booking.getIdSpot());
    }

    @PutMapping("/{id}/pay")
    public void updatePaymentStatus(@PathVariable("id") int id, @RequestBody Map<String, Boolean> payload) {
        Boolean isPaid = payload.get("getPaid");
        if (isPaid != null) {
            bookingDAO.updatePaymentStatus(id, isPaid);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable("id") int id) {
        Booking booking = bookingDAO.findById(id);

        bookingDAO.freeSpot(booking.getIdSpot());
        bookingDAO.delete(id);
    }
}