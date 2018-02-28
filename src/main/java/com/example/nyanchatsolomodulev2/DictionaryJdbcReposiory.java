package com.example.nyanchatsolomodulev2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DictionaryJdbcReposiory {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public DictionaryEntry getEntryByTitle(String word) {
        return jdbcTemplate.queryForObject("SELECT * FROM dictionary WHERE entryTitle = ?",
                new Object[] { word }, new BeanPropertyRowMapper<>(DictionaryEntry.class));
    }

    public int insert(DictionaryEntry entry) {
        return jdbcTemplate.update(
                "INSERT INTO dictionary (entryTitle, partOfSpeech, definition, etymology)" + "VALUES(?, ?, ?, ?)",
                new Object[] {entry.getEntryTitle(), entry.getPartOfSpeech(), entry.getDefinition(), entry.getEtymology()});
    }
}
