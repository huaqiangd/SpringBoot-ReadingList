
INSERT INTO readinglist_role (role_id, role_name, auth) VALUES (1, 'ROLE_USER', 'user');
INSERT INTO readinglist_role (role_id, role_name, auth) VALUES (2, 'ROLE_ADMIN', 'user,admin');


INSERT INTO readinglist_user (username, password, role_id, last_password_change, enable) VALUES ('admin', '123456', 2, '2019-02-27 00:00:00', 1);
INSERT INTO readinglist_user (username, password, role_id, last_password_change, enable) VALUES ('guest', '123456', 1, '2019-02-27 00:00:00', 1);