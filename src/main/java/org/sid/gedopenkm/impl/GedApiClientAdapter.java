package org.sid.gedopenkm.impl;

import org.sid.gedopenkm.dto.DocumentInfo;
import org.sid.gedopenkm.dto.GedDocument;

import java.util.Map;

public interface GedApiClientAdapter {
    GedDocument ajouterDocument(DocumentInfo documentInfo);
    void modifierDocument(DocumentInfo documentInfo);
    void modifierDocument(String pid, Map<String, Object> indexes) ;
}
