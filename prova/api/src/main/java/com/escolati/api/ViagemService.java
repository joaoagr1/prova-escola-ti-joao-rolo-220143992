package com.escolati.api;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ViagemService {

    ViagemRepository viagemRepository;

    public Optional<Viagem> getById(Long id) {
       return viagemRepository.findById(id);
    }

    public List<Viagem> getAll() {
        return viagemRepository.findAll();
    }

    public void createViagem(Viagem viagem) {
        viagemRepository.save(viagem);
    }

    public void deleteById(Long id) {
        getById(id).ifPresent(viagemRepository::delete);
    }

    public void updateStock(ViagemDTO dto, Long id) {

        Viagem viagem = viagemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("A viagem com id: [%s], não foi encontrada.", id)));

        if(dto.nome() != null) {
        viagem.setNome(dto.nome());
        }

        if(dto.dataSaida() != null){
            viagem.setDataSaida(dto.dataSaida());
        }

        if(dto.valor() != null) {
            viagem.setValor(dto.valor());
        }

        if(dto.dataChegado() != null) {
            viagem.setDataChegada(dto.dataChegado());
        }

        viagemRepository.save(viagem);

    }
}

