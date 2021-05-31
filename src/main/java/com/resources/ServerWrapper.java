package com.resources;

import java.util.HashMap;
import java.util.Map;

public class ServerWrapper {
    String region;
    Float totalCost = 0.0F;
    Map<String, Integer> server = new HashMap<>();

    public Map<String, Integer> getServer() {
        return server;
    }

    public void setServer(Map<String, Integer> server) {
        this.server = server;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "{" +
                "region='" + region + '\'' +
                ", totalCost=" + totalCost +
                ", server=" + server +
                '}';
    }
}
