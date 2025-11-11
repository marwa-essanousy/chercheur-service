package org.example.chercheurservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChercheurResponseDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String specialite;
    private Long enseignantId;
}
