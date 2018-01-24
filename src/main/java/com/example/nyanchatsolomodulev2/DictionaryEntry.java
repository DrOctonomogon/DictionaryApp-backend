package com.example.nyanchatsolomodulev2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class DictionaryEntry {


    @JsonProperty()
    private String entryTitle;

    @JsonProperty()
    private String partOfSpeech;

    @JsonProperty()
    private String definition;

    @JsonProperty()
    private String etymology;

    public DictionaryEntry(String entryTitle, String partOfSpeech, String definition, String etymology) {
        this.entryTitle = entryTitle;
        this.partOfSpeech = partOfSpeech;
        this.definition = definition;
        this.etymology = etymology;
    }

    @Override
    public String toString() {
        return "DictionaryEntry{" +
                "entryTitle='" + entryTitle + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                ", definition='" + definition + '\'' +
                ", etymology='" + etymology + '\'' +
                '}';
    }

}
