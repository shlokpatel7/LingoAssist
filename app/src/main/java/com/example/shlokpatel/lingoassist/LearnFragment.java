package com.example.shlokpatel.lingoassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LearnFragment extends Fragment {
    CardView levelOne,levelTwo,levelThree;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_learn, container, false);
        levelOne=view.findViewById(R.id.level_one);
        levelTwo=view.findViewById(R.id.level_two);
        levelThree=view.findViewById(R.id.level_three);
        levelOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startLearn=new Intent(getActivity(),LearningActivity.class);
                startLearn.putExtra("LEVEL",1);
                startActivity(startLearn);
                getActivity().finish();
            }
        });
        levelTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startLearn=new Intent(getActivity(),LearningActivity.class);
                startLearn.putExtra("LEVEL",2);
                startActivity(startLearn);
                getActivity().finish();
            }
        });
        levelThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startLearn=new Intent(getActivity(),LearningActivity.class);
                startLearn.putExtra("LEVEL",3);
                startActivity(startLearn);
                getActivity().finish();
            }
        });
        return view;
    }

}
