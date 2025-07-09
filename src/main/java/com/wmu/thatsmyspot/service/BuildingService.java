package com.wmu.thatsmyspot.service;

import com.wmu.thatsmyspot.entity.Building;
import com.wmu.thatsmyspot.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;

    public List<Building> findAll() {
        return buildingRepository.findAll();
    }

    public Optional<Building> findById(Long id) {
        return buildingRepository.findById(id);
    }

    public Building save(Building building) {
        return buildingRepository.save(building);
    }
} 