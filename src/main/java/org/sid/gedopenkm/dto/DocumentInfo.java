package org.sid.gedopenkm.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentInfo {
    private String familleGed;

    private String pid;

    private Map<String, Object> indexes = new HashMap<String, Object>();

    private List<GedDocument> documentContentList = new ArrayList<GedDocument>(0);




    public String getFamilleGed() {
        return familleGed;
    }

    public void setFamilleGed(String familleGed) {
        this.familleGed = familleGed;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Map<String, Object> getIndexes() {
        return indexes;
    }

    public void setIndexes(Map<String, Object> indexes) {
        this.indexes = indexes;
    }

    public List<GedDocument> getDocumentContentList() {
        return documentContentList;
    }

    public void setDocumentContentList(List<GedDocument> documentContentList) {
        this.documentContentList = documentContentList;
    }
}
