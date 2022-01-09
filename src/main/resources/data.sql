INSERT INTO USER ( USERNAME , PASSWORD , FORENAME , LASTNAME) values ( 'user1@lala.fr' , '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq' , 'user' , '1' );
INSERT INTO USER ( USERNAME , PASSWORD , FORENAME , LASTNAME) values ( 'user2@lala.fr' , '$2a$10$kK/Il8IGJrbTijoNSmBVRu2GJpOPTHkh12YIEsTsjPHmF7ZLw5mnO' , 'user' , '2' );
INSERT INTO USER ( USERNAME , PASSWORD , FORENAME , LASTNAME) values ( 'user3@lala.fr' , '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq' , 'user' , '3' );
INSERT INTO USER ( USERNAME , PASSWORD , FORENAME , LASTNAME) values ( 'user4@lala.fr' , '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq' , 'user' , '4' );
INSERT INTO USER ( USERNAME , PASSWORD , FORENAME , LASTNAME) values ( 'user5@lala.fr' , '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq' , 'user' , '5' );
INSERT INTO USER ( USERNAME , PASSWORD , FORENAME , LASTNAME) values ( 'user6@lala.fr' , '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq' , 'user' , '6' );
INSERT INTO USER ( USERNAME , PASSWORD , FORENAME , LASTNAME) values ( 'user7@lala.fr' , '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq' , 'user' , '7' );
INSERT INTO USER ( USERNAME , PASSWORD , FORENAME , LASTNAME) values ( 'user8@lala.fr' , '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq' , 'user' , '8' );
INSERT INTO USER ( USERNAME , PASSWORD , FORENAME , LASTNAME) values ( 'user9@lala.fr' , '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq' , 'user' , '9' );
INSERT INTO USER ( USERNAME , PASSWORD , FORENAME , LASTNAME , SUBSCRIPTION_DATE , FREE_ACCESS ) values ( 'Vale@lala.fr' , '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq' , 'Valérian' , 'Jacobi' , CURRENT_DATE , true );
INSERT INTO USER ( USERNAME , PASSWORD , FORENAME , LASTNAME) values ( 'Ludo@lala.fr' , '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq' , 'Ludovic' , 'Beyer' );
INSERT INTO USER ( USERNAME , PASSWORD , FORENAME , LASTNAME) values ( 'Max@lala.fr' , '$2a$10$PKXm.mhK6wib2AySUciHReGjF7zc.Xz8ZX5jephttRVEyQtZtrDlq' , 'Maxime' , 'Cron' );

INSERT INTO SEANCE ( NAME , MAX_SPOT , START_DATE , DURATION , LOCATION , COACH_ID ) values ( 'WOD PLUS' , 12 , '2021-12-24T10:08' , 90 , null , null );
INSERT INTO SEANCE ( NAME , MAX_SPOT , START_DATE , DURATION , LOCATION , COACH_ID ) values ( 'WOD' , 12 , '2021-12-20 19:30:00' , 90 , null , null );
INSERT INTO SEANCE ( NAME , MAX_SPOT , START_DATE , DURATION , LOCATION , COACH_ID ) values ( 'WOD' , 9 , '2021-12-21 18:00:00' , 90 , null , null );
INSERT INTO SEANCE ( NAME , MAX_SPOT , START_DATE , DURATION , LOCATION , COACH_ID ) values ( 'SPECIAL HALTERO' , 250 , '2021-12-21 19:00:00' , 90 , null , null );
INSERT INTO SEANCE ( NAME , MAX_SPOT , START_DATE , DURATION , LOCATION , COACH_ID ) values ( 'QUICK DEATH' , 12 , '2021-12-22 19:00:00' , 90 , null , null );
INSERT INTO SEANCE ( NAME , MAX_SPOT , START_DATE , DURATION , LOCATION , COACH_ID ) values ( 'SPECIAL CARDIO' , 5 , '2021-12-23 20:00:00' , 90 , null , null );

INSERT INTO ROLE values ( null , 'ROLE_MEMBER' );
INSERT INTO ROLE values ( null , 'ROLE_COACH' );
INSERT INTO ROLE values ( null , 'ROLE_OFFICE' );

INSERT INTO USER_ROLES values ( 1 , 1 );
INSERT INTO USER_ROLES values ( 2 , 1 );
INSERT INTO USER_ROLES values ( 3 , 1 );
INSERT INTO USER_ROLES values ( 4 , 1 );
INSERT INTO USER_ROLES values ( 5 , 1 );
INSERT INTO USER_ROLES values ( 6 , 1 );
INSERT INTO USER_ROLES values ( 7 , 1 );
INSERT INTO USER_ROLES values ( 8 , 1 );
INSERT INTO USER_ROLES values ( 9 , 1 );
INSERT INTO USER_ROLES values ( 10 , 1 );
INSERT INTO USER_ROLES values ( 10 , 2 );
INSERT INTO USER_ROLES values ( 10 , 3 );
INSERT INTO USER_ROLES values ( 11 , 1 );
INSERT INTO USER_ROLES values ( 11 , 2 );
INSERT INTO USER_ROLES values ( 12 , 1 );
INSERT INTO USER_ROLES values ( 12 , 3 );

INSERT INTO SEANCE_USERS values ( 6 , 1 );
INSERT INTO SEANCE_USERS values ( 6 , 5 );
INSERT INTO SEANCE_USERS values ( 6 , 4 );
INSERT INTO SEANCE_USERS values ( 6 , 6 );
INSERT INTO SEANCE_USERS values ( 6 , 12 );
INSERT INTO SEANCE_USERS values ( 3 , 5 );
INSERT INTO SEANCE_USERS values ( 3 , 11 );
INSERT INTO SEANCE_USERS values ( 2 , 11 );
INSERT INTO SEANCE_USERS values ( 1 , 12 );

INSERT INTO GUEST_SUBSCRIPTION ( ID , SEANCE_ID , GUEST_NAME , COACH_NAME , COMMENT ) values ( null , 6 , 'Guest 1' , 'Coach X' , 'comment 1' );
INSERT INTO GUEST_SUBSCRIPTION ( ID , SEANCE_ID , GUEST_NAME , COACH_NAME , COMMENT ) values ( null , 6 , 'Guest 2' , 'Coach Y' , 'comment 2' );
INSERT INTO GUEST_SUBSCRIPTION ( ID , SEANCE_ID , GUEST_NAME , COACH_NAME , COMMENT ) values ( null , 6 , 'Guest 3' , 'Coach X' , 'comment 3' );
INSERT INTO GUEST_SUBSCRIPTION ( ID , SEANCE_ID , GUEST_NAME , COACH_NAME , COMMENT ) values ( null , 7 , 'Guest 1' , 'Coach Y' , 'comment 4' );
INSERT INTO GUEST_SUBSCRIPTION ( ID , SEANCE_ID , GUEST_NAME , COACH_NAME , COMMENT ) values ( null , 7 , 'Guest 7' , 'Coach X' , 'comment 5' );

/*
SELECT * FROM ROLE ;
SELECT * FROM SEANCE ;
SELECT * FROM SEANCE_USERS ;
SELECT * FROM SEANCE_WAITING ;
SELECT * FROM USER ;
SELECT * FROM USER_ROLES ;
SELECT * FROM GUEST_SUBSCRIPTION ;
*/
