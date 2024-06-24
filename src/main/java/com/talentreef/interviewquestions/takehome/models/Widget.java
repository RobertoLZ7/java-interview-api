package com.talentreef.interviewquestions.takehome.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;


@Data
@Entity
@Table(name = "widgets")
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder(toBuilder=true)
public class Widget {

  @Id
  @Column(unique = true, nullable = false, length = 100)
  @Size(min = 3, max = 100)
  @NotNull
  private String name;

  @Column(nullable = false, length = 1000)
  @Size(min = 5, max = 1000)
  @NotNull
  private String description;

  @Column(nullable = false)
  @DecimalMin(value = "1.00")
  @DecimalMax(value = "20000.00")
  @Digits(integer = 5, fraction = 2)
  @NotNull
  private BigDecimal price;
}
