package com.example.futin.tabletest.RESTService.request;

/**
 * Created by Futin on 3/22/2015.
 */
public class RSSearchForStudentRequest {
    String findStudentText;

    public RSSearchForStudentRequest(String findStudentText) {
        this.findStudentText = findStudentText;
    }

    public String getFindStudentText() {
        return findStudentText;
    }

    public void setFindStudentText(String findStudentText) {
        this.findStudentText = findStudentText;
    }

    @Override
    public String toString() {
        return "findStudentText="+findStudentText;
    }
}
