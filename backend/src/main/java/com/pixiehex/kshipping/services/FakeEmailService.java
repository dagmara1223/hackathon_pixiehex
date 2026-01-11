package com.pixiehex.kshipping.services;

import org.springframework.stereotype.Service;

@Service
public class FakeEmailService {

    public void sendEmail(String to, String subject, String body) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n");
        System.out.println(" ------------------------------------------------------------- ");
        System.out.println("   [FAKE SMTP SERVER] Sending email...");
        System.out.println("   TO:      " + to);
        System.out.println("   SUBJECT: " + subject);
        System.out.println("   -------------------------------------------------------------");
        System.out.println("   CONTENT:");
        System.out.println("   " + body.replace("\n", "\n   ")); 
        System.out.println("-------------------------------------------------------------");
        System.out.println("\n");
    }
}