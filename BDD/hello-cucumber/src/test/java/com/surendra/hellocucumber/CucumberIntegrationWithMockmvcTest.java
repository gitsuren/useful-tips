//package com.surendra.hellocucumber;
//
//import io.cucumber.junit.Cucumber;
//import io.cucumber.junit.CucumberOptions;
//import io.cucumber.spring.CucumberContextConfiguration;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//@RunWith(Cucumber.class)
//@CucumberOptions(features = "src/test/resources")
//@CucumberContextConfiguration
//@ContextConfiguration(classes = HelloCucumberApplication.class)
//@WebAppConfiguration
////@WebAppConfiguration
//
////java.util.concurrent.ExecutionException: java.lang.IllegalStateException:
////@WebAppConfiguration should only be used with @SpringBootTest when @SpringBootTest is configured with a mock web environment.
////        Please remove @WebAppConfiguration or reconfigure @SpringBootTest.
////        at java.base/java.util.concurrent.FutureTask.report(FutureTask.java:122)
//
//
//@SpringBootTest(classes = HelloCucumberApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//public class CucumberIntegrationWithMockmvcTest {
//}