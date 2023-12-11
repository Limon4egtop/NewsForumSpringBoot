package ru.limon4etop.SpringBoot;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
		Assert.assertEquals(2, 1+2);
	}

}
