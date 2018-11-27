package com.example.shlokpatel.lingoassist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MyDialog extends Activity {
    public static boolean active = false;
    public static Activity myDialog;
    RecyclerView recyclerView;
    EditText editText;
    TextView submitBtn,emailBot;
    LinearLayout linearLayout;
    MessageAdapter messageAdapter;
    List<MyText> list;
    CircleImageView circleImageView;
    private String localeCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        if (pref.contains("LOCALE_CODE")) {
            localeCode = pref.getString("LOCALE_CODE", null);
        } else {
            Intent intent = new Intent(this, LanguageFragment.class);
            startActivity(intent);
        }
        linearLayout=findViewById(R.id.layout_one);
        linearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        circleImageView=findViewById(R.id.image_bot);
        emailBot=findViewById(R.id.email_bot);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Picasso.get().load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                    .error(R.drawable.user)
                    .into(circleImageView);
            emailBot.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+"\n"+
                    FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }
        list = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        editText = (EditText) findViewById(R.id.input_text);
        submitBtn = (TextView) findViewById(R.id.send_btn);
        myDialog = MyDialog.this;
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        messageAdapter = new MessageAdapter(this, list);
        recyclerView.setAdapter(messageAdapter);
        submitBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    String string = editText.getText().toString();
                    MyText myText = new MyText(string, 1);
                    list.add(myText);
                    messageAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(messageAdapter.getItemCount()-1);
                    editText.setText("");
                    DoTheShit(localeCode, string);
                }
            }
        });
		
		/*btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str = edt.getText().toString();
				if(str.length() > 0){
//					ChatHeadService.showMsg(MyDialog.this, str);
					Intent it = new Intent(MyDialog.this, ChatHeadService.class);
					it.putExtra(Utils.EXTRA_MSG, str);
					startService(it);
				}
			}
		});*/

    }

    void DoTheShit(String localeCode, String text) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("text", text)
                .build();

        Request request = new Request.Builder()
                .url("https://app.pairaphrase.com/apiv2/translate?token="+R.string.API_KEY+"&source=en-US&target="
                        + localeCode + "-" + localeCode.toUpperCase())
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "onFailure: " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonResp = response.body().string();
                Gson gson = new Gson();
                final TranslationResponse translationResponse = gson.fromJson(jsonResp, TranslationResponse.class);
                Log.e("TAG", "onResponse: "+translationResponse);
                MyDialog.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MyText myText = new MyText(translationResponse.getTranslation(), 2);
                        list.add(myText);
                        recyclerView.scrollToPosition(messageAdapter.getItemCount()-1);
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        active = true;
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        active = false;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        active = false;
    }


}
