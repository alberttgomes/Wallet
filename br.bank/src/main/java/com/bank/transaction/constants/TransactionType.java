package com.bank.transaction.constants;

public class TransactionType {

    public static final String CRYPT = "CRYPT";

    public static final String DEPOSIT = "DEPOSIT";

    public static final String LOANS = "LOANS";

    public static final String TRANSFER = "TRANSFER";

    public static final String WITHDRAW = "WITHDRAW";

    public static String[] getAllTransactionTypes() {
        return new String[]{CRYPT, DEPOSIT, LOANS, TRANSFER, WITHDRAW};
    }

}
