-- Users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Buildings table
CREATE TABLE buildings (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

-- Rooms table
CREATE TABLE rooms (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    building_id BIGINT NOT NULL REFERENCES buildings(id) ON DELETE CASCADE,
    capacity INTEGER NOT NULL,
    features JSONB,
    access_type VARCHAR(20) NOT NULL DEFAULT 'OPEN',
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    image_path VARCHAR(255),
    CONSTRAINT unique_room_name_per_building UNIQUE (name, building_id)
);

-- Bookings table
CREATE TABLE bookings (
    id BIGSERIAL PRIMARY KEY,
    room_id BIGINT NOT NULL REFERENCES rooms(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT no_overlap UNIQUE (room_id, start_time, end_time)
);

-- Indexes for performance
CREATE INDEX idx_room_building ON rooms(building_id);
CREATE INDEX idx_booking_user ON bookings(user_id);
CREATE INDEX idx_booking_room_time ON bookings(room_id, start_time, end_time); 