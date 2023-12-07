INSERT INTO addresses (id, country, city, postal_code, address) VALUES (1, 'Srbija', 'Novi Sad', '21000', 'Futoska 12');
INSERT INTO accounts(id, email, password, status, type) VALUES (1, 'email@example.com', 'password', 'ACTIVE', 'HOST');
INSERT INTO accounts(id, email, password, status, type) VALUES (2, 'email@example.com', 'password', 'ACTIVE', 'HOST');

insert into users(id,first_name,last_name,address_id,phone_number,account_id,picture_path) values (1,'pera','peric',1,'1234',1,'putanja');
insert into users(id,first_name,last_name,address_id,phone_number,account_id,picture_path) values (2,'mika','peric',1,'1234',2,'putanja');

insert into guests values (1);

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



VALUES (1,'2023-01-01 10:00:00','2023-01-01 12:00:00');
INSERT INTO amenities (id, amenity_name)
VALUES (1,'WiFi');

INSERT INTO pricelist_items (id, time_slot_id, price)
VALUES (1, 1,100.00);
INSERT INTO accommodations (
    id, name, description, address_id, min_guest, max_guest,
    acc_type, price_per_guest, automatic_conf, acc_status,
    reservation_deadline)
VALUES (
           1,
           'Accommodation Name',
           'Accommodation Description',
           1,
           1,
           5,
           'APARTMENT',
           true,
           true,
           'ACCEPTED',
           7
       );
INSERT INTO accommodations_price_list VALUES (1,1);
INSERT INTO accommodations_amenities VALUES (1,1);
INSERT INTO accommodations_free_time_slots VALUES (1,1);

insert into favorite_accommodation values(1,1);


INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
VALUES (1,150.00,1, 2,'ACCEPTED' );

INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
VALUES (2,3450.00,1, 5,'ACCEPTED' );

INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
VALUES (3,350.00,1, 2,'ACCEPTED' );

INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
VALUES (4,350.00,1, 2,'WAITING' );

INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
VALUES (5,350.00,1, 2,'WAITING' );
