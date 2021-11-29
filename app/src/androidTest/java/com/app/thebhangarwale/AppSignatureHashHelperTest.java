package com.app.thebhangarwale;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import java.security.Signature;

public class AppSignatureHashHelperTest {

    @Test
    public void test_hash_function(){
        AppSignatureHashHelper appSignatureHashHelper = new
                AppSignatureHashHelper(ApplicationProvider.getApplicationContext());
        String signature = appSignatureHashHelper.getAppSignatures().get(0);
        System.out.println("Signature : "+signature);
    }

}
