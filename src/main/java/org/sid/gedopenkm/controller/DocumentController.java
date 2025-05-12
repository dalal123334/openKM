package org.sid.gedopenkm.controller;

import org.sid.gedopenkm.dto.DocumentInfo;
import org.sid.gedopenkm.dto.GedDocument;
import org.sid.gedopenkm.impl.GedApiClientAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final GedApiClientAdapter gedApiClientAdapter;

    @Autowired
    public DocumentController(GedApiClientAdapter gedApiClientAdapter) {
        this.gedApiClientAdapter = gedApiClientAdapter;
    }

    @PostMapping("/add")
    public ResponseEntity<GedDocument> addDocument(@RequestBody DocumentInfo documentInfo) {
        try {
            GedDocument addedDocument = gedApiClientAdapter.ajouterDocument(documentInfo);
            return ResponseEntity.ok(addedDocument);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Vous pouvez personnaliser la réponse d'erreur
        }
    }
}
