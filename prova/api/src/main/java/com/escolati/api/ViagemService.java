package com.escolati.api;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createViagem(Viagem viagem) {

        if (viagem.getDestinos() != null) {

            for (int i = 0; i < viagem.getDestinos().size(); i++) {
                Destino destino = viagem.getDestinos().get(i);

                if (destino.getId() != null) {

                    destino = entityManager.merge(destino);
                }

                destino.setViagem(viagem);

                viagem.getDestinos().set(i, destino);
            }
        }

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

        if(dto.dataChegada() != null) {
            viagem.setDataChegada(dto.dataChegada());
        }

        viagemRepository.save(viagem);

    }

    public void addDestino(Long viagemId, Destino destino) {

        Viagem viagem = viagemRepository.findById(viagemId)
                .orElseThrow(() -> new EntityNotFoundException("Viagem com id: " + viagemId + " não encontrada."));

        destino.setViagem(viagem);

        viagem.getDestinos().add(destino);

        viagemRepository.save(viagem);

    }

    public void removeDestino(Long viagemId, Long destinoId) {

            Viagem viagem = viagemRepository.findById(viagemId)
                    .orElseThrow(() -> new EntityNotFoundException("Viagem não encontrada"));


            boolean removed = viagem.getDestinos().removeIf(destino -> destino.getId().equals(destinoId));

            if (!removed) {
                throw new EntityNotFoundException("Essa viagem não possui esse destino");
            }


            viagemRepository.save(viagem);
    }

    public void removeDestinoByName(Long viagemId, String destinoName) {

        Viagem viagem = viagemRepository.findById(viagemId)
                .orElseThrow(() -> new EntityNotFoundException("Viagem não encontrada"));

        boolean removed = viagem.getDestinos().removeIf(destino -> destino.getNome().equals(destinoName));

        if (!removed) {
            throw new EntityNotFoundException("Essa viagem não possui esse destino");
        }

        viagemRepository.save(viagem);

    }
}

