package com.example.mangesh.assignmentgallary;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CardStackFragment extends android.support.v4.app.Fragment {

    private int position;
    String[] num;
    String[] nameAssign, subject, status, remDays;
    CardStackModel cardStackModel;
    public CardStackFragment() {
    }

    @SuppressLint("ValidFragment")
    public CardStackFragment(int position, CardStackModel cardStackModel) {
        this.position = position;
        this.cardStackModel = cardStackModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_stack_fragment,null);

        nameAssign = cardStackModel.getNameOfAssign();
        subject = cardStackModel.getSubjectOfAssign();
        status = cardStackModel.getStatusOfAssign();
        remDays = cardStackModel.getRemainingDays();

        TextView nameOfAssign = view.findViewById(R.id.nameOfAssigTextView);
        TextView subjectOfAssign = view.findViewById(R.id.subjectOFAssignTextView);
        TextView statusOfAssign = view.findViewById(R.id.statusOfAssignTextView);
        TextView remOfAssign = view.findViewById(R.id.remDaysTextView);
        CardView card = view.findViewById(R.id.cardStackCard);

        if(status[position].equals("Not written")){
            card.setBackgroundColor(getResources().getColor(R.color.notWrittenCardColor));
        }else{
            card.setBackgroundColor(getResources().getColor(R.color.writtenCardColor));
        }

        nameOfAssign.setText(nameAssign[position]);
        subjectOfAssign.setText(subject[position]);
        statusOfAssign.setText(status[position]);
        remOfAssign.setText(remDays[position]);

        return view;
    }

}
