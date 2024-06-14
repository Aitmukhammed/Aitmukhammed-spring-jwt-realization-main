```
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
select * from roles;
select * from users;
select * from user_roles ur;

```

# Signup
`POST localhost:8080/api/auth/signup`
```
{
    "username": "admin",
    "email": "admin@gmail.com",
    "password": "12345678",
    "role": ["admin, moderator"]
}
```
