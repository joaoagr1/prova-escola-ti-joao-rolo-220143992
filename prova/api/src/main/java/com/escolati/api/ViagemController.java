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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/viagens")
@CrossOrigin(
        origins = "http://localhost:4200",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*",
        allowCredentials = "true"
)
@AllArgsConstructor
public class ViagemController {

    public ViagemService viagemService;

    @GetMapping("/{id}")
    public ResponseEntity<Viagem> getViagem(@PathVariable Long id) {
        Viagem byId = viagemService.getById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("A viagem com id: [%s], não foi encontrada.", id)));
        return ResponseEntity.ok(byId);
    }

    @GetMapping
    public ResponseEntity<List<Viagem>> getAll() {
        List<Viagem> all = viagemService.getAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Viagem viagem) {
        viagemService.createViagem(viagem);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        viagemService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody ViagemDTO dto, @PathVariable Long id) {
        viagemService.updateStock(dto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{viagemId}/destinos")
    public ResponseEntity<Void> addDestinoToViagem(@PathVariable Long viagemId, @RequestBody Destino destino) {
        viagemService.addDestino(viagemId, destino);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{viagemId}/destinos/{destinoId}")
    public ResponseEntity<Void> removeDestinoFromViagem(@PathVariable Long viagemId, @PathVariable Long destinoId) {
        viagemService.removeDestino(viagemId, destinoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{viagemId}/destinos/name")
    public ResponseEntity<Void> removeDestinoFromViagemByName(@PathVariable Long viagemId, @RequestParam String destinoName) {
        viagemService.removeDestinoByName(viagemId, destinoName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}