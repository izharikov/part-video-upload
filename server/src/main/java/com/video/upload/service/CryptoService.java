package com.video.upload.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Ihar Zharykau
 */
@Component
public class CryptoService {
    private final MessageDigest messageDigest;

    @Autowired
    public CryptoService(ServiceConfiguration serviceConfiguration) throws NoSuchAlgorithmException{
        this(serviceConfiguration.getHashMethod());
    }

    private CryptoService(String alg) throws NoSuchAlgorithmException {
        messageDigest = MessageDigest.getInstance(alg);
    }

    public String hash(byte[] bytes){
        byte[] byteResult;
        synchronized (messageDigest) {
            messageDigest.update(bytes);
            byteResult = messageDigest.digest();
        }
        StringBuilder strb = new StringBuilder();
        for (byte b : byteResult){
            strb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return strb.toString();
    }
}
