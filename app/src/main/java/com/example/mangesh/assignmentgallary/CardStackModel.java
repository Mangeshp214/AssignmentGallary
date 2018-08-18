package com.example.mangesh.assignmentgallary;

import java.util.ArrayList;

public class CardStackModel {

    private long[] remainingDays;
    private String[] nameOfAssign, subjectOfAssign, statusOfAssign, remDays;

    public CardStackModel() {
    }

    public void setRemainingDays(long[] remainingDays) {
        this.remainingDays = remainingDays;
        convertToString();
    }

    public void setNameOfAssign(String[] nameOfAssign) {
        this.nameOfAssign = nameOfAssign;
    }

    public void setSubjectOfAssign(String[] subjectOfAssign) {
        this.subjectOfAssign = subjectOfAssign;
    }

    public void setStatusOfAssign(String[] statusOfAssign) {
        this.statusOfAssign = statusOfAssign;
    }

    public String[] getRemainingDays() {
        return remDays;
    }

    public String[] getNameOfAssign() {
        return nameOfAssign;
    }

    public String[] getStatusOfAssign() {
        return statusOfAssign;
    }

    public String[] getSubjectOfAssign() {
        return subjectOfAssign;
    }

    private void convertToString(){

        remDays =  new String[remainingDays.length];

        for(int i=0; i<remainingDays.length; i++)
            remDays[i] = remainingDays[i]+"";
    }
}
