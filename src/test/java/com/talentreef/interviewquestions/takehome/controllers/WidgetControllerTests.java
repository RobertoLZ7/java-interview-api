package com.talentreef.interviewquestions.takehome.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.services.WidgetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WidgetControllerTests {

  final private ObjectMapper objectMapper = new ObjectMapper();

  private MockMvc mockMvc;

  @Mock
  private WidgetService widgetService;

  @InjectMocks
  private WidgetController widgetController;

  @Before
  public void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(widgetController).build();
  }

  @Test
  public void when_getAllWidgets_expect_allWidgets() throws Exception {
    Widget widget = Widget.builder().name("Widget von Hammersmark").build();
    List<Widget> allWidgets = List.of(widget);
    when(widgetService.getAllWidgets()).thenReturn(allWidgets);

    MvcResult result = mockMvc.perform(get("/v1/widgets"))
               .andExpect(status().isOk())
               .andDo(print())
               .andReturn();

    List<Widget> parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(),
        new TypeReference<List<Widget>>(){});
    assertThat(parsedResult).isEqualTo(allWidgets);
  }

  @Test
  public void when_getWidgetByName_expect_widget() throws Exception {
    Widget firstWidget = Widget.builder().name("test widget").build();
    Widget secondWidget = Widget.builder().name("Widget von Hammersmark").build();

    when(widgetService.getWidgetByName(any())).thenReturn(firstWidget);

    MvcResult result = mockMvc.perform(get("/v1/widgets?name=test widget"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

    Widget parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(),
            new TypeReference<Widget>() {});
    assertThat(parsedResult).isEqualTo(firstWidget);
  }

  @Test
  public void when_createWidget_expect_widget() throws Exception {
    Widget newWidget = Widget.builder().name("new widget").price(new BigDecimal("20.0")).description("this is a description example").build();
    String jsonRequest = objectMapper.writeValueAsString(newWidget);
    when(widgetService.createWidget(any())).thenReturn(newWidget);

    MvcResult result = mockMvc.perform(post("/v1/widgets").contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

    Widget parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Widget>() {});
    assertThat(parsedResult).isEqualTo(newWidget);
  }
  @Test
  public void when_createWidget_withoutPrice_expect_badRequest() throws Exception {
    Widget newWidget = Widget.builder().name("new widget").description("this is a description example").build();
    String jsonRequest = objectMapper.writeValueAsString(newWidget);

    MvcResult result = mockMvc.perform(post("/v1/widgets").contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
            .andExpect(status().isBadRequest())
            .andDo(print())
            .andReturn();

    assertThat(result.getResponse().getErrorMessage()).isEqualTo("Invalid request content.");
  }

  @Test
  public void when_createWidget_withoutDescription_expect_badRequest() throws Exception {
    Widget newWidget = Widget.builder().name("new widget").price(new BigDecimal("20.0")).build();
    String jsonRequest = objectMapper.writeValueAsString(newWidget);

    MvcResult result = mockMvc.perform(post("/v1/widgets").contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
            .andExpect(status().isBadRequest())
            .andDo(print())
            .andReturn();

    assertThat(result.getResponse().getErrorMessage()).isEqualTo("Invalid request content.");
  }

  @Test
  public void when_createWidget_withoutName_expect_badRequest() throws Exception {
    Widget newWidget = Widget.builder().description("this is a description example").price(new BigDecimal("20.0")).build();
    String jsonRequest = objectMapper.writeValueAsString(newWidget);

    MvcResult result = mockMvc.perform(post("/v1/widgets").contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
            .andExpect(status().isBadRequest())
            .andDo(print())
            .andReturn();

    assertThat(result.getResponse().getErrorMessage()).isEqualTo("Invalid request content.");
  }

  @Test
  public void when_createWidget_smallerName_expect_badRequest() throws Exception {
    Widget newWidget = Widget.builder().name("tt").price(new BigDecimal("20.0")).description("this is a description example").build();
    String jsonRequest = objectMapper.writeValueAsString(newWidget);

    MvcResult result = mockMvc.perform(post("/v1/widgets").contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
            .andExpect(status().isBadRequest())
            .andDo(print())
            .andReturn();

    assertThat(result.getResponse().getErrorMessage()).isEqualTo("Invalid request content.");
  }

  @Test
  public void when_updateWidget_expect_widget() throws Exception {
    Widget updatedWidget = Widget.builder().name("updated").price(new BigDecimal("20.0")).description("this is a description example").build();
    String jsonRequest = objectMapper.writeValueAsString(updatedWidget);
    when(widgetService.updateWidget(any())).thenReturn(updatedWidget);

    MvcResult result = mockMvc.perform(put("/v1/widgets").contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

    Widget parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Widget>() {});
    assertThat(parsedResult).isEqualTo(updatedWidget);
  }

  @Test
  public void when_updateWidget_smallDescription_expect_badRequest() throws Exception {
    Widget updatedWidget = Widget.builder().name("updated").price(new BigDecimal("20.0")).description("desc").build();
    String jsonRequest = objectMapper.writeValueAsString(updatedWidget);

    MvcResult result = mockMvc.perform(put("/v1/widgets").contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
            .andExpect(status().isBadRequest())
            .andDo(print())
            .andReturn();

    assertThat(result.getResponse().getErrorMessage()).isEqualTo("Invalid request content.");
  }

  @Test
  public void when_updateWidget_higherPrice_expect_badRequest() throws Exception {
    Widget updatedWidget = Widget.builder().name("updated").price(new BigDecimal("20000.01")).description("this is an example description").build();
    String jsonRequest = objectMapper.writeValueAsString(updatedWidget);

    MvcResult result = mockMvc.perform(put("/v1/widgets").contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
            .andExpect(status().isBadRequest())
            .andDo(print())
            .andReturn();

    assertThat(result.getResponse().getErrorMessage()).isEqualTo("Invalid request content.");
  }

  @Test
  public void when_updateWidget_lowerPrice_expect_badRequest() throws Exception {
    Widget updatedWidget = Widget.builder().name("updated").price(new BigDecimal("0.50")).description("this is an example description").build();
    String jsonRequest = objectMapper.writeValueAsString(updatedWidget);

    MvcResult result = mockMvc.perform(put("/v1/widgets").contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
            .andExpect(status().isBadRequest())
            .andDo(print())
            .andReturn();

    assertThat(result.getResponse().getErrorMessage()).isEqualTo("Invalid request content.");
  }

  @Test
  public void when_updateWidget_exceedDecimals_expect_badRequest() throws Exception {
    Widget updatedWidget = Widget.builder().name("updated").price(new BigDecimal("20.034")).description("this is an example description").build();
    String jsonRequest = objectMapper.writeValueAsString(updatedWidget);

    MvcResult result = mockMvc.perform(put("/v1/widgets").contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
            .andExpect(status().isBadRequest())
            .andDo(print())
            .andReturn();

    assertThat(result.getResponse().getErrorMessage()).isEqualTo("Invalid request content.");
  }

  @Test
  public void when_deleteWidgetByName_expect_widget() throws Exception {
    Widget deletedWidget = Widget.builder().name("deleted").build();
    when(widgetService.deleteWidgetByName(any())).thenReturn(deletedWidget);

    MvcResult result = mockMvc.perform(delete("/v1/widgets?name=deleted"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

    Widget parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Widget>() {});
    assertThat(parsedResult).isEqualTo(deletedWidget);
  }
}
