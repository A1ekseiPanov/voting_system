DELETE
FROM user_role;
DELETE
FROM users;
DROP SEQUENCE IF EXISTS SYSTEM_SEQUENCE_C6FED0E2_3C84_4016_9FA9_05E739003C7B;

CREATE SEQUENCE SYSTEM_SEQUENCE_C6FED0E2_3C84_4016_9FA9_05E739003C7B START WITH 1;

INSERT INTO USERS (email, name, surname, password)
VALUES ('user@yan.ru', 'Вася', 'Петров', '{noop}12345');

INSERT INTO USERS (email, name, surname, password)
VALUES ('user@ya.ru', 'Вас', 'Пров', '{noop}54321');

INSERT INTO USER_ROLE (USER_ID, ROLE)
VALUES (1, 'USER'),
       (2, 'USER'),
       (2, 'ADMIN');

INSERT INTO RESTAURANT(NAME, DESCRIPTION)
VALUES ('Утка в яблоке', 'Ресторан утки'),
       ('Пончики', 'Огромный ассортимент пончиков'),
       ('Шаурмяшка', 'Шаурма и шаверма всех размеров');

INSERT INTO MENU(NAME, RESTAURANT_ID)
VALUES ('дневное меню', 1),
       ('завтрак', 2),
       ('ужин', 3);

INSERT INTO DISH (NAME, PRICE, MENU_ID)
VALUES ('утка по пекински', 235.4, 1),
       ('рис с овощами', 54.33, 1),
       ('хлеб', 5, 1),
       ('кофе', 120, 1),
       ('10 пончиков в сахарной пудре', 160, 2),
       ('кофе с сиропом', 150, 2),
       ('Шаурма биг сайз', 210, 3),
       ('чай', 40, 3);

INSERT INTO VOTE (RESTAURANT_ID, USER_ID)
VALUES (1, 1),
       (1, 2);