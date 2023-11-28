package com.gabriella.databases.services.email;

public interface EmailService {

    void sendRegistrationEmail(String userEmail, String userName, long userId);
}
