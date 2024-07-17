package com.example;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class KeyManager {
    public static void generateKey(String filePath) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // for example, 256-bit AES
        SecretKey secretKey = keyGen.generateKey();

        try (FileOutputStream keyOut = new FileOutputStream(filePath)) {
            keyOut.write(secretKey.getEncoded());
        }
    }

    public static SecretKey loadKey(String filePath) throws IOException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filePath));
        return new javax.crypto.spec.SecretKeySpec(keyBytes, "AES");
    }
}
