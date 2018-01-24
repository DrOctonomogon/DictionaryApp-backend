package com.example.nyanchatsolomodulev2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@WebMvcTest(value = DictionaryController.class, secure = false)
public class DictionaryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private DictionaryService service;

    DictionaryEntry mockDictionaryEntry = new DictionaryEntry("lugubrious", "adjective","excessively mournful", "");

    @Test
    public void defineWordTest_pass() throws Exception {

        Mockito.when(service.lookupWord(Mockito.anyString())).thenReturn(mockDictionaryEntry);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/entries/lugubrious").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"word\":\"lugubrious\",\"definition\":\"excessively mournful\",\"partOfSpeech\":\"adjective\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void defineWordTest_fail() throws Exception {
        Mockito.when(service.lookupWord(Mockito.anyString())).thenReturn(mockDictionaryEntry);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/entries/1337H4X").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = null;
        JSONAssert.assertNotEquals(expected, result.getResponse().getContentAsString(), false);
    }
}