package com.crudac.container.repository;


import com.crudac.container.entity.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    // SELECT * FROM personas WHERE LOWER(nombre) LIKE LOWER('%juan%');
    // personaRepository.findByNombreContainingIgnoreCase("juan", PageRequest.of(0, 10));
    Page<Persona> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    // SELECT * FROM personas WHERE nombre = 'Carlos';
    List<Persona> findByNombre(String nombre);
    // SELECT * FROM personas WHERE LOWER(nombre) = LOWER('Carlos');
    List<Persona> findByNombreIgnoreCase(String nombre);
    // SELECT * FROM personas WHERE nombre LIKE '%Carlos%';
    List<Persona> findByNombreContaining(String fragmento);
    // SELECT * FROM personas WHERE LOWER(nombre) LIKE LOWER('%Carlos%');
    List<Persona> findByNombreContainingIgnoreCase(String fragmento);
    // SELECT * FROM personas WHERE nombre LIKE 'Car%';
    List<Persona> findByNombreStartsWith(String prefijo);
    // SELECT * FROM personas WHERE nombre LIKE '%los';
    List<Persona> findByNombreEndsWith(String sufijo);
    /*
    SELECT *
    FROM personas
    WHERE LOWER(nombre) LIKE LOWER('%' || :nombre || '%')
      AND correo LIKE '%' || :correoFin;
  * */
    List<Persona> findByNombreContainingIgnoreCaseAndCorreoEndingWith(String nombre, String correoFin);


    /*------by query-------*/
    @Query("SELECT p FROM Persona p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Persona> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT p FROM Persona p WHERE p.nombre = :nombre AND p.correo = :correo")
    Optional<Persona> buscarPorNombreYCorreo(@Param("nombre") String nombre, @Param("correo") String correo);

    @Query("SELECT p FROM Persona p WHERE LOWER(p.correo) LIKE LOWER(CONCAT('%', :correo, '%'))")
    List<Persona> buscarPorCorreoParcial(@Param("correo") String correo);

    @Query("SELECT p FROM Persona p WHERE LENGTH(p.nombre) > :longitud")
    List<Persona> nombreMayorA(@Param("longitud") int longitud);

    @Query("SELECT p FROM Persona p WHERE p.id IN :ids")
    List<Persona> buscarPorIds(@Param("ids") List<Long> ids);

    @Query("SELECT p FROM Persona p WHERE p.correo IS NULL")
    List<Persona> sinCorreo();

    @Query("SELECT p FROM Persona p ORDER BY p.nombre ASC")
    List<Persona> ordenarPorNombre();

    @Query("SELECT COUNT(p) FROM Persona p WHERE p.correo IS NOT NULL")
    Long contarConCorreo();
    /*
    CREATE OR REPLACE FUNCTION filtrar_personas(nombre_input text)
    RETURNS TABLE(id bigint, nombre text, correo text)
    AS $$
    BEGIN
        RETURN QUERY
        SELECT id, nombre, correo
        FROM personas
        WHERE LOWER(nombre) LIKE LOWER('%' || nombre_input || '%');
    END;
    $$ LANGUAGE plpgsql;
    * */
    @Query(value = "SELECT * FROM filtrar_personas(:nombre)", nativeQuery = true)
    List<Persona> filtrarPorStoredProcedure(@Param("nombre") String nombre);
}
