package org.fizz_buzz.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomainSpecificValidatorTest {

    @Test
    void isMatchPageExists_NoNameLargePageNumber_ReturnFalse(){
        ParamValidator paramValidator = ParamValidator.getInstance();
        int pageNumber = 9000;

        assertFalse(paramValidator.isMatchPageExists(pageNumber));
    }

    @Test
    void isMatchPageExists_NoNameExistingPageNumber_ReturnTrue(){
        ParamValidator paramValidator = ParamValidator.getInstance();
        int pageNumber = 1;

        assertTrue(paramValidator.isMatchPageExists(pageNumber));
    }

    @Test
    void isMatchPageExists_NoNameNegativePageNumber_ReturnTrue(){
        ParamValidator paramValidator = ParamValidator.getInstance();
        int pageNumber = -1;

        assertFalse(paramValidator.isMatchPageExists(pageNumber));
    }
}