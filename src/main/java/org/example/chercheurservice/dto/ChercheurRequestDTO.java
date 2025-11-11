package org.example.chercheurservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChercheurRequestDTO {
    private String nom;
    private String prenom;
    private String email;
    private String specialite;
    private Long enseignantId;
}
