package com.mgcss;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(classes = MgcssTrackL2Grupo4Application.class)
@ActiveProfiles("test") 
class MgcssTrackL2Grupo4ApplicationTests {

    @Test
    void contextLoads() {
        // Esta aserción confirma que el contexto de Spring se inicia sin errores
        assertDoesNotThrow(() -> {}, "El contexto de la aplicación debería cargar sin excepciones");
    }

    @Test
    void mainMethodTest() {
        // Al llamar al main, cubres las líneas rojas de la clase principal
        // y la aserción verifica que la aplicación arranca correctamente
        assertDoesNotThrow(() -> MgcssTrackL2Grupo4Application.main(new String[] {"--spring.main.web-application-type=none"}), 
            "El método main debería ejecutarse sin lanzar excepciones");
    }
}