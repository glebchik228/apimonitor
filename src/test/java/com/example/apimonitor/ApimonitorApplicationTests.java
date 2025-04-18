package com.example.apimonitor;

import com.example.apimonitor.model.MonitoredUrl;
import com.example.apimonitor.service.MonitorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApimonitorApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MonitorService monitorService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        monitorService.getUrls().clear();
    }

    @Test
    void testServiceAddAndGet() {
        MonitoredUrl url = new MonitoredUrl();
        url.setUrl("https://example.com");
        url.setIntervalInSeconds(30);

        monitorService.addUrl(url);
        List<MonitoredUrl> all = monitorService.getAll();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getUrl()).isEqualTo("https://example.com");
        assertThat(all.get(0).getIntervalInSeconds()).isEqualTo(30);
    }

    @Test
    void testAddUrlEndpoint() throws Exception {
        MonitoredUrl url = new MonitoredUrl();
        url.setUrl("https://test.com");
        url.setIntervalInSeconds(45);

        mockMvc.perform(post("/api/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(url)))
                .andExpect(status().isOk());

        List<MonitoredUrl> all = monitorService.getAll();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getUrl()).isEqualTo("https://test.com");
    }

    @Test
    void testGetAllEndpoint() throws Exception {
        MonitoredUrl url1 = new MonitoredUrl();
        url1.setUrl("https://one.com");
        url1.setIntervalInSeconds(10);

        MonitoredUrl url2 = new MonitoredUrl();
        url2.setUrl("https://two.com");
        url2.setIntervalInSeconds(20);

        monitorService.addUrl(url1);
        monitorService.addUrl(url2);

        mockMvc.perform(get("/api/urls"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].url").value("https://one.com"))
                .andExpect(jsonPath("$[1].intervalInSeconds").value(20));
    }
}
