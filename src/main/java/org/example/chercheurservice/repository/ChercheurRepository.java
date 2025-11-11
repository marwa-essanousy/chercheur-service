package org.example.chercheurservice.repository;

import org.example.chercheurservice.entites.Chercheur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChercheurRepository extends JpaRepository<Chercheur, Long> {
    long countByEnseignantId(Long enseignantId);
}
