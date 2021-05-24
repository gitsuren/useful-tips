//package com.surendra.hellocucumber;
//
//import io.cucumber.java.en.And;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.is;
//
//
////In order to use this class, use CucumberIntegrationWithMockmvcTest instead of CucumberIntegrationTest
//public class StepDefsIntegrationWithMockmvcTest {
//
//    MockMvc mockMvc;
//    MvcResult mvcResult;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @When("^the client calls /baeldung$")
//    public void the_client_issues_POST_hello() throws Throwable {
////        executePost();
//    }
//
//    @Given("^the client calls /hello$")
//    public void the_client_issues_GET_hello() throws Throwable {
////        executeGet("http://localhost:8082/hello");
//    }
//
//    @When("^the client calls /version$")
//    public void the_client_issues_GET_version() throws Throwable {
////        executeGet("http://localhost:8082/version");
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//
//        mvcResult = mockMvc
//                .perform(MockMvcRequestBuilders.get("/version"))
//                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//    }
//
//    @Then("^the client receives status code of (\\d+)$")
//    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
////        final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
////        assertThat("status code is incorrect : " + latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
//        assertThat("status code is incorrect : " + mvcResult.getResponse().getStatus(), mvcResult.getResponse().getStatus(), is(statusCode));
//    }
//
//    @And("^the client receives server version (.+)$")
//    public void the_client_receives_server_version_body(String version) throws Throwable {
////        assertThat(latestResponse.getBody(), is(version));
//        assertThat(mvcResult.getResponse().getContentAsString(), is(version));
//    }
//}