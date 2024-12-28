package org.fizz_buzz.dao;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ObsceneVocabularyFileDAO implements VocabularyDAO {

    private static final String[] OBSCENE_VOCABULARY_PATHS = {"ObsceneVocabularyEN.txt",
            "ObsceneVocabularyRU.txt"};

    private static ObsceneVocabularyFileDAO instance;

    private ObsceneVocabularyFileDAO() {
    }

    public synchronized static VocabularyDAO getInstance() {
        if (instance == null) {
            instance = new ObsceneVocabularyFileDAO();
        }
        return instance;
    }

    @Override
    public List<String> readAll() {
        List<String> obsceneWords = new ArrayList<>();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        for (String obsceneVocabularyPath : OBSCENE_VOCABULARY_PATHS) {

            try {
                URL url = classloader.getResource(obsceneVocabularyPath);
                assert url != null;
                Path path = Paths.get(url.toURI());
                obsceneWords.addAll(Files.readAllLines(path, StandardCharsets.UTF_8));
            } catch (URISyntaxException | IOException | RuntimeException e) {
                    log.error(e.getMessage(), e);
                    throw new RuntimeException(e);
            }
        }

        return obsceneWords;
    }
}
