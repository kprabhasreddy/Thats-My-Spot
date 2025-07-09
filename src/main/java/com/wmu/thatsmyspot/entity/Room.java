package com.wmu.thatsmyspot.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Map;

@Entity
@Table(name = "rooms", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "building_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @Column(nullable = false)
    private Integer capacity;

    @Column(columnDefinition = "jsonb")
    @Convert(converter = com.wmu.thatsmyspot.util.JsonbConverter.class)
    private Map<String, Object> features;

    @Column(name = "access_type", nullable = false, length = 20)
    private String accessType;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "image_path", length = 255)
    private String imagePath;
} 