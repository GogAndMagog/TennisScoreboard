package org.fizz_buzz.validation;

import org.fizz_buzz.dao.MatchDAO;
import org.fizz_buzz.dao.ObsceneVocabularyFileDAO;
import org.fizz_buzz.dao.VocabularyDAO;

import java.util.HashSet;
import java.util.Set;

public class ParamValidator {

    private final VocabularyDAO obsceneVocabularyDAO = ObsceneVocabularyFileDAO.getInstance();

    private volatile static ParamValidator instance;

    private ParamValidator() {
    }

    public static ParamValidator getInstance() {
        if (instance == null) {
            synchronized (ParamValidator.class) {
                if (instance == null) {
                    instance = new ParamValidator();
                }
            }
        }
        return instance;
    }

    public boolean isLongerThan(int maxLength, String param) {
        if (param == null) {
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
        return param != null && param.matches("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");
    }

    public boolean isNotUnique(String... params) {
        Set<String> uniqValues = new HashSet<>();

        for (String param : params) {
            String normalizedParam = param.replaceAll("\\s+", " ");
            if (!uniqValues.add(normalizedParam)) {
                return true;
            }
        }

        return false;
    }

    public boolean isObscene(String param) {
        return param != null && obsceneVocabularyDAO.readAll()
                .stream()
                .anyMatch(param.toLowerCase()::contains);
    }

    public boolean isMatchPageExists(int pageNumber) {
        if (pageNumber < 1) {
            return false;
        }

        MatchDAO matchDAO = MatchDAO.getInstance();
        return pageNumber <= matchDAO.totalPages(MatchDAO.DEFAULT_PAGE_SIZE);
    }
}
