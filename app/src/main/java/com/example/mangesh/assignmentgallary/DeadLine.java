package com.example.mangesh.assignmentgallary;

import java.text.SimpleDateFormat;
import java.time.MonthDay;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

class DeadLine {

    String day, month;
    int year;

    public DeadLine() {
    }

    public DeadLine(int day, int month, int year) {
        this.day = String.format("%02d",day);
        this.month = String.format("%02d",month);
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getDeadLine(){
        return day+"-"+month+"-"+year;
    }

    public String[] sortDates(String[] deadLines) throws Exception{

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date[] deadLinesArray = new Date[deadLines.length];
        for (int i=0; i<deadLines.length; i++)
            deadLinesArray[i] = simpleDateFormat.parse(deadLines[i]);

        Arrays.sort(deadLinesArray);

        for (int i=0; i<deadLines.length; i++)
            deadLines[i] = simpleDateFormat.format(deadLinesArray[i]);

        return deadLines;

    }

    public long[] calcDateDiff(String[] deadLines) throws Exception {

        long[] dateDiff = new long[deadLines.length];
        Date todaysDate = new Date();

        Date[] deadLinesArray = new Date[deadLines.length];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        for (int i = 0; i<deadLines.length; i++)
            deadLinesArray[i] = simpleDateFormat.parse(deadLines[i]);

        for(int i=0; i<deadLines.length; i++){

            long temp;
            temp = deadLinesArray[i].getTime() - todaysDate.getTime();
            dateDiff[i] = TimeUnit.DAYS.convert(temp, TimeUnit.MILLISECONDS);

        }

        return dateDiff;

    }

    public boolean isAfterToday(String deadLine) throws Exception{

        Date todaysDate = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date deadlineDate = simpleDateFormat.parse(deadLine);

        if(deadlineDate.after(todaysDate))
            return true;

        return false;

    }

}
