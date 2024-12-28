package org.fizz_buzz.controller.filter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParamValidatorTest {

    @Test
    void isLongerThan_LongerValue_ReturnTrue() {
        ParamValidator paramValidator = ParamValidator.getInstance();

        assertTrue(paramValidator.isLongerThan(3, "AAAA"));
    }

    @Test
    void isLongerThan_NonLongerValue_ReturnFalse() {
        ParamValidator paramValidator = ParamValidator.getInstance();

        assertFalse(paramValidator.isLongerThan(3, "AA"));
    }

    @Test
    void isEmpty_NullValue_ReturnTrue() {
        ParamValidator paramValidator = ParamValidator.getInstance();

        assertTrue(paramValidator.isEmpty(null));
    }

    @Test
    void isEmpty_EmptyValue_ReturnTrue() {
        ParamValidator paramValidator = ParamValidator.getInstance();
        String pattern = "";

        assertTrue(paramValidator.isEmpty(pattern));
    }

    @Test
    void isEmpty_NonEmptyValue_ReturnFalse() {
        ParamValidator paramValidator = ParamValidator.getInstance();
        String pattern = "123";

        assertFalse(paramValidator.isEmpty(pattern));
    }

    @Test
    void isInteger_IntegerValue_ReturnTrue() {
        ParamValidator paramValidator = ParamValidator.getInstance();
        String integerValue = "1";

        assertTrue(paramValidator.isInteger(integerValue));
    }

    @Test
    void isInteger_NotIntegerValue_ReturnTrue() {
        ParamValidator paramValidator = ParamValidator.getInstance();
        String nonIntegerValue = "awsdcaca";

        assertFalse(paramValidator.isInteger(nonIntegerValue));
    }

    @Test
    void isNegative_NegativeIntegerValue_ReturnTrue() {
        ParamValidator paramValidator = ParamValidator.getInstance();
        String negativeValue = "-1";

        assertTrue(paramValidator.isNegative(negativeValue));
    }

    @Test
    void isNegative_PositiveIntegerValue_ReturnFalse() {
        ParamValidator paramValidator = ParamValidator.getInstance();
        String positiveValue = "1";

        assertFalse(paramValidator.isNegative(positiveValue));
    }

    @Test
    void isNegative_NonIntegerValue_ReturnFalse() {
        ParamValidator paramValidator = ParamValidator.getInstance();
        String nonIntegerValue = "sadaadsa";

        assertFalse(paramValidator.isNegative(nonIntegerValue));
    }

    @Test
    void isUUID_ValidUUID_ReturnTrue() {
        ParamValidator paramValidator = ParamValidator.getInstance();
        String validUUID = "9aab33d5-0cfc-412d-bbfe-79ac357211e7";

        assertTrue(paramValidator.isUUID(validUUID));
    }

    @Test
    void isUUID_NonValidUUID_ReturnFalse() {
        ParamValidator paramValidator = ParamValidator.getInstance();
        String nonValidUUID = "9aab33d5-0cfc-412d-bbfe-79ac357211e";

        assertFalse(paramValidator.isUUID(nonValidUUID));
    }

    @Test
    void isNotUnique_UniqueValue_ReturnFalse() {
        ParamValidator paramValidator = ParamValidator.getInstance();
        String[] uniqueValues = { "1", "2" };

        assertFalse(paramValidator.isNotUnique(uniqueValues));
    }

    @Test
    void isNotUnique_NonUniqueValue_ReturnTrue() {
        ParamValidator paramValidator = ParamValidator.getInstance();
        String[] nonUniqueValues = { "1", "2", "2"};

        assertTrue(paramValidator.isNotUnique(nonUniqueValues));
    }

    @Test
    void isNotUnique_NonUniqueValueWithWhiteSpaces_ReturnTrue() {
        ParamValidator paramValidator = ParamValidator.getInstance();
        String[] nonUniqueValues = { "1 1", "1     1"};

        assertTrue(paramValidator.isNotUnique(nonUniqueValues));
    }


    @Test
    void isObscene_ObsceneWord_ReturnTrue()
    {
        ParamValidator paramValidator = ParamValidator.getInstance();
        String obsceneWord = "sex";

        assertTrue(paramValidator.isObscene(obsceneWord));
    }

    @Test
    void isObscene_NonObsceneWord_ReturnFalse()
    {
        ParamValidator paramValidator = ParamValidator.getInstance();
        String nonObsceneWord = "apple";

        assertFalse(paramValidator.isObscene(nonObsceneWord));
    }

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

    @Test
    void isMatchPageExists_WithNameLargePageNumber_ReturnFalse(){
        ParamValidator paramValidator = ParamValidator.getInstance();
        int pageNumber = 9000;
        String playerName = "Oleg";

        assertFalse(paramValidator.isMatchPageExists(pageNumber, playerName));
    }

    @Test
    void isMatchPageExists_WithNameExistingPageNumber_ReturnTrue(){
        ParamValidator paramValidator = ParamValidator.getInstance();
        int pageNumber = 1;
        String playerName = "Oleg";

        assertTrue(paramValidator.isMatchPageExists(pageNumber, playerName));
    }
}