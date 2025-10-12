package com.bookmanager.Helpers;


import java.text.Normalizer;

public class TextTranf {
    public static String removeVietnameseAccents(String text) {
        if (text == null) return null;
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
