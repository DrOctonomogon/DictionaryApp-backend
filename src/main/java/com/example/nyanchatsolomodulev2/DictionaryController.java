package com.example.nyanchatsolomodulev2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class DictionaryController {

    @Autowired
    DictionaryService service;

    @GetMapping("/test")
    public String test() {
        return service.testOutput();
    }

    @GetMapping("entries/{word}")
    public DictionaryEntry defineWord(@PathVariable String word) {
        System.out.println("Made a call for \'" + word + "\'");
        return service.lookupWord(word);
    }
    
}
