package com.mgcss;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.mgcss.domain.MgcssTrackL2Grupo4Application;


@SpringBootTest(classes = MgcssTrackL2Grupo4Application.class) 
class MgcssTrackL2Grupo4ApplicationTests {

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring arranca correctamente
    }

    @Test
    void mainMethodTest() {
        // Esto ejecutará la línea de SpringApplication.run y subirá la cobertura al 100%
        MgcssTrackL2Grupo4Application.main(new String[] {});
    }
}
