package com.example;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.SecureRandom;

public class FileEncryptor {
    private SecretKey key;

    public FileEncryptor(SecretKey key) {
        this.key = key;
    }

    public void encryptFile(String filePath) throws Exception {
        File inputFile = new File(filePath);
        byte[] inputBytes = Files.readAllBytes(inputFile.toPath());

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[cipher.getBlockSize()];
        secureRandom.nextBytes(iv);
        IvParameterSpec ivParams = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);

        byte[] outputBytes = cipher.doFinal(inputBytes);

        try (FileOutputStream outputStream = new FileOutputStream(inputFile)) {
            outputStream.write(iv);
            outputStream.write(outputBytes);
        }
    }
}

