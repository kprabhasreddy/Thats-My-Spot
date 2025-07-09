package com.wmu.thatsmyspot.controller;

import com.wmu.thatsmyspot.entity.Building;
import com.wmu.thatsmyspot.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/buildings")
@RequiredArgsConstructor
public class BuildingController {
    private final BuildingService buildingService;

    @GetMapping
    public List<Building> getAllBuildings() {
        return buildingService.findAll();
    }

    @PostMapping
    public Building createBuilding(@RequestBody Building building) {
        return buildingService.save(building);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Building> updateBuilding(@PathVariable Long id, @RequestBody Building building) {
        return buildingService.findById(id)
                .map(existing -> {
                    building.setId(id);
                    return ResponseEntity.ok(buildingService.save(building));
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 