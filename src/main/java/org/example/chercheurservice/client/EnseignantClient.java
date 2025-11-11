package org.example.chercheurservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "enseignant-client",
        url = "http://localhost:8080"
)
public interface EnseignantClient {

    @GetMapping("/v1/enseignants/{id}")
    Object getEnseignantById(@PathVariable("id") Long id);
}

