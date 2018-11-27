package com.example.shlokpatel.lingoassist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AccountFragment extends Fragment {
    Button signOutBtn;
    CardView langBtn,botBtn;
    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD = 1234;
    FirebaseAuth auth;
    CircleImageView circleImageView;
    TextView userName, userMail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        auth = FirebaseAuth.getInstance();
        signOutBtn = view.findViewById(R.id.sign_out);
        userName = view.findViewById(R.id.user_name);
        botBtn = view.findViewById(R.id.bot_button);
        botBtn.setOnClickListener(lst_StartService);
        userMail = view.findViewById(R.id.user_mail);
        langBtn = view.findViewById(R.id.change_language);
        circleImageView = view.findViewById(R.id.user_image);
        if(auth.getCurrentUser() != null){
            userName.setText("Name : "+auth.getCurrentUser().getDisplayName());
            userMail.setText("Email : "+auth.getCurrentUser().getEmail());
            Picasso.get().load(auth.getCurrentUser().getPhotoUrl())
                    .error(R.drawable.user)
                    .into(circleImageView);
        }

        langBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),LanguageFragment.class);
                startActivity(intent);
            }
        });


        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                signInActivity();
            }
        });
        return view;
    }

    void signInActivity() {
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    Button.OnClickListener lst_StartService = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Log.d(Utils.LogTag, "lst_StartService -> Utils.canDrawOverlays(Main.this): " + Utils.canDrawOverlays(getContext()));

            if (Utils.canDrawOverlays(getContext()))
                startChatHead();
            else {
                requestPermission(OVERLAY_PERMISSION_REQ_CODE_CHATHEAD);
            }
        }

    };

    private void startChatHead() {
        getActivity().startService(new Intent(getActivity().getApplicationContext(), ChatHeadService.class));
    }

    private void needPermissionDialog(final int requestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("You need to allow permission");
        builder.setPositiveButton("OK",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        requestPermission(requestCode);
                    }
                });
        builder.setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.setCancelable(false);
        builder.show();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void requestPermission(int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD) {
            if (!Utils.canDrawOverlays(getContext())) {
                needPermissionDialog(requestCode);
            } else {
                startChatHead();
            }

        }

    }


}
