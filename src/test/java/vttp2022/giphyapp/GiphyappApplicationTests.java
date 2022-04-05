package vttp2022.giphyapp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vttp2022.giphyapp.service.GiphyService;

@SpringBootTest
class GiphyappApplicationTests {

	@Autowired
	private GiphyService service;

	@Test
	void shouldReturn10Images(){
		List<String> gifs = service.getGifs("pokemon", 10);
		assertEquals(gifs.size(), 10, "Should return 10 images in list");
	}
}
