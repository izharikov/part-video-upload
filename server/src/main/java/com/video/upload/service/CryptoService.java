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

    private String hashMethod;

    @Autowired
    public CryptoService(ServiceConfiguration serviceConfiguration) {
        hashMethod = serviceConfiguration.getHashMethod();
    }


    public String hash(byte[] bytes){
        byte[] byteResult;
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(hashMethod);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if ( messageDigest == null){
            return null;
        }
        messageDigest.update(bytes);
        byteResult = messageDigest.digest();
        StringBuilder strb = new StringBuilder();
        for (byte b : byteResult) {
            strb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return strb.toString();
    }
}
