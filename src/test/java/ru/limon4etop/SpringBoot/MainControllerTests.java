package ru.limon4etop.SpringBoot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getRegisterUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registrUser").content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void updatePostListFullTest() throws  Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/updatePostList")
                .param("postCategory", "Animals")
                .param("searchText", "личный бассейн"))
                .andDo(print())
                .andExpect(redirectedUrl("/update"));
    }

    @Test
    void updatePostListOnlyCategoryTest() throws  Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/updatePostList")
                        .param("postCategory", "Animals"))
                .andDo(print())
                .andExpect(redirectedUrl("/update"));
    }

    @Test
    void updatePostListOnlyTextTest() throws  Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/updatePostList")
                        .param("searchText", "личный бассейн"))
                .andDo(print())
                .andExpect(redirectedUrl("/update"));
    }

    @Test
    void updatePostListNullTest() throws  Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/updatePostList"))
                .andDo(print())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void editUserDataTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/editUserData/{id}")
                        .param("id", "Id12345"))
                .andExpect(status().isOk());
    }

}
