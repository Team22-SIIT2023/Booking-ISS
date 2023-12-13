INSERT INTO addresses (country, city, postal_code, address) VALUES ('Srbija', 'Novi Sad', '21000', 'Futoska 12');

INSERT INTO roles (name) VALUES ('HOST');
INSERT INTO roles (name) VALUES ('GUEST');

INSERT INTO accounts(username, password, status, role_id) VALUES ('pera@example.com', '123', 'ACTIVE', 1);
INSERT INTO accounts(username, password, status, role_id) VALUES ('mika@example.com', '123', 'ACTIVE', 2);

INSERT INTO users (first_name, last_name, address_id, phone_number, account_id, picture_path, last_password_reset_date)
VALUES ('pera', 'peric', 1, '1234', 1, '', '2023-01-01 12:00:00');
insert into users(first_name,last_name,address_id,phone_number,account_id,picture_path,last_password_reset_date)
values ('mika','peric',1,'1234',2,'','2023-01-01 12:00:00');


insert into guests values (2);
insert into hosts values (1);

INSERT INTO timeslots (start_date, end_date)
VALUES ('2024-01-01 10:00:00','2024-01-10 12:00:00');
INSERT INTO timeslots (start_date, end_date)
VALUES ('2024-02-04 10:00:00','2024-02-11 12:00:00');
INSERT INTO timeslots (start_date, end_date)
VALUES ('2024-01-21 10:00:00','2024-01-03 12:00:00');
INSERT INTO timeslots (start_date, end_date)
VALUES ('2024-01-21 10:00:00','2024-01-24 12:00:00');
INSERT INTO timeslots (start_date, end_date)
VALUES ('2024-04-01 10:00:00','2024-04-11 12:00:00');

INSERT INTO amenities (amenity_name)
VALUES ('WiFi');

INSERT INTO pricelist_items (time_slot_id, price)
VALUES (1,100.00);
INSERT INTO accommodations (
    name, description, address_id, min_guest, max_guest,
    acc_type, price_per_guest, automatic_conf,host_id, acc_status,
    reservation_deadline)
VALUES (
           'Accommodation Name',
           'Accommodation Description',
           1,
           1,
           5,
           'APARTMENT',
           true,
           true,
            1,
           'ACCEPTED',
           7
       );

-- INSERT INTO accommodations (name, description, address_id, min_guest, max_guest, acc_type, price_per_guest, automatic_conf, host_id, acc_status, reservation_deadline)
-- VALUES
--     ('Cozy Apartment', 'A comfortable place for your stay', 1, 2, 4, 'APARTMENT', true, true, 1, 'ACTIVE', 7);
-- INSERT INTO accommodations (name, description, address_id, min_guest, max_guest, acc_type, price_per_guest, automatic_conf, host_id, acc_status, reservation_deadline)
-- VALUES
--     ('Luxury Villa', 'Experience luxury in our beautiful villa', 2, 6, 10, 'VILLA', true, false, 2, 'ACTIVE', 14);
-- INSERT INTO accommodations (name, description, address_id, min_guest, max_guest, acc_type, price_per_guest, automatic_conf, host_id, acc_status, reservation_deadline)
-- VALUES
--     ('Mountain Cabin', 'Escape to nature in our rustic cabin', 1, 2, 4, 'CABIN', true, true, 1, 'INACTIVE', 10);

INSERT INTO accommodations_price_list VALUES (1,1);
INSERT INTO amenities_accommodation VALUES (1,1);
INSERT INTO accommodations_free_time_slots VALUES (1,1);

insert into favorite_accommodation values(1,2);


-- INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
-- VALUES (,150.00,1, 2,'ACCEPTED' );
--
-- INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
-- VALUES (2,3450.00,1, 5,'ACCEPTED' );

INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
VALUES (3,350.00,1, 2,'ACCEPTED' );

INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
VALUES (4,350.00,1, 2,'WAITING' );

INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
VALUES (5,350.00,1, 2,'WAITING' );

-- Insert Requests
-- INSERT INTO requests (time_slot_id, price, guest_id, accommodation_id, guest_number, request_status)
-- VALUES
--     (6, 100.00, 1, 2, 2, 'PENDING'),
--     (7, 200.00, 2, 3, 6, 'CONFIRMED'),
--     (8, 150.00, 1, 4, 4, 'CANCELLED');
