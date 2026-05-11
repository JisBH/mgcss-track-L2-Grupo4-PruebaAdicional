package com.mgcss.integration;

import com.mgcss.infraestructure.persistence.JpaTecnicoRepository;
import com.mgcss.infraestructure.persistence.TecnicoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class JpaTecnicoRepositoryIntegrationTest {

    @Autowired
    private JpaTecnicoRepository repository;

    @Test
    void testGuardarYRecuperarTecnico() {
        TecnicoEntity tecnico = new TecnicoEntity(true);
        TecnicoEntity guardado = repository.save(tecnico);
        
        assertNotNull(guardado.getId());
        assertTrue(repository.findById(guardado.getId()).isPresent());
    }
}