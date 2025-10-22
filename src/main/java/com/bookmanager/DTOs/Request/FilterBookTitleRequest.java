package com.bookmanager.DTOs.Request;

import java.util.Map;

public class FilterBookTitleRequest {

    private Map<Long, Integer> classifyMap;

    public Map<Long, Integer> getClassifyMap() {
        return classifyMap;
    }

    public void setClassifyMap(Map<Long, Integer> classifyMap) {
        this.classifyMap = classifyMap;
    }
}
