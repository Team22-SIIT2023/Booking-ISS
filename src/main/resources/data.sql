INSERT INTO addresses (country, city, postal_code, address) VALUES ('Srbija', 'Novi Sad', '21000', 'Futoska 12');

INSERT INTO roles (name) VALUES ('HOST');
INSERT INTO roles (name) VALUES ('GUEST');

INSERT INTO accounts(username, password, status, role_id) VALUES ('pera@example.com', '123', 'ACTIVE', 1);
INSERT INTO accounts(username, password, status, role_id) VALUES ('mika@example.com', '123', 'ACTIVE', 2);

insert into users(first_name,last_name,address_id,phone_number,account_id,picture_path,last_password_reset_date) values ('pera','peric',1,'1234',1,'putanja','2023-01-01 12:00:00');
insert into users(first_name,last_name,address_id,phone_number,account_id,picture_path,last_password_reset_date) values ('mika','peric',1,'1234',2,'putanja','2023-01-01 12:00:00');
--
-- insert into guests values (1);
--
-- INSERT INTO timeslots (start_date, end_date)
-- VALUES ('2024-01-01 10:00:00','2024-01-10 12:00:00');
-- INSERT INTO timeslots (start_date, end_date)
-- VALUES ('2024-02-04 10:00:00','2024-02-11 12:00:00');
-- INSERT INTO timeslots (start_date, end_date)
-- VALUES ('2024-01-21 10:00:00','2024-01-03 12:00:00');
-- INSERT INTO timeslots (start_date, end_date)
-- VALUES ('2024-01-21 10:00:00','2024-01-24 12:00:00');
-- INSERT INTO timeslots (start_date, end_date)
-- VALUES ('2024-04-01 10:00:00','2024-04-11 12:00:00');
--
-- INSERT INTO amenities (amenity_name) VALUES ('wifi');
-- INSERT INTO amenities (amenity_name) VALUES ('pool');
-- INSERT INTO amenities (amenity_name) VALUES ('air conditioning');
-- INSERT INTO amenities (amenity_name) VALUES ('spa');
-- INSERT INTO amenities (amenity_name) VALUES ('tv');
-- INSERT INTO amenities (amenity_name) VALUES ('breakfast');
-- INSERT INTO amenities (amenity_name) VALUES ('pet friendly');
-- INSERT INTO amenities (amenity_name) VALUES ('bar');
-- INSERT INTO amenities (amenity_name) VALUES ('kitchen');
-- INSERT INTO amenities (amenity_name) VALUES ('balcony');
--
-- INSERT INTO pricelist_items (time_slot_id, price)
-- VALUES (1,100.00);
-- INSERT INTO accommodations (
--     name, description, address_id, min_guest, max_guest,
--     acc_type, price_per_guest, automatic_conf, acc_status,
--     reservation_deadline)
-- VALUES (
--            'Accommodation Name',
--            'Accommodation Description',
--            1,
--            1,
--            5,
--            'APARTMENT',
--            true,
--            true,
--            'ACCEPTED',
--            7
--        );
-- INSERT INTO accommodations_price_list VALUES (1,1);
-- INSERT INTO amenities_accommodation VALUES (1,1);

-- INSERT INTO accommodations_free_time_slots VALUES (1,1);
--
-- insert into favorite_accommodation values(1,1);
-- --
-- INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
-- VALUES (21,150.00,10, 2,'ACCEPTED');
--
-- INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
-- VALUES (2,3450.00,1, 5,'ACCEPTED' );
--
-- INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
-- VALUES (3,350.00,1, 2,'ACCEPTED' );
--
-- INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
-- VALUES (4,350.00,1, 2,'WAITING' );
--
-- INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
-- VALUES (5,350.00,1, 2,'WAITING' );