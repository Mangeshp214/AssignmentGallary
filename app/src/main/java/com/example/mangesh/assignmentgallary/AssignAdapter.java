package com.example.mangesh.assignmentgallary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AssignAdapter extends ArrayAdapter<AssignModel> {

    Context context;
    int tab;

    public AssignAdapter(Context context, ArrayList<AssignModel> assign, int tab) {

        super(context,0,assign);
        this.context = context;
        this.tab = tab;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        final LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(listItemView == null){
            listItemView = inflater.inflate(R.layout.card_list_fragment,parent,false);
        }

        final AssignModel currentPos = getItem(position);
        TextView A_Name = (TextView)listItemView.findViewById(R.id.assignName);
        TextView A_Sub = (TextView)listItemView.findViewById(R.id.assignSubject);
        TextView A_Status = (TextView)listItemView.findViewById(R.id.assignStatus);
        TextView A_Availability = (TextView)listItemView.findViewById(R.id.assignAvailability);
        ImageView deleteAssign = listItemView.findViewById(R.id.assignDelete);
        ImageView editAssign = listItemView.findViewById(R.id.assignEdit);

        A_Name.setText(currentPos.getA_Name());
        A_Sub.setText(currentPos.getA_Sub());
        A_Status.setText(currentPos.getA_Status());
        A_Availability.setText(currentPos.getA_Availability());

        deleteAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);
                builder.setTitle("DELETE");
                builder.setMessage("Are you sure you want to delete. This can not be undone!");
                builder.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataBaseHandler db = new DataBaseHandler(context);
                        if(db.DeleteSelectedItem(currentPos.getA_Name())){
                            Toast.makeText(context, "Deleted Assignment " + currentPos.getA_Name(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, GalleryDrawerActivity.class);
                            intent.putExtra("tab", tab);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            context.startActivity(intent);
                            ((Activity)context).finish();
                            ((Activity) context).overridePendingTransition(0,0);
                        }

                    }
                })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                builder.create().show();

            }
        });

        editAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateAssignActivity.class);
                intent.putExtra("assignName",currentPos.getA_Name());
                intent.putExtra("assignSubject",currentPos.getA_Sub());
                intent.putExtra("assignDeadline",currentPos.getA_Deadline());
                intent.putExtra("assignStatus",currentPos.getA_Status());
                intent.putExtra("assignAvailability",currentPos.getA_Availability());
                intent.putExtra("tab",tab);
                context.startActivity(intent);
                //Toast.makeText(context, "Edit button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return listItemView;
    }

    public String getAssignName(int pos){

        AssignModel currentPos = getItem(pos);
        String name = currentPos.getA_Name();
        return name;
    }

}
