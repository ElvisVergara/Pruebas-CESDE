package com.crudac.container.controller;

import com.crudac.container.entity.Persona;
import com.crudac.container.service.PersonaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PersonaController.class)
class PersonaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonaService personaService;

    @TestConfiguration
    static class Config {
        @Bean
        public PersonaService personaService() {
            return Mockito.mock(PersonaService.class);
        }
    }

    @Test
    @DisplayName("GET /hola devuelve Hola mundo")
    void testHola() throws Exception {
        mockMvc.perform(get("/api/personas/hola"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hola mundo"));
    }

    @Test
    @DisplayName("GET / devuelve lista de personas")
    void testListar() throws Exception {
        when(personaService.listar()).thenReturn(List.of(
                new Persona("Juan", "juan@correo.com"),
                new Persona("Maria", "maria@correo.com")
        ));

        mockMvc.perform(get("/api/personas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].nombre", is("Juan")));
    }

    @Test
    @DisplayName("GET /{id} devuelve persona existente")
    void testObtener() throws Exception {
        Persona persona = new Persona("Carlos", "carlos@correo.com");
        persona.setId(1L);
        when(personaService.obtener(1L)).thenReturn(Optional.of(persona));

        mockMvc.perform(get("/api/personas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Carlos")));
    }

    @Test
    @DisplayName("DELETE /{id} elimina persona")
    void testEliminar() throws Exception {
        Persona persona = new Persona("Lucia", "lucia@correo.com");
        persona.setId(2L);
        when(personaService.obtener(2L)).thenReturn(Optional.of(persona));
        doNothing().when(personaService).eliminar(2L);

        mockMvc.perform(delete("/api/personas/2"))
                .andExpect(status().isOk());
    }
}
