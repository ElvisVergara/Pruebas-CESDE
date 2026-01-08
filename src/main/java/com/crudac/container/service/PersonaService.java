package com.crudac.container.service;

import com.crudac.container.entity.Persona;
import com.crudac.container.repository.PersonaRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    private final PersonaRepository repository;

    public PersonaService(PersonaRepository repository) {
        this.repository = repository;
    }

    public List<Persona> listar() {
        return repository.findAll();
    }

    public Optional<Persona> obtener(Long id) {
        return repository.findById(id);
    }

    public Persona guardar(Persona persona) {
        return repository.save(persona);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public Page<Persona> filtrarPorNombre(String nombre, Pageable pageable) {
        return repository.findByNombreContainingIgnoreCase(nombre, pageable);
    }

    public List<Persona> filtrarViaStoredProcedure(String nombre) {
        return repository.filtrarPorStoredProcedure(nombre);
    }

    public Page<Persona> buscarPorNombrePaginado(String nombre, Pageable pageable) {
        return repository.findByNombreContainingIgnoreCase(nombre, pageable);
    }

    public List<Persona> buscarPorNombreExacto(String nombre) {
        return repository.findByNombre(nombre);
    }

    public List<Persona> buscarPorNombreIgnoreCase(String nombre) {
        return repository.findByNombreIgnoreCase(nombre);
    }

    public List<Persona> buscarPorFragmentoNombre(String fragmento) {
        return repository.findByNombreContaining(fragmento);
    }

    public List<Persona> buscarPorFragmentoNombreIgnoreCase(String fragmento) {
        return repository.findByNombreContainingIgnoreCase(fragmento);
    }

    public List<Persona> buscarPorNombreIniciaCon(String prefijo) {
        return repository.findByNombreStartsWith(prefijo);
    }

    public List<Persona> buscarPorNombreTerminaCon(String sufijo) {
        return repository.findByNombreEndsWith(sufijo);
    }

    public List<Persona> buscarPorNombreYCorreoFin(String nombre, String correoFin) {
        return repository.findByNombreContainingIgnoreCaseAndCorreoEndingWith(nombre, correoFin);
    }

    public List<Persona> buscarPorNombreQuery(String nombre) {
        return repository.buscarPorNombre(nombre);
    }

    public Optional<Persona> buscarPorNombreYCorreo(String nombre, String correo) {
        return repository.buscarPorNombreYCorreo(nombre, correo);
    }

    public List<Persona> buscarPorCorreoParcial(String correo) {
        return repository.buscarPorCorreoParcial(correo);
    }

    public List<Persona> buscarPorNombreMayorA(int longitud) {
        return repository.nombreMayorA(longitud);
    }

    public List<Persona> buscarPorIds(List<Long> ids) {
        return repository.buscarPorIds(ids);
    }

    public List<Persona> buscarSinCorreo() {
        return repository.sinCorreo();
    }

    public List<Persona> buscarOrdenadoPorNombre() {
        return repository.ordenarPorNombre();
    }

    public Long contarConCorreo() {
        return repository.contarConCorreo();
    }

    public List<Persona> filtrarPorStoredProcedure(String nombre) {
        return repository.filtrarPorStoredProcedure(nombre);
    }

}
