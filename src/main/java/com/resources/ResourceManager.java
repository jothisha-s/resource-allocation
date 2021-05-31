package com.resources;

import java.util.*;

public class ResourceManager {

    public static void main(String... args) {
        int hours = 24;
        Integer cpu = null;
        Float price = 300.0F;
        List<ServerWrapper> results = getCosts(getInstances(), hours, cpu, price);
        Collections.sort(results, (a1, a2) -> Float.compare(a1.getTotalCost(), a2.getTotalCost()));
        System.out.println(results);

    }

    static List<ServerWrapper> getCosts(Map<String, Map<String, Float>> instances, int hours, Integer cpus, Float price) {
        List<ServerWrapper> result = new ArrayList<>();

            for (String region: instances.keySet()) {



                List<ResourceTypeEnum> resourceTypeEnumList = ResourceTypeEnum.getResourceTypesByDescending();
                Map<String, Float> sortedMap = sortByValue(instances.get(region));
                Map<String, Integer> server = new HashMap<>();
                if((price == null || price <= 0.0F) && (cpus != null && cpus > 0)) {
                    Integer cpuTobeAlloted = cpus;
                    allotServers(sortedMap, server, resourceTypeEnumList, cpuTobeAlloted);
                    if (cpuTobeAlloted > 0) {
                        resourceTypeEnumList = Arrays.asList(ResourceTypeEnum.values());
                        for (ResourceTypeEnum resourceTypeEnum : resourceTypeEnumList) {
                            if (instances.get(resourceTypeEnum.getName()) != null && cpuTobeAlloted <= resourceTypeEnum.getNumberOfCPU()) {
                                server.put(resourceTypeEnum.getName(), resourceTypeEnum.getNumberOfCPU());
                                cpuTobeAlloted = 0;
                            }
                        }
                    }
                } else if((cpus == null || cpus <= 0) && (price != null && price > 0.0F)) {
                    Integer cpuTobeAlloted = cpus;
                    Float priceRemaining = price;
                    allotServers(sortedMap, server, resourceTypeEnumList,  price, hours);

                } else {
                    // TODO
                }
                float totalCost = 0.0f;

                for(String key: server.keySet()) {
                    totalCost += server.get(key).intValue() * instances.get(region).get(key).floatValue() * hours;
                }
                ServerWrapper serverWrapper = new ServerWrapper();
                serverWrapper.setServer(server);
                serverWrapper.setRegion(region);
                serverWrapper.setTotalCost(totalCost);
                result.add(serverWrapper);

        }
        return result;
    }

    static void allotServers(Map<String, Float> instances, Map<String, Integer> servers, List<ResourceTypeEnum> resourceTypeEnumList, Integer cpuTobeAlloted) {
        for(ResourceTypeEnum resourceTypeEnum: resourceTypeEnumList) {
            if(instances.get(resourceTypeEnum.getName()) != null && cpuTobeAlloted >= resourceTypeEnum.getNumberOfCPU()) {
                int remaining = cpuTobeAlloted % resourceTypeEnum.getNumberOfCPU();
                int allotted = cpuTobeAlloted / resourceTypeEnum.getNumberOfCPU();
                servers.put(resourceTypeEnum.getName(), allotted);
                cpuTobeAlloted = remaining;
            }

        }
    }

    static void allotServers(Map<String, Float> instances, Map<String, Integer> servers, List<ResourceTypeEnum> resourceTypeEnumList, Float price, Integer hours) {
        for(ResourceTypeEnum resourceTypeEnum: resourceTypeEnumList) {
            if(instances.get(resourceTypeEnum.getName()) != null && price >= (instances.get(resourceTypeEnum.getName()).floatValue() * hours)) {
                Float remaining = price % (instances.get(resourceTypeEnum.getName()).floatValue() * hours);
                Float allotted = price / (instances.get(resourceTypeEnum.getName()).floatValue() * hours);
                servers.put(resourceTypeEnum.getName(), allotted.intValue());
                price = remaining;
            }

        }
    }

    static void allotServers(Map<String, Float> instances, Map<String, Integer> servers, List<ResourceTypeEnum> resourceTypeEnumList, Integer cpuTobeAlloted, Float price) {
        // TODO
    }

    static Map<String, Map<String, Float>> getInstances() {
        Map<String, Map<String, Float>> instances = new HashMap<>();
        Map<String, Float> instance = new HashMap<>();
        instance.put(ResourceTypeEnum.LARGE.getName(), 0.12F);
        instance.put(ResourceTypeEnum.XLARGE.getName(), 0.23F);
        instance.put(ResourceTypeEnum.TWO_XLARGE.getName(), 0.45F);
        instance.put(ResourceTypeEnum.FOUR_XLARGE.getName(), 0.774F);
        instance.put(ResourceTypeEnum.EIGHT_XLARGE.getName(), 1.4F);
        instance.put(ResourceTypeEnum.TEN_XLARGE.getName(), 2.82F);

        instances.put("us-east", instance);
        instance = new HashMap<>();
        instance.put(ResourceTypeEnum.LARGE.getName(), 0.14F);
        instance.put(ResourceTypeEnum.TWO_XLARGE.getName(), 0.413F);
        instance.put(ResourceTypeEnum.FOUR_XLARGE.getName(), 0.83F);
        instance.put(ResourceTypeEnum.EIGHT_XLARGE.getName(), 1.3F);
        instance.put(ResourceTypeEnum.TEN_XLARGE.getName(), 2.97F);
        instances.put("us-west", instance);

        return instances;
    }

    static List<String> getRegions() {
        List<String> regions = new ArrayList<>();
        regions.add("us-east");
        regions.add("us-west");
        return regions;
    }

    static HashMap<String, Float> sortByValue(Map<String, Float> hm) {
        List<Map.Entry<String, Float> > list =
                new LinkedList<Map.Entry<String, Float> >(hm.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Float> >() {
            public int compare(Map.Entry<String, Float> o1,
                               Map.Entry<String, Float> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        HashMap<String, Float> temp = new LinkedHashMap<String, Float>();
        for (Map.Entry<String, Float> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
