begin tran;
INSERT INTO T_USER(USER_NAME, USER_EMAIL, USER_BIRTH, USER_PASSWORD, CREATED_AT, UPDATED_AT, VERSION, roles) VALUES('name0', 'name0@example.com' ,'2000-01-01','password0','2012-10-13 00:00:00','2012-10-13 00:00:00',0, 'ROLE_USER')
INSERT INTO T_USER(USER_NAME, USER_EMAIL, USER_BIRTH, USER_PASSWORD, CREATED_AT, UPDATED_AT, VERSION, roles) VALUES('name1', 'name1@example.com' ,'2000-01-01','password1','2012-10-13 00:00:00','2012-10-13 00:00:00',0, 'ROLE_USER') 
INSERT INTO T_USER(USER_NAME, USER_EMAIL, USER_BIRTH, USER_PASSWORD, CREATED_AT, UPDATED_AT, VERSION, roles) VALUES('name2', 'name2@example.com' ,'2000-01-01','password2','2012-10-13 00:00:00','2012-10-13 00:00:00',0, 'ROLE_USER') 
INSERT INTO T_USER(USER_NAME, USER_EMAIL, USER_BIRTH, USER_PASSWORD, CREATED_AT, UPDATED_AT, VERSION, roles) VALUES('name3', 'name3@example.com' ,'2000-01-01','password3','2012-10-13 00:00:00','2012-10-13 00:00:00',0, 'ROLE_USER') 
INSERT INTO T_USER(USER_NAME, USER_EMAIL, USER_BIRTH, USER_PASSWORD, CREATED_AT, UPDATED_AT, VERSION, roles) VALUES('name4', 'name4@example.com' ,'2000-01-01','password4','2012-10-13 00:00:00','2012-10-13 00:00:00',0, 'ROLE_USER') 
INSERT INTO T_USER(USER_NAME, USER_EMAIL, USER_BIRTH, USER_PASSWORD, CREATED_AT, UPDATED_AT, VERSION, roles) VALUES('admin', 'name4@example.com' ,'2000-01-01','admin','2012-10-13 00:00:00','2012-10-13 00:00:00',0, 'ROLE_ADMIN' )
commit;