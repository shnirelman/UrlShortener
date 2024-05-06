package org.example.service;

import org.example.controller.dto.UrlDto;
import org.example.repository.UrlRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.google.gson.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlServiceTest {
    /*@Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlRepository urlRepository;

    @Test
    void testGetMissingUrl() throws Exception {
        Mockito.doReturn(Optional.empty())
                .when(urlRepository)
                .findUrlByShortForm("abacaba");

        mockMvc.perform(get("/url/abacaba"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.longForm", is("URL does not exist"))
                );
    }

    @Test
    void testAddSameUrlMultipleTimes() throws Exception {
        String longForm = "test_add_url_2";
        String shortFormSuffix = "abc";
        UrlDao urlDao = new UrlDao(longForm, shortFormSuffix);
        Mockito.doReturn(Optional.of(urlDao))
                .when(urlRepository)
                .findUrlByLongForm(longForm);
        Mockito.doReturn(Optional.of(urlDao))
                .when(urlRepository)
                .findUrlByShortForm(shortFormSuffix);

        UrlDto urlDto = new UrlDto(longForm);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(urlDto);
        MvcResult mvcResult =
                mockMvc.perform(post("/url/create")
                                .contentType("application/json;charset=UTF-8")
                                .content(json))
                        .andExpectAll(
                                status().isOk()
                        ).andReturn();
        String shortForm = mvcResult.getResponse().getContentAsString();
        assertEquals(shortFormSuffix, shortForm.substring(UrlServiceImpl.shortFormPrefix.length()));

        for(int i = 0; i < 10; i++) {
            MvcResult mvcResult2 =
                    mockMvc.perform(post("/url/create")
                                    .contentType("application/json;charset=UTF-8")
                                    .content(json))
                            .andExpectAll(
                                    status().isOk()
                            ).andReturn();
            assertEquals(shortForm, mvcResult2.getResponse().getContentAsString());
        }

        mockMvc.perform(get("/url/" + shortFormSuffix))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.longForm", is(longForm))
                );
    }*/
}
