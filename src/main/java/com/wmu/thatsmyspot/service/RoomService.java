package com.wmu.thatsmyspot.service;

import com.wmu.thatsmyspot.entity.Room;
import com.wmu.thatsmyspot.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    public List<Room> findByBuildingId(Long buildingId) {
        return roomRepository.findByBuildingId(buildingId);
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public void softDelete(Long id) {
        roomRepository.findById(id).ifPresent(room -> {
            room.setIsActive(false);
            roomRepository.save(room);
        });
    }
} 