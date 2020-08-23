package com.usoft;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.usoft.application.Application;

@SpringBootTest(classes = Application.class)
public class TestClassToTestModule {
	@Test
	public void contextLoads() {
		assertTrue(true);
	}
}
