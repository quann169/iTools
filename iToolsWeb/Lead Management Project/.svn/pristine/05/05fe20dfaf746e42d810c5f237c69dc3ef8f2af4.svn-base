IF OBJECT_ID(N'dbo.t_user', N'U') IS NOT NULL
   DROP TABLE dbo.t_user;


create table t_user (
	user_id integer IDENTITY,
	user_birth datetime NOT NULL,
	created_at datetime NOT NULL,
	user_email varchar(64) NOT NULL,
	user_name varchar(255) NOT NULL,
	user_password varchar(255) NOT NULL,
	updated_at datetime NOT NULL,
	VERSION integer NOT NULL,
	roles varchar(100),
	CONSTRAINT pk_user_id PRIMARY KEY (user_id)
)

