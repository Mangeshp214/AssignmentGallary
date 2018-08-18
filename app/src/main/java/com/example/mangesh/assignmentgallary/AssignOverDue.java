package com.example.mangesh.assignmentgallary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AssignOverDue extends AppCompatActivity {

    private TextView overdueCountTextView;
    private ListView overdueListView;
    private DataBaseHandler dataBaseHandler;
    private ArrayList<AssignModel> assignList;
    private AssignAdapter assignAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_over_due);

        init();

        assignList = dataBaseHandler.getAllAssignments();
        assignAdapter = new AssignAdapter(AssignOverDue.this, assignList, 0);
        overdueListView.setAdapter(assignAdapter);

    }

    private void init() {

        overdueListView = findViewById(R.id.listViewOverDue);
        overdueCountTextView = findViewById(R.id.textViewOverDueCount);
        dataBaseHandler = new DataBaseHandler(AssignOverDue.this);
        assignList  = new ArrayList<>();

    }
}
