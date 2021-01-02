package com.accounting.accountingapi;

import com.accounting.accountingapi.resource.PersonResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountingApiApplicationTests {

    @Autowired
    private PersonResource personResource;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(personResource);
    }

}
