package io.tpd.superheroes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tpd.superheroes.MvcTestsApplication;
import io.tpd.superheroes.domain.SuperHero;
import io.tpd.superheroes.exceptions.NonExistingHeroException;
import io.tpd.superheroes.repository.SuperHeroRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * This class demonstrates how to test a controller using Spring Boot Test
 * (what makes it much closer to a Integration Test)
 *
 * @author moises.macero
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MvcTestsApplication.class})
@WebAppConfiguration
public class SuperHeroControllerSpringBootwithWACTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SuperHeroRepository superHeroRepository;

    @InjectMocks
    private SuperHeroController superHeroController;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<SuperHero> jsonSuperHero;

    //    @BeforeEach
//    public void setup() {
//        // We would need this line if we would not use the MockitoExtension
//        // MockitoAnnotations.initMocks(this);
//        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
//        JacksonTester.initFields(this, new ObjectMapper());
//        // MockMvc standalone approach
//        mvc = MockMvcBuilders.standaloneSetup(superHeroController)
//                .setControllerAdvice(new SuperHeroExceptionHandler())
//                .addFilters(new SuperHeroFilter())
//                .build();
//    }
    @BeforeEach
    public void setup() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .addFilters(new SuperHeroFilter())
                .build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("superHeroController"));
    }

    @Test
    public void canRetrieveByIdWhenExists() throws Exception {
        // given
        given(superHeroRepository.getSuperHero(2))
                .willReturn(new SuperHero("Rob", "Mannon", "RobotMan"));

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/superheroes/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonSuperHero.write(new SuperHero("Rob", "Mannon", "RobotMan")).getJson()
        );
    }

    @Test
    public void canRetrieveByIdWhenDoesNotExist() throws Exception {
        // given
        given(superHeroRepository.getSuperHero(2))
                .willThrow(new NonExistingHeroException());

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/superheroes/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    public void canRetrieveByNameWhenExists() throws Exception {
        // given
        given(superHeroRepository.getSuperHero("RobotMan"))
                .willReturn(Optional.of(new SuperHero("Rob", "Mannon", "RobotMan")));

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/superheroes/?name=RobotMan")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonSuperHero.write(new SuperHero("Rob", "Mannon", "RobotMan")).getJson()
        );
    }

    @Test
    public void canRetrieveByNameWhenDoesNotExist() throws Exception {
        // given
        given(superHeroRepository.getSuperHero("RobotMan"))
                .willReturn(Optional.empty());

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/superheroes/?name=RobotMan")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("null");
    }

    @Test
    public void canCreateANewSuperHero() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                post("/superheroes/").contentType(MediaType.APPLICATION_JSON).content(
                        jsonSuperHero.write(new SuperHero("Rob", "Mannon", "RobotMan")).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void headerIsPresent() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/superheroes/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getHeaders("X-SUPERHERO-APP")).containsOnly("super-header");
    }

}
