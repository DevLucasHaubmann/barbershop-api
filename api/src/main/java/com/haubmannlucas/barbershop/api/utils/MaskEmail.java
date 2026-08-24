package com.haubmannlucas.barbershop.api.utils;

public class MaskEmail {

    public static final String maskEmail(String email) {
        if (email == null || !email.contains("@")) return "***";
        String[] parts = email.split("@");
        String name = parts[0];
        if (name.length() <= 2) return "***@" + parts[1];
        return name.charAt(0) + "***" + name.charAt(name.length() - 1) + "@" + parts[1];
    }
}
