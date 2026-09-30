package com.example.notas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.notas.model.Nota;
import com.example.notas.repository.NotaRepository;

@RestController 
@RequestMapping("/api/notas")
// Permite cualquier puerto de localhost durante desarrollo para evitar bloqueos por CORS
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {
    RequestMethod.GET, 
    RequestMethod.POST, 
    RequestMethod.PUT, 
    RequestMethod.DELETE, 
    RequestMethod.OPTIONS
})
public class NotaController {

    @Autowired 
    private NotaRepository notaRepository;

    @GetMapping
    public List<Nota> getAllNotas() {
        return notaRepository.findAll();
    } 

    @PostMapping
    public ResponseEntity<Nota> createNota(@RequestBody Nota nota) {
        Nota nuevaNota = notaRepository.save(nota);
        return new ResponseEntity<>(nuevaNota, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Nota> updateNota(@PathVariable Long id, @RequestBody Nota notaDetalles) {
        return notaRepository.findById(id)
            .map(nota -> {
                nota.setTitulo(notaDetalles.getTitulo());
                nota.setContenido(notaDetalles.getContenido());
                Nota notaActualizada = notaRepository.save(nota);
                return ResponseEntity.ok(notaActualizada);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNota(@PathVariable Long id) {
        if (notaRepository.existsById(id)) {
            notaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    } 
}