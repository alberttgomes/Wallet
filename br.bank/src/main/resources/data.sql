DELETE FROM TRANSACTION;

DELETE FROM WALLET;

INSERT INTO WALLET (
        ID, FULL_NAME, CPF, EMAIL, "PASSWORD", "TYPE", BALANCE, "COUNTID")
VALUES (
            1, 'Albert Cabral', 12345678900, "albert.calbral@common.com", 'test', 1, 10000,00, xxx-5xy
       );

INSERT INTO WALLET (
        ID, FULL_NAME, CPF, EMAIL, "PASSWORD", "TYPE", BALANCE, "COUNTID")
VALUES (
           2, 'Camile Souza', 12345678901, "camile.souza@common.com", 'test', 2, 5000,00, xxx-5yz
       );

INSERT INTO WALLET (
    ID, FULL_NAME, CPF, EMAIL, "PASSWORD", "TYPE", BALANCE, "COUNTID")
VALUES (
           3, 'Mariah De Souza Cabral', 12345678902, "mariah.cabral@gold.com", 'test', 5, 15000,00, xxx-7yx
       );
INSERT INTO WALLET (
    ID, FULL_NAME, CPF, EMAIL, "PASSWORD", "TYPE", BALANCE, "COUNTID")
VALUES (
           4, 'Solange Gomez De Souza', 12345678904, "solange.souza@store.com", 'test', 5, 55000,00, xxz-7yy
       );