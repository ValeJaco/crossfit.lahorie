INSERT INTO user (USERNAME, PASSWORD, FORENAME, LASTNAME)
values (LOWER('user1@test.fr'), '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq', 'user', '1');
INSERT INTO user (USERNAME, PASSWORD, FORENAME, LASTNAME)
values (LOWER('user2@test.fr'), '$2a$10$kK/Il8IGJrbTijoNSmBVRu2GJpOPTHkh12YIEsTsjPHmF7ZLw5mnO', 'user', '2');
INSERT INTO user (USERNAME, PASSWORD, FORENAME, LASTNAME)
values (LOWER('user3@test.fr'), '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq', 'user', '3');
INSERT INTO user (USERNAME, PASSWORD, FORENAME, LASTNAME)
values (LOWER('user4@test.fr'), '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq', 'user', '4');
INSERT INTO user (USERNAME, PASSWORD, FORENAME, LASTNAME)
values (LOWER('user5@test.fr'), '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq', 'user', '5');
INSERT INTO user (USERNAME, PASSWORD, FORENAME, LASTNAME)
values (LOWER('user6@test.fr'), '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq', 'user', '6');
INSERT INTO user (USERNAME, PASSWORD, FORENAME, LASTNAME)
values (LOWER('user7@test.fr'), '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq', 'user', '7');
INSERT INTO user (USERNAME, PASSWORD, FORENAME, LASTNAME)
values (LOWER('user8@test.fr'), '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq', 'user', '8');
INSERT INTO user (USERNAME, PASSWORD, FORENAME, LASTNAME)
values (LOWER('user9@test.fr'), '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq', 'user', '9');
INSERT INTO user (USERNAME, PASSWORD, FORENAME, LASTNAME, SUBSCRIPTION_DATE, FREE_ACCESS)
values (LOWER('Vale@test.fr'), '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq', 'Valérian', 'Jacobi',
        CURRENT_DATE, true);
INSERT INTO user (USERNAME, PASSWORD, FORENAME, LASTNAME)
values (LOWER('Ludo@test.fr'), '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq', 'Ludovic', 'Beyer');
INSERT INTO user (USERNAME, PASSWORD, FORENAME, LASTNAME)
values (LOWER('Max@test.fr'), '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq', 'Maxime', 'Cron');

INSERT INTO seance (NAME, MAX_SPOT, START_DATE, DURATION, LOCATION, COACH_ID, Unsubscription_HOURS_LIMIT)
values ('WOD PLUS', 12, '2022-02-02T10:08', 90, null, null, 6);
INSERT INTO seance (NAME, MAX_SPOT, START_DATE, DURATION, LOCATION, COACH_ID, Unsubscription_HOURS_LIMIT, SEANCE_TYPE)
values ('WOD', 12, '2022-02-02 19:30:00', 90, null, null, 6 , 'ROLE_OFFICE');
INSERT INTO seance (NAME, MAX_SPOT, START_DATE, DURATION, LOCATION, COACH_ID, Unsubscription_HOURS_LIMIT)
values ('WOD', 2, '2022-02-03 18:00:00', 90, null, null, 6);
INSERT INTO seance (NAME, MAX_SPOT, START_DATE, DURATION, LOCATION, COACH_ID, Unsubscription_HOURS_LIMIT)
values ('SPECIAL HALTERO', 250, '2022-02-06 19:00:00', 90, null, null, 6);
INSERT INTO seance (NAME, MAX_SPOT, START_DATE, DURATION, LOCATION, COACH_ID, Unsubscription_HOURS_LIMIT, SEANCE_TYPE)
values ('QUICK DEATH', 12, '2022-02-05 19:00:00', 90, null, null, 6, 'ROLE_COACH');
INSERT INTO seance (NAME, MAX_SPOT, START_DATE, DURATION, LOCATION, COACH_ID, Unsubscription_HOURS_LIMIT)
values ('SPECIAL CARDIO', 5, '2022-02-01 20:00:00', 90, null, null, 6);

INSERT INTO role
values (null, 'ROLE_MEMBER');
INSERT INTO role
values (null, 'ROLE_COACH');
INSERT INTO role
values (null, 'ROLE_OFFICE');
INSERT INTO role
values (null, 'ROLE_CROSSFIT');
INSERT INTO role
values (null, 'ROLE_HALTERO');

INSERT INTO user_roles
values (1, 1);
INSERT INTO user_roles
values (2, 1);
INSERT INTO user_roles
values (3, 1);
INSERT INTO user_roles
values (4, 1);
INSERT INTO user_roles
values (5, 1);
INSERT INTO user_roles
values (6, 1);
INSERT INTO user_roles
values (7, 1);
INSERT INTO user_roles
values (8, 1);
INSERT INTO user_roles
values (9, 1);
INSERT INTO user_roles
values (10, 1);
INSERT INTO user_roles
values (10, 2);
INSERT INTO user_roles
values (10, 3);
INSERT INTO user_roles
values (11, 1);
INSERT INTO user_roles
values (12, 1);
INSERT INTO user_roles
values (12, 3);

INSERT INTO seance_users
values (6, 1);
INSERT INTO seance_users
values (6, 5);
INSERT INTO seance_users
values (6, 4);
INSERT INTO seance_users
values (6, 6);
INSERT INTO seance_users
values (6, 10);
INSERT INTO seance_users
values (3, 5);
INSERT INTO seance_users
values (3, 11);
INSERT INTO seance_users
values (2, 10);
INSERT INTO seance_users
values (1, 12);

INSERT INTO planning (ID, IS_ACTIVE, NAME)
values (null, true, 'lala');
INSERT INTO seance_planning
values (null, null, 0, 90, null, 12, 'Dimanche 1', 1, 'ROLE_MEMBER', '18:30:00', 13);
INSERT INTO seance_planning
values (null, null, 0, 60, null, 9, 'Dimanche 2', 1, 'ROLE_MEMBER', '19:30:00', 13);
INSERT INTO seance_planning
values (null, 1, 3, 90, null, 12, 'Mercredi 1', 1, 'ROLE_COACH', '19:25:00', 6);
INSERT INTO seance_planning
values (null, null, 1, 90, null, 12, 'Lundi 1', 1, 'ROLE_MEMBER', '18:30:00', 6);


INSERT INTO guest_subscription (ID, SEANCE_ID, GUEST_NAME, COACH_NAME, COMMENT)
values (null, 6, 'Guest 1', 'Coach X', 'comment 1');
INSERT INTO guest_subscription (ID, SEANCE_ID, GUEST_NAME, COACH_NAME, COMMENT)
values (null, 6, 'Guest 2', 'Coach Y', 'comment 2');
INSERT INTO guest_subscription (ID, SEANCE_ID, GUEST_NAME, COACH_NAME, COMMENT)
values (null, 6, 'Guest 3', 'Coach X', 'comment 3');
INSERT INTO guest_subscription (ID, SEANCE_ID, GUEST_NAME, COACH_NAME, COMMENT)
values (null, 7, 'Guest 1', 'Coach Y', 'comment 4');
INSERT INTO guest_subscription (ID, SEANCE_ID, GUEST_NAME, COACH_NAME, COMMENT)
values (null, 7, 'Guest 7', 'Coach X', 'comment 5');
