package org.example.chercheurservice.mapper;

import org.example.chercheurservice.dto.ChercheurRequestDTO;
import org.example.chercheurservice.dto.ChercheurResponseDTO;
import org.example.chercheurservice.entites.Chercheur;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ChercheurMapper {

    public Chercheur toEntity(ChercheurRequestDTO dto) {
        if (dto == null) return null;
        return Chercheur.builder()
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .email(dto.getEmail())
                .specialite(dto.getSpecialite())
                .enseignantId(dto.getEnseignantId())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    public ChercheurResponseDTO toResponse(Chercheur entity) {
        if (entity == null) return null;
        return ChercheurResponseDTO.builder()
                .id(entity.getId())
                .nom(entity.getNom())
                .prenom(entity.getPrenom())
                .email(entity.getEmail())
                .specialite(entity.getSpecialite())
                .enseignantId(entity.getEnseignantId())
                .build();
    }

    public void updateEntity(Chercheur entity, ChercheurRequestDTO dto) {
        entity.setNom(dto.getNom());
        entity.setPrenom(dto.getPrenom());
        entity.setEmail(dto.getEmail());
        entity.setSpecialite(dto.getSpecialite());
        entity.setEnseignantId(dto.getEnseignantId());
        entity.setUpdatedAt(Instant.now());
    }
}
