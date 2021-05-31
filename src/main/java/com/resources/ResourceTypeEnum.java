package com.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum ResourceTypeEnum {

    LARGE("large", 1),
    XLARGE("xlarge", 2),
    TWO_XLARGE("2xlarge", 4),
    FOUR_XLARGE("4xlarge", 8),
    EIGHT_XLARGE("8xlarge", 18),
    TEN_XLARGE("10xlarge", 32);

    private String name;
    private int numberOfCPU;

    private ResourceTypeEnum(String name, int numberOfCPU) {
        this.name = name;
        this.numberOfCPU = numberOfCPU;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfCPU() {
        return numberOfCPU;
    }

    public List<Integer> getNumberOfCPUs() {
        return Arrays.stream(ResourceTypeEnum.values()).map(resourceTypeEnum -> {return resourceTypeEnum.getNumberOfCPU();}).collect(Collectors.toList());
    }

    public String getName(int cpu) {
        List<String> names = Arrays.stream(ResourceTypeEnum.values()).filter(resourceTypeEnum -> resourceTypeEnum.getNumberOfCPU() == cpu ).map(resourceTypeEnum -> {return resourceTypeEnum.getName();}).collect(Collectors.toList());
        if(names != null && !names.isEmpty()) {
            return names.get(0);
        }
        return null;
    }

    public static List<ResourceTypeEnum> getResourceTypesByDescending() {
        List<ResourceTypeEnum> resources = Arrays.asList(ResourceTypeEnum.values());
        Collections.sort(resources, (a1, a2) -> Integer.compare(a2.getNumberOfCPU(), a1.getNumberOfCPU()));
        return resources;
    }
}
