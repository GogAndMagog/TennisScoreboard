package org.fizz_buzz.controller.filter;

import org.fizz_buzz.dao.MatchDAO;
import org.fizz_buzz.dao.ObsceneVocabularyFileDAO;
import org.fizz_buzz.dao.PlayerDAO;
import org.fizz_buzz.dao.VocabularyDAO;

public class ParamValidator {

    private final VocabularyDAO obsceneVocabularyDAO = ObsceneVocabularyFileDAO.getInstance();

    private volatile static ParamValidator instance;

    private ParamValidator() {
    }

    public static ParamValidator getInstance() {
        if (instance == null) {
            synchronized (PlayerDAO.class) {
                if (instance == null) {
                    instance = new ParamValidator();
                }
            }
        }
        return instance;
    }

    public boolean isLongerThan(int maxLength, String param) {
        if(param == null){
            return false;
        }

        return param.length() > maxLength;
    }

    public boolean isEmpty(String param) {
        return param == null || param.isEmpty() || param.matches("^\\s*$");
    }

    public boolean isInteger(String param) {
        return param != null && param.matches("\\d+");
    }

    public boolean isNegative(String param) {
        return param != null && param.matches("-\\d+");
    }

    public boolean isUUID(String param) {
        return param.matches("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");
    }

    public boolean isNotUnique(String... params) {
        for (int i = 0; i < params.length; i++) {
            for (int j = 0; j < params.length; j++) {
                if (i != j && params[i].replaceAll("\\s+", " ")
                        .equals(params[j].replaceAll("\\s+", " "))) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isObscene(String param) {
        return obsceneVocabularyDAO.readAll()
                .stream()
                .anyMatch(param.toLowerCase()::contains);
    }

    public boolean isMatchPageExists(int pageNumber){
        if (pageNumber < 1) {
            return false;
        }

        MatchDAO matchDAO = MatchDAO.getInstance();
        return pageNumber <= matchDAO.totalPages(MatchDAO.DEFAULT_PAGE_SIZE);
    }


    public boolean isMatchPageExists(int pageNumber, String playerName){
        if (pageNumber < 1) {
            return false;
        }

        MatchDAO matchDAO = MatchDAO.getInstance();
        return pageNumber <= matchDAO.totalPages(MatchDAO.DEFAULT_PAGE_SIZE, playerName);
    }
}
