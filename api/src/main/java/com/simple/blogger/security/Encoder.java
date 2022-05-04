package com.simple.blogger.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Encoder implements PasswordEncoder {

    private static final int ENCRYPTION_KEY = 8;
    @Override
    public String encode(CharSequence decoded){

        String encoded = "";
        for (int i=0; i< decoded.length(); i++)
        {
            if (Character.isUpperCase(decoded.charAt(i)))
            {
                char ch = (char)(((int)decoded.charAt(i) +
                        ENCRYPTION_KEY - 65) % 26 + 65);
                encoded += ch;
            }
            else
            {
                char ch = (char)(((int)decoded.charAt(i) +
                        ENCRYPTION_KEY - 97) % 26 + 97);
                encoded += ch;
            }
        }
        return encoded;
    }
    public String decode(String encoded){
        String decoded = "";
        for (int i=0; i< encoded.length(); i++)
        {
            if (Character.isUpperCase(encoded.charAt(i)))
            {
                char ch = (char)(((int)encoded.charAt(i) -
                        ENCRYPTION_KEY - 65) % 26 + 65);
                decoded += ch;
            }
            else
            {
                char ch = (char)(((int)encoded.charAt(i) -
                        ENCRYPTION_KEY - 97) % 26 + 97);
                decoded += ch;
            }
        }
        return decoded;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}