package com.surendra.hellocucumber;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class VersionControllerStandaloneTest {

    private MockMvc mockMvc;
    private static final String CONTENT_TYPE = "application/json";

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new VersionController()).build();
    }

    @Test
    public void testGetVersionAPI() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/version"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(mvcResult -> mvcResult.getResponse().getContentAsString().equals("1.0"));
    }


}