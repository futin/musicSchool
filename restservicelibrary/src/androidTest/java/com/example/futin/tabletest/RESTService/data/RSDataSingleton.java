package com.example.futin.tabletest.RESTService.data;

/**
 * Created by Futin on 3/3/2015.
 */
public class RSDataSingleton {
    private static RSDataSingleton instance;

    public static RSDataSingleton getInstance() {
        if(instance==null){
            instance=new RSDataSingleton();
        }
            return instance;
    }

    public RSServerURL getServerUrl(){
        return new RSServerURL();
    }
}
