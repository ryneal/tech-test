package com.github.ryneal.techtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class TechTestApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Test
	public void shouldBeAbleToAccessPeoplePage() throws Exception {
		ResponseEntity<String> entity = this.restTemplate.getForEntity("/", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("<h1>People View All</h1>");
	}

	@Test
	public void shouldBeAbleToAccessCreatePage() throws Exception {
		ResponseEntity<String> entity = this.restTemplate.getForEntity("/create", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("<h1>Person Edit/Create</h1>");
	}

	@Test
	public void shouldBeAbleToAccessViewPersonPage() throws Exception {
		ResponseEntity<String> entity = this.restTemplate.getForEntity("/1", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("<h1>Person View</h1>");
	}

	@Test
	public void shouldBeAbleToAccessEditPersonPage() throws Exception {
		ResponseEntity<String> entity = this.restTemplate.getForEntity("/edit/1", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("<h1>Person Edit/Create</h1>");
	}

	@Test
	public void shouldBeAbleToAccessBootstrapCss() throws Exception {
		ResponseEntity<String> entity = this.restTemplate.getForEntity(
				"http://localhost:" + this.port + "/css/bootstrap.min.css", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("body");
	}

}
