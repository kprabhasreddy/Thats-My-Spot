package com.wmu.thatsmyspot.service;

import com.wmu.thatsmyspot.entity.Booking;
import com.wmu.thatsmyspot.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wmu.thatsmyspot.entity.Room;
import com.wmu.thatsmyspot.entity.User;
import com.wmu.thatsmyspot.repository.RoomRepository;
import com.wmu.thatsmyspot.repository.UserRepository;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final JavaMailSender mailSender;
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public List<Booking> findByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public List<Booking> findByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking save(Booking booking) {
        Booking saved = bookingRepository.save(booking);
        sendBookingConfirmation(saved);
        return saved;
    }

    private void sendBookingConfirmation(Booking booking) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(booking.getUser().getEmail());
            message.setSubject("Your Room Booking Confirmation");
            message.setText("Your booking for room '" + booking.getRoom().getName() + "' is confirmed.\n" +
                "Start: " + booking.getStartTime() + "\nEnd: " + booking.getEndTime());
            mailSender.send(message);
            log.info("Booking confirmation email sent to {}", booking.getUser().getEmail());
        } catch (Exception e) {
            log.warn("Could not send booking confirmation email: {}", e.getMessage());
        }
    }

    public void cancelBooking(Long id) {
        bookingRepository.findById(id).ifPresent(booking -> {
            booking.setStatus("CANCELLED");
            bookingRepository.save(booking);
        });
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking createBooking(Long roomId, Long userId, LocalDateTime start, LocalDateTime end) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        Booking booking = Booking.builder()
                .room(room)
                .user(user)
                .startTime(start)
                .endTime(end)
                .status("ACTIVE")
                .build();
        return save(booking);
    }
} 