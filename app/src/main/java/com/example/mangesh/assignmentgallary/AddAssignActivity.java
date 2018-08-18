package com.example.mangesh.assignmentgallary;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class AddAssignActivity extends AppCompatActivity {

    private static final String TAG = "Exception";
    TextInputEditText addAssignName;
    TextInputEditText addAssignSubject;
    Button addAssignSave;
    ImageButton pickDate, info;
    EditText addAssignDeadline;
    Spinner addAssignStatus, addAssignAvailability;
    String[] get_status, get_availability;
    String assignName, assignSubject, assignDeadLine, assignStatus, assignAvailability;
    DeadLine deadLine;
    DataBaseHandler DataBaseHandler;
    ImageView navBack;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assign);

        init();

        get_status = new String[]{"Not written","Written but not checked","Written and Checked"};
        get_availability = new String[]{"Unavailable","Available","Given to friend","Submitted to Teacher"};

        ArrayAdapter<String> StatusAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,get_status);
        addAssignStatus.setAdapter(StatusAdapter);

        ArrayAdapter<String> AvailableAdapter = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,get_availability);
        addAssignAvailability.setAdapter(AvailableAdapter);

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddAssignActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        deadLine = new DeadLine(datePicker.getDayOfMonth(), datePicker.getMonth()+1, datePicker.getYear());
                        assignDeadLine = deadLine.getDeadLine();
                        addAssignDeadline.setText(assignDeadLine.toCharArray(), 0, assignDeadLine.toCharArray().length);

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();


            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(AddAssignActivity.this);
                builder.setCancelable(true);
                builder.setTitle("INFO");
                builder.setMessage("Deadline helps you get better suggestions about which assignment to write first. " +
                        "If you don't know the exact date you can always choose any relevant date up to which you want to complete the assignment. :)");

                builder.setPositiveButton("I know!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();

            }
        });

        addAssignSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (setVariables()) {

                        DataBaseHandler.AddAssignment(assignName, assignSubject, assignStatus, assignAvailability, assignDeadLine);
                        Toast.makeText(AddAssignActivity.this,"Assignment added successfully!!",Toast.LENGTH_SHORT).show();
                        clearFields();
                        finish();

                    }
                }catch (Exception e){
                    Log.e(TAG, "onClick: ", e );
                }

            }
        });

        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void clearFields() {

        addAssignName.setText("");
        addAssignSubject.setText("");
        addAssignDeadline.setText("");

    }

    private boolean setVariables() throws Exception {

        if (TextUtils.isEmpty(addAssignName.getText().toString().trim())) {
            addAssignName.setError("Field can't be empty");
            return false;
        }
        if (TextUtils.isEmpty(addAssignSubject.getText().toString().trim())) {
            addAssignSubject.setError("Field can't be empty");
            return false;
        }
        if (TextUtils.isEmpty(addAssignDeadline.getText().toString().trim())) {
            addAssignDeadline.setError("Field can't be empty");
            return false;
        }
        if(!deadLine.isAfterToday(addAssignDeadline.getText().toString())){
            addAssignDeadline.setError("Deadline can't be before today");
            return false;
        }
        assignName = addAssignName.getText().toString();
        assignSubject = addAssignSubject.getText().toString();
        assignDeadLine = addAssignDeadline.getText().toString();
        assignStatus = addAssignStatus.getSelectedItem().toString();
        assignAvailability = addAssignAvailability.getSelectedItem().toString();
        return true;

    }

    private void init() {

        addAssignName = (TextInputEditText) findViewById(R.id.addAssignNameInputLayout);
        addAssignSubject = (TextInputEditText) findViewById(R.id.addAssignSubjectInputLayout);
        addAssignSave =  findViewById(R.id.addAssignSaveButton);
        addAssignDeadline = findViewById(R.id.updateAssignDeadlinePicker);
        pickDate = findViewById(R.id.selectDate);
        info = findViewById(R.id.infoImageButton);
        addAssignStatus = findViewById(R.id.updateAssignStatusSpinner);
        addAssignAvailability = findViewById(R.id.updateAssignAvailabilitySpinner);
        DataBaseHandler = new DataBaseHandler(AddAssignActivity.this);
        navBack = findViewById(R.id.navBack);
    }

}
