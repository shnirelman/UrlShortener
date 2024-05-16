package org.example.controller;

import org.example.controller.dto.UrlDto;
import org.example.repository.UrlRepository;
import org.example.repository.entity.UrlEntity;
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
public class UrlControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlRepository urlRepository;

    @Test
    void testGetMissingUrl() throws Exception {
        Mockito.doReturn(Optional.empty())
                .when(urlRepository)
                .findByShortForm("abacaba");

        mockMvc.perform(get("/url/abacaba"))
                .andExpectAll(
                        status().is4xxClientError()
                );
    }

    @Test
    void testAddSameUrlMultipleTimes() throws Exception {
        String longForm = "test_add_url_2";
        String shortFormSuffix = "abc";
        UrlEntity urlEntity = new UrlEntity(shortFormSuffix, longForm);
        Mockito.doReturn(Optional.of(urlEntity))
                .when(urlRepository)
                .findByLongForm(longForm);
        Mockito.doReturn(Optional.of(urlEntity))
                .when(urlRepository)
                .findByShortForm(shortFormSuffix);

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
//        assertEquals(shortFormSuffix, shortForm.substring(UrlServiceImpl.shortFormPrefix.length()));

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

        MvcResult mvcResult3 = mockMvc.perform(get("/url/" + shortFormSuffix))
                .andExpect(
                        status().isOk()
                ).andReturn();
        assertEquals("redirect:" + longForm, mvcResult3.getResponse().getContentAsString());

    }
}
