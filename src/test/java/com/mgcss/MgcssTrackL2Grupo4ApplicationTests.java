package com.mgcss;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.mgcss.domain.MgcssTrackL2Grupo4Application;

@SpringBootTest(classes = MgcssTrackL2Grupo4Application.class)
@ActiveProfiles("test") //Spring busca el archivo application-test.yml
class MgcssTrackL2Grupo4ApplicationTests {

	@Test
	void contextLoads() {
	}

}
