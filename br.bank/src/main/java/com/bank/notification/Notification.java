package com.bank.notification;

/**
 * @author Albert Cabral
 * @param message
 */
public record Notification(String message) {

    public static boolean notificationValid(Notification notification) {
        return notification.message.matches("([\\w.+\\s*]+)");
    }

}
