package com.escolati.api;

import java.util.List;

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

import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping(path = "/api/destinos")
@CrossOrigin(
        origins = "http://localhost:4200",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*",
        allowCredentials = "true"
)
@AllArgsConstructor
public class DestinoController {

    public DestinoService destinoService;

    @GetMapping("/{id}")
    public ResponseEntity<Destino> getDestino(@PathVariable Long id) {
        Destino byId = destinoService.getById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("O destino com id: [%s], não foi encontrada.", id)));
        return ResponseEntity.ok(byId);
    }

    @GetMapping
    public ResponseEntity<List<Destino>> getAll() {
        List<Destino> all = destinoService.getAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Destino>> getAllData() {
        List<Destino> all = destinoService.getAllData();
        return ResponseEntity.ok(all);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Destino destino) {
        destinoService.createDestino(destino);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        destinoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody DestinoDTO dto, @PathVariable Long id) {
        destinoService.updateDestino(dto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
