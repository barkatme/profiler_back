create TABLE PERMISSIONS (
    id serial primary key,
    name varchar(100) not null unique,
    created_at timestamp not null default CURRENT_TIMESTAMP
);

create TABLE USER_ROLES (
    id serial primary key,
    name varchar(100) not null unique,
    image varchar(100) not null default '',
    created_at timestamp not null default CURRENT_TIMESTAMP
);

--connects user_roles with it's permissions
create TABLE USER_ROLE_PERMISSIONS (
    id serial primary key,
    user_role_id int not null,
    permission_id int not null,
    created_at timestamp not null default CURRENT_TIMESTAMP,
    FOREIGN KEY (user_role_id) REFERENCES USER_ROLES (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES PERMISSIONS (id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE (user_role_id, permission_id)
);

--connects routes(url string without BASE_URL) with it's permissions
create TABLE URL_PERMISSIONS (
    id serial primary key,
    url varchar(100) not null,
    permission_id int not null,
    created_at timestamp not null default CURRENT_TIMESTAMP,
    FOREIGN KEY (permission_id) REFERENCES PERMISSIONS (id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE (url, permission_id)
);


create TABLE USERS (
    id serial primary key,
    active boolean,
    deleted boolean not null default false,
    email varchar(100) not null unique,
    password_hash varchar(100) not null,
    user_role_id int not null default 1,
    login varchar(100),
    token varchar(255),
    first_name varchar(100),
    last_name varchar(100),
    about varchar(100),
    created_at timestamp not null default CURRENT_TIMESTAMP,
    FOREIGN KEY (user_role_id) REFERENCES USER_ROLES (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create TABLE SERVICE_INFO (
    id serial primary key,
    name varchar(100) not null,
    link varchar(100) not null,
    image varchar(100) not null,
    created_at timestamp not null default CURRENT_TIMESTAMP
);

create TABLE USER_SERVICE (
    id serial primary key,
    user_id int not null,
    service_id int not null,
    link varchar(100) not null unique,
    created_at timestamp not null default CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (service_id) REFERENCES SERVICE_INFO (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create TABLE VIEWED_USER (
    id serial primary key,
    user_id int not null,
    viewed_user_id int not null,
    last_time timestamp not null default CURRENT_TIMESTAMP,
     FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE ON UPDATE CASCADE,
     FOREIGN KEY (viewed_user_id) REFERENCES USERS (id) ON DELETE CASCADE ON UPDATE CASCADE,
     UNIQUE (user_id, viewed_user_id)
);

create TABLE SAVED_USERS (
     id serial primary key,
     user_id int not null,
     saved_user_id int not null,
     created_at timestamp not null default CURRENT_TIMESTAMP,
     FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE ON UPDATE CASCADE,
     FOREIGN KEY (saved_user_id) REFERENCES USERS (id) ON DELETE CASCADE ON UPDATE CASCADE,
     UNIQUE (user_id, saved_user_id)
);