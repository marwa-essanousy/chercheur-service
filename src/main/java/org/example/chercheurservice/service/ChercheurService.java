package org.example.chercheurservice.service;

import lombok.RequiredArgsConstructor;
import org.example.chercheurservice.client.EnseignantClient;
import org.example.chercheurservice.dto.ChercheurRequestDTO;
import org.example.chercheurservice.dto.ChercheurResponseDTO;
import org.example.chercheurservice.entites.Chercheur;
import org.example.chercheurservice.mapper.ChercheurMapper;
import org.example.chercheurservice.repository.ChercheurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import feign.FeignException.NotFound;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ChercheurService {

    private final ChercheurRepository repo;
    private final ChercheurMapper mapper;
    private final EnseignantClient enseignantClient;

    public List<ChercheurResponseDTO> findAll() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }

    public ChercheurResponseDTO findById(Long id) {
        Chercheur c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Chercheur introuvable"));
        return mapper.toResponse(c);
    }
    private final Logger log = LoggerFactory.getLogger(ChercheurService.class);

    public ChercheurResponseDTO create(ChercheurRequestDTO dto) {
        Long enseignantId = dto.getEnseignantId();
        if (enseignantId != null) {
            try {
                // Appel : si l'enseignant n'existe pas, Feign lèvera NotFound
                enseignantClient.getEnseignantById(enseignantId);
            } catch (NotFound nf) {
                log.warn("Enseignant id={} introuvable (404)", enseignantId);
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Impossible de créer le chercheur : enseignant id=" + enseignantId + " introuvable"
                );
            } catch (FeignException fe) {
                int status = fe.status();
                log.warn("FeignException lors vérif enseignant id={} : status={}, message={}", enseignantId, status, fe.getMessage());

                if (status > 0) {
                    throw new ResponseStatusException(
                            HttpStatus.SERVICE_UNAVAILABLE,
                            "Impossible de vérifier l'enseignant (service renvoie " + status + ")"
                    );
                }
                throw new ResponseStatusException(
                        HttpStatus.SERVICE_UNAVAILABLE,
                        "Impossible de joindre l'enseignant-service (erreur réseau)",
                        fe
                );
            } catch (Exception e) {
                log.error("Erreur inattendue lors de la vérification enseignant id={}", enseignantId, e);
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Erreur lors de la vérification de l'enseignant", e);
            }
        }

        Chercheur entity = mapper.toEntity(dto);
        return mapper.toResponse(repo.save(entity));
    }


    public ChercheurResponseDTO update(Long id, ChercheurRequestDTO dto) {
        Chercheur existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Chercheur introuvable"));
        mapper.updateEntity(existing, dto);
        return mapper.toResponse(repo.save(existing));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public long countByEnseignant(Long enseignantId) {
        return repo.countByEnseignantId(enseignantId);
    }
}
