package com.example;

import javax.crypto.SecretKey;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelEncryptor {
    private SecretKey key;
    private int numThreads;

    public ParallelEncryptor(SecretKey key, int numThreads) {
        this.key = key;
        this.numThreads = numThreads;
    }

    public void encryptFiles(List<String> filePaths) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (String filePath : filePaths) {
            executor.submit(() -> {
                FileEncryptor encryptor = new FileEncryptor(key);
                try {
                    encryptor.encryptFile(filePath);
                    System.out.println("Encrypted: " + filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all tasks to finish
        }
    }

    public static void main(String[] args) throws Exception {
        KeyManager.generateKey("secret.key");
        SecretKey key = KeyManager.loadKey("secret.key");

        String directoryPath = "path/to/your/files";
        File directory = new File(directoryPath);
        List<String> filePaths = Arrays.asList(directory.list((dir, name) -> new File(dir, name).isFile()));

        ParallelEncryptor parallelEncryptor = new ParallelEncryptor(key, 4); // Example with 4 threads
        parallelEncryptor.encryptFiles(filePaths);
    }
}
