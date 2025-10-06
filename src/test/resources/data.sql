-- Reset sequences (optional, helps avoid ID collisions if tests rerun)
DELETE FROM booking;
DELETE FROM room;
DELETE FROM guest;

DELETE FROM booking_seq;
DELETE FROM room_seq;
DELETE FROM guest_seq;

INSERT INTO booking_seq (next_val) VALUES (1);
INSERT INTO room_seq (next_val) VALUES (1);
INSERT INTO guest_seq (next_val) VALUES (1);

-- Insert rooms
INSERT INTO room (id, room_number, room_name, room_size)
VALUES
    (1, '101', 'single room', 12),
    (2, '202', 'double room', 24),
    (3, '909', 'suite', 90);

-- Insert guests
INSERT INTO guest (id, name, email, phonenumber)
VALUES
    (1, 'Andreas', 'Andreas@Hotmale.com', '+46763060692'),
    (2, 'Arvid', 'Arvid@Gmail.com', '+46763060693'),
    (3, 'Adam', 'Adam@Gmail.com', '+46763060694');

-- Insert bookings
INSERT INTO booking (id, date_from, date_until, number_of_guests, guest_id, room_id)
VALUES
    (1, CURRENT_DATE(), DATE_ADD(CURRENT_DATE(), INTERVAL 5 DAY), 2, 1, 1);
