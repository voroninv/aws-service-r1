package com.base.servicer1.controllers;

import com.base.servicer1.services.interfaces.IS3ImageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
   JUnit Jupiter (JUnit 5).
   Instantiates only web layer rather than whole context.
   Need to mock all components.
*/

    @WebMvcTest(S3ImageController.class)
    public class S3ImageControllerTest {

        private static final String TEST_DATA = "test";

        @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private IS3ImageService s3ImageService;

        @Test
        public void listImagesTest() throws Exception {
            when(s3ImageService.listImages()).thenReturn(List.of("image1", "image2"));

            this.mockMvc.perform(get("/r1/api/s3/image/list"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$[0]").value("image1"))
                    .andExpect(jsonPath("$[1]").value("image2"));
        }

    @Test
    public void getImageTest() throws Exception {
        InputStream inputStream = new ByteArrayInputStream(TEST_DATA.getBytes());
        InputStreamResource resource = new InputStreamResource(inputStream);
        when(s3ImageService.getImage(eq("img1"))).thenReturn(resource);

        ResultActions perform = this.mockMvc.perform(get("/r1/api/s3/image/img1"));
        MockHttpServletResponse response = perform
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/octet-stream"))
                .andReturn().getResponse();

        Assertions.assertThat(response.getContentAsString()).isEqualTo("test");
    }
}
