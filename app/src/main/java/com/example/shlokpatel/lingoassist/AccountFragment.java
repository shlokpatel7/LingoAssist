package com.example.shlokpatel.lingoassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {
    Button signOutBtn;
    FirebaseAuth auth;
    CircleImageView circleImageView;
    TextView userName,userMail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_account, container, false);
        auth=FirebaseAuth.getInstance();
        signOutBtn=view.findViewById(R.id.sign_out);
        userName=view.findViewById(R.id.user_name);
        userMail=view.findViewById(R.id.user_mail);
        circleImageView=view.findViewById(R.id.user_image);
        if(auth.getCurrentUser() != null){
            userName.setText("Name : "+auth.getCurrentUser().getDisplayName());
            userMail.setText("Email : "+auth.getCurrentUser().getEmail());
            Picasso.get().load(auth.getCurrentUser().getPhotoUrl())
                    .error(R.drawable.user)
                    .into(circleImageView);
            Log.e("TAG", "onCreateView: "+auth.getCurrentUser().getPhotoUrl());
        }
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                signInActivity();
            }
        });
        return view;
    }


    void signInActivity(){
        Intent intent=new Intent(getActivity(),SignInActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
