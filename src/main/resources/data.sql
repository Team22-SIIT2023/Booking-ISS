
INSERT INTO addresses (country, city, postal_code, address) VALUES ('Serbia', 'Novi Sad', '21000', 'Alekse Santica 54',false);
INSERT INTO addresses (country, city, postal_code, address) VALUES ('Serbia', 'Novi Sad', '21000', 'Futoska 54',false,);
INSERT INTO addresses (country, city, postal_code, address) VALUES ('Serbia', 'Novi Sad', '21000', 'Puskinova 3',false);


INSERT INTO role (name) VALUES ('ROLE_HOST');
INSERT INTO role (name) VALUES ('ROLE_GUEST');


INSERT INTO accounts(username, password, status) VALUES ('pera@example.com', '123', 'ACTIVE',false);
INSERT INTO accounts(username, password, status) VALUES ('mika@example.com', '123', 'ACTIVE',false);
INSERT INTO accounts(username, password, status) VALUES ('zika@example.com', '123', 'ACTIVE',false);


INSERT INTO account_role VALUES (1,1);
INSERT INTO account_role VALUES (2,2);
INSERT INTO account_role VALUES (3,1);


INSERT INTO users (first_name, last_name, address_id, phone_number, account_id, picture_path, last_password_reset_date)
VALUES ('pera', 'peric', 1, '1234', 1, '', '2023-01-01 12:00:00',false);
insert into users(first_name,last_name,address_id,phone_number,account_id,picture_path,last_password_reset_date)
values ('mika','peric',1,'1234',2,'','2023-01-01 12:00:00',false);
insert into users(first_name,last_name,address_id,phone_number,account_id,picture_path,last_password_reset_date)
values ('zika','peric',1,'1234',3,'','2023-01-01 12:00:00',false);



insert into guests values (2);
insert into hosts values (1);
insert into hosts values (3);

INSERT INTO timeslots (start_date, end_date, deleted)
VALUES ('2024-01-01 10:00:00','2024-01-10 12:00:00', false);
INSERT INTO timeslots (start_date, end_date, deleted)
VALUES ('2024-01-14 10:00:00','2024-01-22 12:00:00', false);
INSERT INTO timeslots (start_date, end_date, deleted)
VALUES ('2024-03-21 10:00:00','2024-03-30 12:00:00', false);
INSERT INTO timeslots (start_date, end_date, deleted)
VALUES ('2024-01-21 10:00:00','2024-01-24 12:00:00', false);
INSERT INTO timeslots (start_date, end_date, deleted)
VALUES ('2024-04-01 10:00:00','2024-04-11 12:00:00', false);
INSERT INTO timeslots (start_date, end_date, deleted)
VALUES ('2024-04-01 10:00:00','2024-04-11 12:00:00', false);


INSERT INTO amenities (amenity_name, deleted) VALUES ('wifi', false);
INSERT INTO amenities (amenity_name, deleted) VALUES ('pool', false);
INSERT INTO amenities (amenity_name, deleted) VALUES ('air conditioning', false);
INSERT INTO amenities (amenity_name, deleted) VALUES ('spa', false);
INSERT INTO amenities (amenity_name, deleted) VALUES ('tv', false);
INSERT INTO amenities (amenity_name, deleted) VALUES ('breakfast', false);
INSERT INTO amenities (amenity_name, deleted) VALUES ('pet friendly', false);
INSERT INTO amenities (amenity_name, deleted) VALUES ('bar', false);
INSERT INTO amenities (amenity_name, deleted) VALUES ('kitchen', false);
INSERT INTO amenities (amenity_name, deleted) VALUES ('balcony', false);


INSERT INTO accommodations (
    name, description, address_id, min_guest, max_guest,
    acc_type, price_per_guest, automatic_conf, acc_status,
    reservation_deadline,host_id, deleted)
VALUES (
           'Accommodation Name 1',
           'Accommodation Description',
           1,
           1,
           5,
           'HOTEL',
           true,
           true,
           'ACCEPTED',
           7,3,false
       );
INSERT INTO accommodations (
    name, description, address_id, min_guest, max_guest,
    acc_type, price_per_guest, automatic_conf, acc_status,
    reservation_deadline,host_id,deleted)
