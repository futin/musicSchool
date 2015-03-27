package com.example.futin.tabletest.RESTService.data;

import java.util.HashMap;

/**
 * Created by Futin on 3/3/2015.
 */
public class RSDataSingleton {
    private static RSDataSingleton instance;
    private HashMap hasMap;

    public static RSDataSingleton getInstance() {
        if(instance==null){
            instance=new RSDataSingleton();
        }
            return instance;
    }
    public RSDataSingleton(){
        hasMap=new HashMap();
    }

    public RSServerURL getServerUrl(){
        return new RSServerURL();
    }
    public void insertDataInMap(String key, Object value) {
        hasMap.put(key, value);
    }

    public Object getMapData(String key) {
        return hasMap.get(key);
    }

    public void deleteMapData(String key) {
        hasMap.remove(key);
    }


}
