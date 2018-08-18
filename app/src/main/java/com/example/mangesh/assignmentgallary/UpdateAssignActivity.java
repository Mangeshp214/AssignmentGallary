package com.example.mangesh.assignmentgallary;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.Arrays;
import java.util.Calendar;

public class UpdateAssignActivity extends AppCompatActivity {

    private static final String TAG = "Exception in update";
    private String assignName, assignSubject, assignDeadline, deadline, assignStatus, assignAvailability;
    private TextInputEditText name, subject;
    private EditText updateDeadline;
    private Spinner status, availability;
    private ImageButton date, info;
    private DeadLine deadLineObj;
    private int posStatus, posAvailability;
    private ImageView navBack;
    private Button update;
    public int tab;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_assign);




        assignName = getIntent().getExtras().getString("assignName");
        assignSubject = getIntent().getExtras().getString("assignSubject");
        assignDeadline = getIntent().getExtras().getString("assignDeadline");
        assignStatus = getIntent().getExtras().getString("assignStatus");
        assignAvailability = getIntent().getExtras().getString("assignAvailability");




        String[] get_status = new String[]{"Not written", "Written but not checked", "Written and Checked"};
        String[] get_availability = new String[]{"Unavailable", "Available", "Given to friend", "Submitted to Teacher"};

        posStatus = Arrays.asList(get_status).indexOf(assignStatus);
        posAvailability = Arrays.asList(get_availability).indexOf(assignAvailability);




        init();





        ArrayAdapter<String> StatusAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,get_status);
        status.setAdapter(StatusAdapter);

        ArrayAdapter<String> AvailableAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,get_availability);
        availability.setAdapter(AvailableAdapter);






        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateAssignActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        deadLineObj = new DeadLine(datePicker.getDayOfMonth(), datePicker.getMonth()+1, datePicker.getYear());
                        deadline = deadLineObj.getDeadLine();
                        updateDeadline.setText(deadline.toCharArray(), 0, deadline.toCharArray().length);

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();


            }
        });




        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(UpdateAssignActivity.this);
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




        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if(setVariables()){

                        DataBaseHandler db = new DataBaseHandler(UpdateAssignActivity.this);
                        if(db.updateAssignment(assignName, assignSubject, assignStatus, assignAvailability, assignDeadline)) {
                            Toast.makeText(UpdateAssignActivity.this,"Updated successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }catch (Exception e){
                    Log.e(TAG, "onClick: ", e);
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

    private boolean setVariables() throws Exception{

        if(!name.getText().toString().trim().equals(null)){
            if (TextUtils.isEmpty(name.getText().toString().trim())) {
                name.setError("Field can't be empty");
                return false;
            }
        }

        if(!subject.getText().toString().trim().equals(null)){
            if (TextUtils.isEmpty(subject.getText().toString().trim())) {
                subject.setError("Field can't be empty");
                return false;
            }
        }

        if(!updateDeadline.getText().toString().trim().equals("")){
            if (TextUtils.isEmpty(updateDeadline.getText().toString().trim())) {
                updateDeadline.setError("Field can't be empty");
                return false;
            }
        }

        if(!updateDeadline.getText().toString().equals(assignDeadline)){
            if(!deadLineObj.isAfterToday(updateDeadline.getText().toString())){
                updateDeadline.setError("Deadline can't be before today");
                return false;
            }
        }else if(!deadLineObj.isAfterToday(assignDeadline)){
            updateDeadline.setError("Deadline can't be before today");
            return false;
        }

        assignName = name.getText().toString();
        assignSubject = subject.getText().toString();
        assignDeadline = updateDeadline.getText().toString();
        assignStatus = status.getSelectedItem().toString();
        assignAvailability = availability.getSelectedItem().toString();
        return true;

    }

    private void init() {

        name = findViewById(R.id.AssignNameInputLayout);
        subject = findViewById(R.id.updateAssignSubjectInputLayout);
        updateDeadline = findViewById(R.id.updateAssignDeadlinePicker);
        status = findViewById(R.id.updateAssignStatusSpinner);
        availability = findViewById(R.id.updateAssignAvailabilitySpinner);
        date =  findViewById(R.id.selectDate);
        info = findViewById(R.id.infoImage);
        update = findViewById(R.id.updateAssignSaveButton);
        navBack = findViewById(R.id.navBack);

        name.setText(assignName);
        subject.setText(assignSubject);
        updateDeadline.setText(assignDeadline);
        status.setSelection(posStatus);
        availability.setSelection(posAvailability);

    }
}
