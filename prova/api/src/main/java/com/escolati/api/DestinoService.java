package com.escolati.api;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DestinoService {

    DestinoRepository destinoRepository;

    public Optional<Destino> getById(Long id) {
       return destinoRepository.findById(id);
    }

    public List<Destino> getAll() {

        List<Destino> all = destinoRepository.findAll();
        List<Destino> allFiltred = all.stream().filter(x -> x.getViagem() == null).toList();

        return allFiltred;
    }

    public void createDestino(Destino destino) {
        destinoRepository.save(destino);
    }

    public void deleteById(Long id) {
        getById(id).ifPresent(destinoRepository::delete);
    }

    public void updateDestino(DestinoDTO dto, Long id) {

        Destino viagem = destinoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("A viagem com id: [%s], não foi encontrada.", id)));

        if(dto.nome() != null) {
        viagem.setNome(dto.nome());
        }


        destinoRepository.save(viagem);

    }
}

