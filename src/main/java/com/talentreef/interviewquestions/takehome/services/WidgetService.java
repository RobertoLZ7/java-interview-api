package com.talentreef.interviewquestions.takehome.services;

import com.talentreef.interviewquestions.exceptions.ElementAlreadyExistsException;
import com.talentreef.interviewquestions.exceptions.ElementNotFoundException;
import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WidgetService {

  private final WidgetRepository widgetRepository;

  @Autowired
  private WidgetService(WidgetRepository widgetRepository) {
    Assert.notNull(widgetRepository, "widgetRepository must not be null");
    this.widgetRepository = widgetRepository;
  }

  public List<Widget> getAllWidgets() {
    return widgetRepository.findAll();
  }

  public Widget getWidgetByName(String name) {
      return widgetRepository.findByNameIgnoreCase(name).orElseThrow(() -> new ElementNotFoundException(String.format("Widget with name=%s not found", name)));
    }
  public Widget createWidget(Widget widget) {
    Optional<Widget> storedWidget = widgetRepository.findByNameIgnoreCase(widget.getName());

    if (storedWidget.isPresent()) {
      log.error(String.format("Unable to create widget with name=%s - ALREADY EXISTS", widget.getName()));
      throw new ElementAlreadyExistsException(String.format("Widget with name=%s already exists", widget.getName()));
    }

    log.info(String.format("Widget with name=%s created successfully", widget.getName()));
    return widgetRepository.save(widget);
  }

  public Widget updateWidget(Widget newWidgetData) {
    Widget widget = widgetRepository.findByNameIgnoreCase(newWidgetData.getName()).orElseThrow(() -> {
      log.error(String.format("Unable to update widget with name=%s - NOT FOUND", newWidgetData.getName()));
      return new ElementNotFoundException(String.format("Widget with name=%s not found", newWidgetData.getName()));
    });

    widget.setPrice(newWidgetData.getPrice());
    widget.setDescription(newWidgetData.getDescription());

    log.info(String.format("Widget with name=%s updated successfully", widget.getName()));
    return widgetRepository.save(widget);
  }

  public Widget deleteWidgetByName(String name) {
    Widget widget = widgetRepository.findByNameIgnoreCase(name).orElseThrow(() -> {
        log.error(String.format("Unable to delete widget with name=%s - NOT FOUND", name));
        return new ElementNotFoundException(String.format("Widget with name=%s not found", name));
      });

    widgetRepository.delete(widget);
    log.info(String.format("Widget with name=%s deleted successfully", name));

    return widget;
  }
}
