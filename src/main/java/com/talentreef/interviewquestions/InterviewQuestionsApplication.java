package com.talentreef.interviewquestions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.talentreef")
@EnableAutoConfiguration
public class InterviewQuestionsApplication {

  public static void main(String[] args) {
    SpringApplication.run(InterviewQuestionsApplication.class, args);
  }

}
