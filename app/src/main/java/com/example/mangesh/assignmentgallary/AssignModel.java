package com.example.mangesh.assignmentgallary;

class AssignModel {

    String A_Name,A_Sub,A_Status,A_Availability, A_Deadline;

    public AssignModel(String a_Name, String a_Sub, String a_Status, String a_Availability, String a_Deadline) {
        A_Name = a_Name;
        A_Sub = a_Sub;
        A_Status = a_Status;
        A_Availability = a_Availability;
        A_Deadline = a_Deadline;
    }

    public String getA_Name() {
        return A_Name;
    }

    public String getA_Sub() {
        return A_Sub;
    }

    public String getA_Status() {
        return A_Status;
    }

    public String getA_Availability() {
        return A_Availability;
    }

    public String getA_Deadline() {
        return A_Deadline;
    }
}