VALUES (
           'Accommodation Name 2',
           'Accommodation Description',
           2,
           1,
           5,
           'HOTEL',
           true,
           true,
           'ACCEPTED',
           7,1, false
       );
INSERT INTO accommodations (
    name, description, address_id, min_guest, max_guest,
    acc_type, price_per_guest, automatic_conf, acc_status,
    reservation_deadline,host_id,deleted)
VALUES (
           'Accommodation Name 3',
           'Accommodation Description',
           3,
           1,
           5,
           'HOTEL',
           true,
           true,
           'ACCEPTED',
           7,3,false
       );

INSERT INTO pricelist_items (time_slot_id, price, deleted) VALUES (1,100.00, false);

INSERT INTO accommodations_price_list VALUES (1,1);
INSERT INTO amenities_accommodation VALUES (1,1);
INSERT INTO amenities_accommodation VALUES (1,2);
INSERT INTO amenities_accommodation VALUES (2,1);
INSERT INTO accommodations_free_time_slots VALUES (1,1);
INSERT INTO accommodations_free_time_slots VALUES (1,2);
INSERT INTO accommodations_free_time_slots VALUES (1,3);
INSERT INTO accommodations_free_time_slots VALUES (2,4);
INSERT INTO accommodations_free_time_slots VALUES (2,5);
INSERT INTO accommodations_free_time_slots VALUES (2,6);


INSERT INTO comments (comment_text, date, rating, comment_status, guest_id,id, deleted)
VALUES ('For this price great!', '2023-12-11', 4.5, 'ACTIVE', 2,1,false);
INSERT INTO comments (comment_text, date, rating, comment_status, guest_id,id, deleted)
VALUES ('Okay, can be better.', '2023-12-11', 3.0, 'ACTIVE', 2,2,false);
INSERT INTO comments (comment_text, date, rating, comment_status, guest_id,id,deleted)
VALUES ('Loved it!', '2023-12-11', 5.0, 'ACTIVE', 2,3,false);
INSERT INTO comments (comment_text, date, rating, comment_status, guest_id,id, deleted)
VALUES ('Very poor', '2023-12-11', 2.0, 'ACTIVE', 2,4,false);

insert into accommodation_comments values(1,1);
insert into accommodation_comments values(1,2);
insert into accommodation_comments values(1,3);
insert into accommodation_comments values(1,4);




-- INSERT INTO accommodations (name, description, address_id, min_guest, max_guest, acc_type, price_per_guest, automatic_conf, host_id, acc_status, reservation_deadline)
-- VALUES
--     ('Cozy Apartment', 'A comfortable place for your stay', 1, 2, 4, 'APARTMENT', true, true, 1, 'ACTIVE', 7);
-- INSERT INTO accommodations (name, description, address_id, min_guest, max_guest, acc_type, price_per_guest, automatic_conf, host_id, acc_status, reservation_deadline)
-- VALUES
--     ('Luxury Villa', 'Experience luxury in our beautiful villa', 2, 6, 10, 'VILLA', true, false, 2, 'ACTIVE', 14);
-- INSERT INTO accommodations (name, description, address_id, min_guest, max_guest, acc_type, price_per_guest, automatic_conf, host_id, acc_status, reservation_deadline)
-- VALUES
--     ('Mountain Cabin', 'Escape to nature in our rustic cabin', 1, 2, 4, 'CABIN', true, true, 1, 'INACTIVE', 10);


-- insert into favorite_accommodation values(1,2);


-- INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
-- VALUES (,150.00,1, 2,'ACCEPTED' );
--
-- INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
-- VALUES (2,3450.00,1, 5,'ACCEPTED' );

-- INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
-- VALUES (21,150.00,10, 2,'ACCEPTED');

INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status,deleted)
VALUES (2,3450.00,1, 5,'ACCEPTED' ,false);

INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status,deleted)
VALUES (3,350.00,1, 2,'ACCEPTED' ,false);

INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status,deleted)
VALUES (4,350.00,1, 2,'WAITING' ,false);



