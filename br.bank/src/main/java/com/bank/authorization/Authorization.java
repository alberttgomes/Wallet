package com.bank.authorization;

/**
 * @author Albert Cabral
 * @param message
 */
public record Authorization(String message) {

    public boolean isAuthorized() {
        return message.equals("authorized");
    }

}
