package org.fizz_buzz.validation;

import org.fizz_buzz.dao.MatchDAO;

public class DomainSpecificValidator {

    private volatile static DomainSpecificValidator instance;

    private DomainSpecificValidator() {
    }

    public static DomainSpecificValidator getInstance() {
        if (instance == null) {
            synchronized (DomainSpecificValidator.class) {
                if (instance == null) {
                    instance = new DomainSpecificValidator();
                }
            }
        }
        return instance;
    }


    public boolean isMatchPageExists(int pageNumber) {
        if (pageNumber < 1) {
            return false;
        }

        MatchDAO matchDAO = MatchDAO.getInstance();
        return pageNumber <= matchDAO.totalPages(MatchDAO.DEFAULT_PAGE_SIZE);
    }
}
