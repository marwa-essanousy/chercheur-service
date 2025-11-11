package org.example.chercheurservice.entites;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "chercheur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chercheur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String specialite;

    private Long enseignantId;

    private Instant createdAt;
    private Instant updatedAt;
}
