package org.sid.gedopenkm.impl;


import org.sid.gedopenkm.dto.DocumentInfo;
import org.sid.gedopenkm.dto.GedDocument;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.net.URL;
import org.springframework.http.HttpHeaders;;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class GedApiClientAdapterImpl implements GedApiClientAdapter
{
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/OpenKM";
    private HttpHeaders headers;

    public GedApiClientAdapterImpl() {
        authenticate();
    }

    private void authenticate() {
        String loginUrl = baseUrl + "/frontend/login";

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", "okmAdmin");
        form.add("password", "admin");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, requestHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(loginUrl, request, String.class);
        List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);

        headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.put(HttpHeaders.COOKIE, cookies);
    }

    @Override
    public GedDocument ajouterDocument(DocumentInfo documentInfo) {
        try {
            GedDocument gedDoc = documentInfo.getDocumentContentList().get(0);
            String filePath = "/okm:root/Personne/dalal" + gedDoc.getFileName();

            // Contenu du fichier
            ByteArrayResource content = new ByteArrayResource(gedDoc.getContent()) {
                @Override
                public String getFilename() {
                    return gedDoc.getFileName();
                }
            };

            // Construction du body
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("docPath", filePath);
            body.add("content", content);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            restTemplate.postForEntity(baseUrl + "/services/rest/document/create", requestEntity, String.class);

            // On retourne le document mis à jour
            gedDoc.setFileName(gedDoc.getFileName());
            documentInfo.setPid("REST_API_NO_UUID"); // L’API REST ne retourne pas toujours l’UUID, à adapter si besoin
            return gedDoc;

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout du document avec l’API REST d’OpenKM", e);
        }
    }

    @Override
    public void modifierDocument(DocumentInfo documentInfo) {
        modifierDocument(documentInfo.getPid(), documentInfo.getIndexes());
    }

    @Override
    public void modifierDocument(String pid, Map<String, Object> indexes) {
        // ⚠️ L’API REST d’OpenKM ne fournit pas toujours une méthode directe pour modifier les propriétés (groupes).
        // Il faut l’implémenter uniquement si ton instance d’OpenKM expose un endpoint spécifique ou via extension.
        throw new UnsupportedOperationException("Modification des propriétés via REST non encore implémentée.");
    }
}
