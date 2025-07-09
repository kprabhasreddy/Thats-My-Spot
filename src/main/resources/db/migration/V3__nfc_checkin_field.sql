ALTER TABLE bookings ADD COLUMN nfc_checked_in_at TIMESTAMP NULL;
-- For future: set this when user scans NFC at the room/table 