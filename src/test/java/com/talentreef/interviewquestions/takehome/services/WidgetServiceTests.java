package com.talentreef.interviewquestions.takehome.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.talentreef.interviewquestions.exceptions.ElementAlreadyExistsException;
import com.talentreef.interviewquestions.exceptions.ElementNotFoundException;
import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class WidgetServiceTests {

  @Mock
  private WidgetRepository widgetRepository;

  @InjectMocks
  private WidgetService widgetService;

  @Test
  public void when_getAllWidgets_expect_findAllResult() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").build();
    List<Widget> response = List.of(widget);
    when(widgetRepository.findAll()).thenReturn(response);

    List<Widget> result = widgetService.getAllWidgets();

    assertThat(result).isEqualTo(response);
  }

  @Test
  public void when_getWidgetByName_expect_findResult() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").build();
    when(widgetRepository.findByNameIgnoreCase(any())).thenReturn(Optional.of(widget));

    Widget result = widgetService.getWidgetByName(widget.getName());

    assertThat(result).isEqualTo(widget);
  }

  @Test
  public void when_getWidgetByName_expect_notFound() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").build();
    when(widgetRepository.findByNameIgnoreCase(any())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> widgetService.getWidgetByName(widget.getName()))
            .isInstanceOf(ElementNotFoundException.class)
            .hasMessage("Widget with name=Widgette Nielson not found");
  }

  @Test
  public void when_createWidget_expect_widget() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").build();
    when(widgetRepository.findByNameIgnoreCase(any())).thenReturn(Optional.empty());
    when(widgetRepository.save(any())).thenReturn(widget);
    Widget result = widgetService.createWidget(widget);

    assertThat(result).isEqualTo(widget);
  }

  @Test
  public void when_createWidget_expect_alreadyExists() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").build();
    when(widgetRepository.findByNameIgnoreCase(any())).thenReturn(Optional.of(widget));

    assertThatThrownBy(() -> widgetService.createWidget(widget))
            .isInstanceOf(ElementAlreadyExistsException.class)
            .hasMessage("Widget with name=Widgette Nielson already exists");
  }

  @Test
  public void when_updateWidget_expect_widget() throws Exception {
    Widget previousWidget = Widget.builder().name("Widgette Nielson").build();
    Widget updatedWidget = Widget.builder().name("Widgette Nielson").price(new BigDecimal("20.0")).description("updated widget description").build();
    when(widgetRepository.findByNameIgnoreCase(any())).thenReturn(Optional.of(previousWidget));
    when(widgetRepository.save(any())).thenReturn(updatedWidget);

    Widget result = widgetService.updateWidget(updatedWidget);

    assertThat(result).isEqualTo(updatedWidget);
  }

  @Test
  public void when_updateWidget_expect_notFound() throws Exception {
    Widget updatedWidget = Widget.builder().name("Widgette Nielson").price(new BigDecimal("20.0")).description("updated widget description").build();
    when(widgetRepository.findByNameIgnoreCase(any())).thenReturn(Optional.empty());

    assertThatThrownBy(() ->widgetService.updateWidget(updatedWidget))
            .isInstanceOf(ElementNotFoundException.class)
            .hasMessage("Widget with name=Widgette Nielson not found");
  }

  @Test
  public void when_deleteWidgetByName_expect_widget() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").price(new BigDecimal("20.0")).description("updated widget description").build();
    when(widgetRepository.findByNameIgnoreCase(any())).thenReturn(Optional.of(widget));

    Widget deleted = widgetService.deleteWidgetByName(widget.getName());

    assertThat(deleted).isEqualTo(widget);
  }

  @Test
  public void when_deleteWidgetByName_expect_notFound() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").price(new BigDecimal("20.0")).description("updated widget description").build();
    when(widgetRepository.findByNameIgnoreCase(any())).thenReturn(Optional.empty());

    assertThatThrownBy(() ->widgetService.deleteWidgetByName(widget.getName()))
            .isInstanceOf(ElementNotFoundException.class)
            .hasMessage("Widget with name=Widgette Nielson not found");
  }
}
