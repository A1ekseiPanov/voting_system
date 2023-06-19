DELETE
FROM user_role;
DELETE
FROM users;
DROP SEQUENCE IF EXISTS SYSTEM_SEQUENCE_C6FED0E2_3C84_4016_9FA9_05E739003C7B;

CREATE SEQUENCE SYSTEM_SEQUENCE_C6FED0E2_3C84_4016_9FA9_05E739003C7B START WITH 1;

INSERT INTO USERS (email, name, surname, password)
VALUES ('user@yan.ru', 'Вася', 'Петров', '{noop}12345');

INSERT INTO USERS (email, name, surname, password)
VALUES ('user@ya.ru', 'Павел', 'Павлов', '{noop}54321');

INSERT INTO USER_ROLE (USER_ID, ROLE)
VALUES (1, 'USER'),
       (2, 'USER'),
       (2, 'ADMIN');

INSERT INTO RESTAURANT(NAME, DESCRIPTION)
VALUES ('Утка в яблоке', 'Ресторан утки'),
       ('Пончики', 'Огромный ассортимент пончиков'),
       ('Шаурмяшка', 'Шаурма и шаверма всех размеров');

INSERT INTO MENU(NAME, RESTAURANT_ID, OFFER_DATE)
VALUES ('дневное меню', 1, '2023-05-10'),
       ('завтрак', 2, '2023-05-10'),
       ('ужин', 3, '2023-05-10'),
       ('завтрак', 1, now()),
       ('обед из пончиков', 2, now()),
       ('ужин', 3, now());

INSERT INTO DISH (NAME, PRICE, MENU_ID)
VALUES ('утка по пекински', 235, 1),
       ('рис с овощами', 54, 1),
       ('хлеб', 5, 1),
       ('кофе', 120, 1),
       ('10 пончиков в сахарной пудре', 160, 2),
       ('кофе с сиропом', 150, 2),
       ('шаурма биг сайз', 210, 3),
       ('чай', 40, 3),
       ('чай зеленый', 40, 4),
       ('чай ромашковый', 40, 5),
       ('травяной чай', 40, 6);

INSERT INTO VOTE (RESTAURANT_ID, USER_ID, DATE_VOTE)
VALUES (1, 1, '2023-05-10'),
       (1, 2, '2023-05-10'),
       (2, 2, now()),
       (3, 1, now());