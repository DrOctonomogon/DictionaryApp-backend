package com.example.nyanchatsolomodulev2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class DictionaryService {

    private Map<String, DictionaryEntry> dictionaryEntries = new HashMap<>();
//    private Map<String, List<DictionaryEntry>> dictionaryEntries = new HashMap<>();


    @Autowired
    RestTemplate restTemplate;

    public String testOutput() {
        return "It works!";
    }

    public DictionaryEntry lookupWord(String word) {

        if (dictionaryEntries.containsKey(word)) {
            return dictionaryEntries.get(word);
        }

        String oxfordUrl = "https://od-api.oxforddictionaries.com/api/v1/entries/en/" + word;

        HttpHeaders headers = initializeRequestHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = null;
        try{
             response = restTemplate.exchange(oxfordUrl, HttpMethod.GET, entity, String.class);
        }
        catch(HttpClientErrorException e) {
            //e.printStackTrace();
            return new DictionaryEntry("Error", "noun", "You dun' goofed, friend.",
                    "Origin: You. " + word + " isn't a word. At least not in English.");
        }

            DictionaryEntry newEntry = mapResponseToDictionaryEntry(response);
            dictionaryEntries.put(word, newEntry);

        return newEntry;
    }

    private DictionaryEntry mapResponseToDictionaryEntry(ResponseEntity<String> response) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readValue(response.getBody(), JsonNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        node = mapper.valueToTree(node.get("results").get(0));
        JsonNode searchWord = mapper.valueToTree(node.get("id"));
        JsonNode partOfSpeech = mapper.valueToTree(node.get("lexicalEntries").get(0).get("lexicalCategory"));
        JsonNode definition = mapper.valueToTree(node.get("lexicalEntries").get(0).get("entries").get(0).get("senses").get(0).get("definitions").get(0));
        JsonNode etymology = mapper.valueToTree(node.get("lexicalEntries").get(0).get("entries").get(0).get("etymologies").get(0));

        return new DictionaryEntry(searchWord.asText(), partOfSpeech.asText(), formatDefinition(definition.asText()), etymology.asText());
    }

    //        return entries;
    //
    //        dictionaryEntries.put(word, entries);
    //        entries = mapResponseToMultipleDictionaryEntries(response);
    //
    //        }
    //            return entries;
    //                    "Origin: You. " + word + " isn't a word. At least not in English."));
    //            entries.add(new DictionaryEntry("Error", "noun", "You dun' goofed, friend.",
    //            entries = new ArrayList<>();
    //            //e.printStackTrace();
    //        catch(HttpClientErrorException e) {
    //        }
    //            response = restTemplate.exchange(oxfordUrl, HttpMethod.GET, entity, String.class);
    //        try{
    //        ResponseEntity<String> response = null;
    //
    //        HttpEntity<String> entity = new HttpEntity<>(headers);
    //        HttpHeaders headers = initializeRequestHeaders();
    //
    //        String oxfordUrl = "https://od-api.oxforddictionaries.com/api/v1/entries/en/" + word;
    //
    //        List<DictionaryEntry> entries;
    //
    //        }
    //            return dictionaryEntries.get(word);
    //        if (dictionaryEntries.containsKey(word)) {
    //
    //    public List<DictionaryEntry> lookupWord(String word) {
    // Method for getting multiple entries for a word

//    }
//
//    private List<DictionaryEntry> mapResponseToMultipleDictionaryEntries(ResponseEntity<String> response) {
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode node = null;
//        try {
//            node = mapper.readValue(response.getBody(), JsonNode.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        List<DictionaryEntry> entries = new ArrayList<>();
//
//        node = mapper.valueToTree(node.get("results"));
//
//        for(int i = 0; i < node.size(); i++) {
//            JsonNode searchWord = mapper.valueToTree(node.get(i).get("id"));
//            JsonNode partOfSpeech = mapper.valueToTree(node.get(i).get("lexicalEntries").get(0).get("lexicalCategory"));
//            JsonNode definition = mapper.valueToTree(node.get(i).get("lexicalEntries").get(0).get("entries").get(0).get("senses").get(0).get("definitions").get(0));
//            JsonNode etymology = mapper.valueToTree(node.get(i).get("lexicalEntries").get(0).get("entries").get(0).get("etymologies").get(0));
//            entries.add(new DictionaryEntry(searchWord.asText(), partOfSpeech.asText(), formatDefinition(definition.asText()), etymology.asText()));
//        }
//
//        return entries;
//    }

    private String formatDefinition(String definition) {
        return Character.toUpperCase(definition.charAt(0)) + definition.substring(1);
    }

    private HttpHeaders initializeRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("app_id", "ca22f29d");
        headers.set("app_key", "b41c1be55fe51b8ed7e976c023d2276d");
        return headers;
    }

}
