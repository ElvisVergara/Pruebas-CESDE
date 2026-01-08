package com.crudac.container.controller;


import com.crudac.container.DTO.PersonaResponseDTO;
import com.crudac.container.entity.Persona;
import com.crudac.container.DTO.PersonaDTO;
import com.crudac.container.service.PersonaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
@CrossOrigin
public class PersonaController {

    private final PersonaService service;

    public PersonaController(PersonaService service) {
        this.service = service;
    }

    @GetMapping("/hola")
    @Tag(name = "Hola", description = "Decimos lindo")
    public String hola() {
        return "Hola mundo";
    }

    /* @GetMapping
    public List<Persona> listar() {
        return service.listar();
    } */

    @GetMapping
    public ResponseEntity<List<Persona>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtener(@PathVariable Long id) {
        return service.obtener(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Persona>> filtrarPaginado(
            @RequestParam(defaultValue = "") String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        Page<Persona> resultado = service.filtrarPorNombre(nombre, pageable);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/filterSP")
    public ResponseEntity<List<Persona>> filtrarViaStoredProcedure(@RequestParam String nombre) {
        return ResponseEntity.ok(service.filtrarViaStoredProcedure(nombre));
    }


    @PostMapping
    public ResponseEntity<Persona> guardar(@Valid @RequestBody PersonaDTO dto) {
        try {
            Persona persona = new Persona(dto.getNombre(), dto.getCorreo());
            return ResponseEntity.ok(service.guardar(persona));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizar(@PathVariable Long id, @Valid @RequestBody PersonaDTO dto) {
        return service.obtener(id)
                .map(p -> {
                    p.setNombre(dto.getNombre());
                    p.setCorreo(dto.getCorreo());
                    return ResponseEntity.ok(service.guardar(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return service.obtener(id)
                .map(p -> {
                    service.eliminar(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    /* Creando los queries */

    @GetMapping("/pagina")
    public Page<Persona> buscarPaginado(@RequestParam String nombre, Pageable pageable) {
        return service.buscarPorNombrePaginado(nombre, pageable);
    }

    @GetMapping("/exacto")
    public List<Persona> buscarExacto(@RequestParam String nombre) {
        return service.buscarPorNombreExacto(nombre);
    }

    @GetMapping("/ignore-case")
    public List<Persona> buscarIgnoreCase(@RequestParam String nombre) {
        return service.buscarPorNombreIgnoreCase(nombre);
    }

    @GetMapping("/contiene")
    public List<Persona> buscarContiene(@RequestParam String fragmento) {
        return service.buscarPorFragmentoNombre(fragmento);
    }

    @GetMapping("/contiene-ignore")
    public List<Persona> buscarContieneIgnoreCase(@RequestParam String fragmento) {
        return service.buscarPorFragmentoNombreIgnoreCase(fragmento);
    }

    @GetMapping("/inicia")
    public List<Persona> buscarIniciaCon(@RequestParam String prefijo) {
        return service.buscarPorNombreIniciaCon(prefijo);
    }

    @GetMapping("/termina")
    public List<Persona> buscarTerminaCon(@RequestParam String sufijo) {
        return service.buscarPorNombreTerminaCon(sufijo);
    }

    @GetMapping("/nombre-correo")
    public List<Persona> buscarNombreYCorreoFin(@RequestParam String nombre, @RequestParam String correoFin) {
        return service.buscarPorNombreYCorreoFin(nombre, correoFin);
    }

    @GetMapping("/query/nombre")
    public List<Persona> buscarPorNombreQuery(@RequestParam String nombre) {
        return service.buscarPorNombreQuery(nombre);
    }

    @GetMapping("/query/nombre-correo")
    public ResponseEntity<Persona> buscarPorNombreYCorreo(@RequestParam String nombre, @RequestParam String correo) {
        return service.buscarPorNombreYCorreo(nombre, correo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/correo-parcial")
    public List<Persona> buscarCorreoParcial(@RequestParam String correo) {
        return service.buscarPorCorreoParcial(correo);
    }

    @GetMapping("/longitud")
    public List<Persona> nombreMayorA(@RequestParam int longitud) {
        return service.buscarPorNombreMayorA(longitud);
    }

    @PostMapping("/ids")
    public List<Persona> buscarPorIds(@RequestBody List<Long> ids) {
        return service.buscarPorIds(ids);
    }

    @GetMapping("/sin-correo")
    public List<Persona> buscarSinCorreo() {
        return service.buscarSinCorreo();
    }

    @GetMapping("/ordenado")
    public List<Persona> ordenarPorNombre() {
        return service.buscarOrdenadoPorNombre();
    }

    @GetMapping("/contar-correo")
    public Long contarConCorreo() {
        return service.contarConCorreo();
    }

    @GetMapping("/stored")
    public List<Persona> storedProcedure(@RequestParam String nombre) {
        return service.filtrarPorStoredProcedure(nombre);
    }
}
