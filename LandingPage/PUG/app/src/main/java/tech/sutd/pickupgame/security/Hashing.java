package tech.sutd.pickupgame.security;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Hashing {

    public static String makeSHA1Hash(MessageDigest md, String input) {
        md.reset();
        byte[] buffer = input.getBytes(StandardCharsets.UTF_8);
        md.update(buffer);
        byte[] digest = md.digest();

        StringBuilder hexStr = new StringBuilder();
        for (byte b : digest) {
            hexStr.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return hexStr.toString();
    }

}
