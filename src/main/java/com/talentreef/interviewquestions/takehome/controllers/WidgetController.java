package com.talentreef.interviewquestions.takehome.controllers;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.services.WidgetService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1/widgets", produces = MediaType.APPLICATION_JSON_VALUE)
public class WidgetController {

  private final WidgetService widgetService;

  @Autowired
  public WidgetController(WidgetService widgetService) {
    Assert.notNull(widgetService, "widgetService must not be null");
    this.widgetService = widgetService;
  }

  @GetMapping
  public ResponseEntity<List<Widget>> getAllWidgets() {
    log.info("Requesting data from all widgets");
    return ResponseEntity.ok(widgetService.getAllWidgets());
  }

  @GetMapping(params = "name")
  public ResponseEntity<Object> getWidgetByName(@RequestParam String name) {
    log.info("Requesting data from widget with name=" + name);
    return ResponseEntity.ok(widgetService.getWidgetByName(name));
  }

  @PostMapping
  public ResponseEntity<Object> createWidget(@Valid @RequestBody Widget widget) {
    log.info("Requesting to create new widget with name=" + widget.getName());
    return ResponseEntity.ok(widgetService.createWidget(widget));
  }

  @PutMapping
  public ResponseEntity<Object> updateWidget(@Valid @RequestBody Widget widget) {
    log.info("Requesting to update data for widget with name=" + widget.getName());
    return ResponseEntity.ok(widgetService.updateWidget(widget));
  }

  @DeleteMapping
  public ResponseEntity<Object> deleteWidget(@RequestParam String name) {
    log.info("Requesting to delete widget with name=" + name);
    return ResponseEntity.ok(widgetService.deleteWidgetByName(name));
  }

}
