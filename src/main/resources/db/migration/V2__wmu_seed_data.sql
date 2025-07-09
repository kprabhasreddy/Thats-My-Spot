-- Seed buildings
INSERT INTO buildings (name, description) VALUES
  ('Student Center', 'Main student hub with meeting and conference rooms'),
  ('Waldo Library', 'Central library with group and quiet study spaces'),
  ('Parkview Engineering', 'Engineering campus with tech-enabled rooms');

-- Seed rooms (Student Center)
INSERT INTO rooms (name, building_id, capacity, features, access_type, is_active, image_path)
VALUES
  ('1024 Mosaic Meeting', 1, 60, '{"whiteboard":true,"projector":true,"charging_outlets":true}', 'OPEN', true, NULL),
  ('1135 Mosaic Conference', 1, 24, '{"whiteboard":true,"projector":true,"charging_outlets":true}', 'OPEN', true, NULL),
  ('2101 Admissions', 1, 100, '{"projector":true}', 'RESTRICTED', true, NULL),
  ('2118 Conference', 1, 8, '{"whiteboard":true}', 'OPEN', true, NULL);

-- Seed rooms (Waldo Library)
INSERT INTO rooms (name, building_id, capacity, features, access_type, is_active, image_path)
VALUES
  ('Quiet Study Room A', 2, 6, '{"whiteboard":false,"projector":false,"charging_outlets":true}', 'OPEN', true, NULL),
  ('Group Study Room B', 2, 10, '{"whiteboard":true,"projector":false,"charging_outlets":true}', 'OPEN', true, NULL);

-- Seed rooms (Parkview Engineering)
INSERT INTO rooms (name, building_id, capacity, features, access_type, is_active, image_path)
VALUES
  ('Design Lab 1', 3, 20, '{"whiteboard":true,"projector":true,"charging_outlets":true}', 'OPEN', true, NULL),
  ('Tech Conference', 3, 30, '{"whiteboard":true,"projector":true,"charging_outlets":true}', 'RESTRICTED', true, NULL);

-- Seed admin user (password: admin123)
INSERT INTO users (name, email, password_hash, role, created_at)
VALUES ('WMU Admin', 'admin@wmich.edu', '$2a$10$w1QwQwQwQwQwQwQwQwQwQeQwQwQwQwQwQwQwQwQwQwQwQwQwQwQw', 'ADMIN', NOW());
-- The above hash is bcrypt for 'admin123' (for demo only) 