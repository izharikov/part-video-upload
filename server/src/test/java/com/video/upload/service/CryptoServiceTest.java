package com.video.upload.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Here we will test Crypto service with MD5, SHA-1, SHA-256 algorithms
 *
 * @author Ihar Zharykau
 */
@RunWith(Parameterized.class)
public class CryptoServiceTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"MD5", "afdsfs", "C857FAC9F722F8FDACC3513E4AD259F1"},
                {"MD5", "Hello, world!", "6CD3556DEB0DA54BCA060B4C39479839"},
                {"SHA-1", "some string to encrypt", "1270ff7eb9b5a9c37fbd9afa09ddaf064d061c2d"},
                {"SHA-1", "another string", "22022c8894812eade94ee6177446077a119acb22"},
                {"SHA-256", "another string", "81e7826a5821395470e5a2fed0277b6a40c26257512319875e1d70106dcb1ca0"},
                {"SHA-256", "some string to encrypt", "b0cfcd2aabef369d1303c0da58c3a523b919dc0eb31e35b7a6744410a74fd632"}
        });
    }

    private CryptoService cryptoService;

    @Before
    public void configure() {
        ServiceConfiguration serviceConfiguration = new ServiceConfiguration();
        serviceConfiguration.setHashMethod(method);
        cryptoService = new CryptoService(serviceConfiguration);
    }

    @Parameterized.Parameter
    public String method;

    @Parameterized.Parameter(1)
    public String inputString;

    @Parameterized.Parameter(2)
    public String expectedHash;

    @Test
    public void hashIsValid() {
        assertTrue(expectedHash.equalsIgnoreCase(cryptoService.hash(inputString.getBytes())));
    }
}
