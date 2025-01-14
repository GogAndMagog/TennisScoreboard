package org.fizz_buzz.exception;

public class DAOException extends RuntimeException{
    private static final String PROBLEM_IN_DAO_CLASS = "Exception occurred in %s. %s.";

    public DAOException(String daoClassName, String problemDescription){
        super(PROBLEM_IN_DAO_CLASS.formatted(daoClassName, problemDescription));
    }

    public DAOException(String daoClassName, String problemDescription, Throwable e){
        super(PROBLEM_IN_DAO_CLASS.formatted(daoClassName, problemDescription), e);
    }
}
