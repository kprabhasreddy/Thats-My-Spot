package com.wmu.thatsmyspot.controller;

import com.wmu.thatsmyspot.entity.Booking;
import com.wmu.thatsmyspot.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    public List<Booking> getBookingsByUser(@RequestParam Long userId) {
        return bookingService.findByUserId(userId);
    }

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.save(booking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/calendar")
    public List<Map<String, Object>> getBookingsForCalendar(
            @RequestParam(required = false) Long roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<Booking> bookings = (roomId != null)
                ? bookingService.findByRoomId(roomId)
                : bookingService.findAll();
        return bookings.stream()
                .filter(b -> !b.getEndTime().isBefore(start) && !b.getStartTime().isAfter(end))
                .map(b -> {
                    Map<String, Object> event = new java.util.HashMap<>();
                    event.put("id", b.getId());
                    event.put("title", b.getRoom().getName() + " (Booked)");
                    event.put("start", b.getStartTime().toString());
                    event.put("end", b.getEndTime().toString());
                    event.put("roomId", b.getRoom().getId());
                    event.put("userId", b.getUser().getId());
                    return event;
                })
                .collect(Collectors.toList());
    }

    @PostMapping("/calendar")
    public ResponseEntity<?> createBookingFromCalendar(@RequestBody Map<String, Object> req) {
        Long roomId = Long.valueOf(req.get("roomId").toString());
        Long userId = Long.valueOf(req.get("userId").toString());
        LocalDateTime start = LocalDateTime.parse(req.get("start").toString());
        LocalDateTime end = LocalDateTime.parse(req.get("end").toString());
        // Check for overlap
        List<Booking> overlaps = bookingService.findByRoomId(roomId).stream()
                .filter(b -> b.getStatus().equals("ACTIVE") &&
                        !b.getEndTime().isBefore(start) && !b.getStartTime().isAfter(end))
                .toList();
        if (!overlaps.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Room is already booked for this time."));
        }
        Booking booking = bookingService.createBooking(roomId, userId, start, end);
        return ResponseEntity.ok(Map.of("success", true, "bookingId", booking.getId()));
    }
} 