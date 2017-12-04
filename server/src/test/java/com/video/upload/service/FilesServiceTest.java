package com.video.upload.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * And here we use SpringJUnit4ClassRunner to allow spring dependency injection
 * @author Ihar Zharykau
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FilesServiceTest {

    @Autowired
    FilesService filesService;

    @Before
    public void ensureServiceNotNull(){
        assertNotNull("Files service couldn't be null", filesService);
    }

    @Test
    public void partFileExists_existsFile(){
        String hashOfExistingFile = "4dba6aee7d2399eeccd8a37a6a7a7ecb89420e3bedad2dd5f414a98fe5ed326b";
        assertTrue(filesService.partFileExists(hashOfExistingFile));
    }

    @Test
    public void partFileExists_noSuchFile(){
        assertFalse(filesService.partFileExists("non exists file"));
    }

    @Test
    public void uploadFileAndCheckCreated(){
        String fileName = "file_for_test";
        String stringToWrite = "test string";
        filesService.uploadPartFile(fileName, stringToWrite.getBytes());
        assertTrue(filesService.partFileExists(fileName));
    }

}
