package com.example.notas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.notas.model.Nota;
import com.example.notas.repository.NotaRepository;




@RestController 
@RequestMapping("/api/notas")
@CrossOrigin (origins = "http://localhost:5173")// esto es para que se mi fronted react

public class NotaController {
    @Autowired NotaRepository notaRepository;

    @GetMapping
    public List<Nota> getAllNotas(){
        return notaRepository.findAll();
    } 
    @PostMapping
    public Nota createNota(@RequestBody Nota nota) {
        return notaRepository.save(nota);
    }
    
     @PutMapping("/{id}") // mi error es put lo cambie por post
     public Nota updateNota(@PathVariable Long id, @RequestBody Nota notaDetalles) {
        Optional<Nota> optionalNota = notaRepository.findById(id);
        if(optionalNota.isPresent()) {
         Nota nota = optionalNota.get();
         nota.setTitulo(notaDetalles.getTitulo());
            nota.setContenido(notaDetalles.getContenido());
            return notaRepository.save(nota);
        } else {
            return null; // o lanzar una excepción si la nota no existe
        }
     }
     @DeleteMapping("/{id}")
     public String deleteNota(@PathVariable Long id) {
        Optional<Nota> optionalNota = notaRepository.findById(id);

        if(optionalNota.isPresent()) {
        notaRepository.delete(optionalNota.get());
            return "Nota eliminada correctamente";
        } else {
            return null; // o lanzar una excepción si la nota no existe
        }
     } 
            
    
}
