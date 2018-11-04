package com.example.shlokpatel.lingoassist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LearnFragment extends Fragment {
    CardView learningView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_learn, container, false);
        learningView=view.findViewById(R.id.learning_view);
        learningView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startLearn=new Intent(getActivity(),LearningActivity.class);
                startActivity(startLearn);
                getActivity().finish();
            }
        });
        return view;
    }

}
