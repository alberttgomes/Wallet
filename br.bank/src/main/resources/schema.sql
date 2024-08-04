CREATE TABLE IF NOT EXISTS WALLETS (
    ID SERIAL PRIMARY KEY, FULL_NAME VARCHAR(100), CPF INT, EMAIL VARCHAR(100),
    "PASSWORD" VARCHAR(100), "TYPE" INT, BALANCE DECIMAL(10,2), COUNTID VARCHAR(20)
);

CREATE UNIQUE INDEX IF NOT EXISTS cpf_index ON WALLETS (CPF);

CREATE UNIQUE INDEX IF NOT EXISTS email_idx ON WALLETS (EMAIl);

CREATE TABLE IF NOT EXISTS TRANSACTIONS (
    ID SERIAL PRIMARY KEY, PAYER INT, PAYEE INT, "TYPE" VARCHAR(15),"VALUE" DECIMAL(10,2),
    CREATED_AT TIMESTAMP,
    FOREIGN KEY (PAYER) REFERENCES WALLETS (ID),
    FOREIGN KEY (PAYEE) REFERENCES WALLETS (ID)
)