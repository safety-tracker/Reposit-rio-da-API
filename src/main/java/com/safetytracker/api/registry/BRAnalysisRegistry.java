package com.safetytracker.api.registry;

import com.safetytracker.api.analysis.BRAnalysis;

import java.util.HashMap;
import java.util.Map;

public class BRAnalysisRegistry {
    private static Map<String, Map<Integer, BRAnalysis>> analysisMap = new HashMap<>();

    public static Map<String, Map<Integer, BRAnalysis>> getAnalysisMap() {
        return analysisMap;
    }
}
