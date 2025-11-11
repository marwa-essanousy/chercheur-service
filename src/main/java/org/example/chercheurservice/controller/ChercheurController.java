package org.example.chercheurservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.chercheurservice.dto.ChercheurRequestDTO;
import org.example.chercheurservice.dto.ChercheurResponseDTO;
import org.example.chercheurservice.service.ChercheurService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/chercheurs")
@RequiredArgsConstructor
@Tag(name = "Chercheurs", description = "API de gestion des chercheurs")
@SecurityRequirement(name = "bearerAuth") // indique que Swagger doit demander un token
public class ChercheurController {

    private final ChercheurService svc;


    @Operation(summary = "Lister tous les chercheurs")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChercheurResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<ChercheurResponseDTO>> getAll() {
        return ResponseEntity.ok(svc.findAll());
    }


    @Operation(summary = "Récupérer un chercheur par ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Chercheur trouvé",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChercheurResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Chercheur non trouvé"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_USER')")
    public ResponseEntity<ChercheurResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(svc.findById(id));
    }


    @Operation(summary = "Créer un nouveau chercheur")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Chercheur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ChercheurResponseDTO> create(@RequestBody ChercheurRequestDTO dto) {
        return ResponseEntity.status(201).body(svc.create(dto));
    }


    @Operation(summary = "Mettre à jour un chercheur")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mise à jour réussie"),
            @ApiResponse(responseCode = "404", description = "Chercheur non trouvé"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ChercheurResponseDTO> update(@PathVariable Long id,
                                                       @RequestBody ChercheurRequestDTO dto) {
        return ResponseEntity.ok(svc.update(id, dto));
    }


    @Operation(summary = "Supprimer un chercheur")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Suppression réussie"),
            @ApiResponse(responseCode = "404", description = "Chercheur non trouvé"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Compter le nombre de chercheurs encadrés par un enseignant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Résultat du comptage renvoyé avec succès"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @GetMapping("/count/enseignant/{enseignantId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_USER')")
    public ResponseEntity<Long> countByEnseignant(@PathVariable Long enseignantId) {
        return ResponseEntity.ok(svc.countByEnseignant(enseignantId));
    }
}
