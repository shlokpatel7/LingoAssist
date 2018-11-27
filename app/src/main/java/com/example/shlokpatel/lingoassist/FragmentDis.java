package com.example.shlokpatel.lingoassist;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SuppressLint("ValidFragment")
public class FragmentDis extends android.support.v4.app.Fragment {
    TextView mainString, transString;
    ImageView treeImage, speakBtn;
    String mainText, transText;
    int picId;
    TextToSpeech textToSpeech;

    public FragmentDis(String mainText, String transText, int picId) {
        this.mainText = mainText;
        this.transText = transText;
        this.picId = picId;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_one, container, false);
        treeImage = view.findViewById(R.id.tree_image);
        speakBtn = view.findViewById(R.id.speak);
        mainString = view.findViewById(R.id.main_string);
        transString = view.findViewById(R.id.translated_string);
        mainString.setText(mainText);
        Log.e("TAG", "onCreateView: "+mainText);

        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Locale locSpanish = new Locale("fr");
                    int ttsLang = textToSpeech.setLanguage(locSpanish);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                } else {
                    Toast.makeText(getContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        DoTheShit();


        treeImage.setImageResource(picId);

        speakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }
        });
        return view;
    }

    private void speakOut() {

        int speechStatus = textToSpeech.speak(transString.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);

        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text to Speech!");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    void DoTheShit() {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("text", mainText)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.43.222:2001/leaf")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "onFailure: " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                resp = resp.replaceAll("<EOS>", "");
                resp = resp.replaceAll(".", "");
                resp = resp.replaceAll(",", "");
                Log.e("TAG", "onResponse: " + resp);
                final String finalResp = resp;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        transString.setText(finalResp);
                    }
                });
            }
        });
    }
}


