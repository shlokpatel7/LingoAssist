package com.example.shlokpatel.lingoassist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

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
public class FragmentOne extends Fragment {
    TextView mainString,transString;
    ImageView treeImage,speakBtn;
    String mainText,transText;
    int picId;
    String localeCode;
    TextToSpeech textToSpeech;
    public FragmentOne(String mainText, String transText, int picId) {
        this.mainText = mainText;
        this.transText = transText;
        this.picId = picId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment_one,container,false);
        treeImage=view.findViewById(R.id.tree_image);
        speakBtn=view.findViewById(R.id.speak);
        mainString=view.findViewById(R.id.main_string);
        transString=view.findViewById(R.id.translated_string);
        mainString.setText(mainText);

        SharedPreferences pref = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        if (pref.contains("LOCALE_CODE")) {
            localeCode = pref.getString("LOCALE_CODE", null);
            textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        Locale locSpanish = new Locale(localeCode);
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
            Log.e("TAG", "locale code in fragment one: " + localeCode);
            DoTheShit(localeCode);
        }
        else{
            Intent intent=new Intent(getActivity(),LanguageFragment.class);
            startActivity(intent);
        }

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

        int speechStatus = textToSpeech.speak(transString.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);

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

    void DoTheShit(String localeCode){
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("text",mainText)
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
                Log.e("TAG", "json resp code in fragment one: " + jsonResp);
                Gson gson = new Gson();
                final TranslationResponse translationResponse = gson.fromJson(jsonResp, TranslationResponse.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        transString.setText(translationResponse.getTranslation());
                    }
                });
            }
        });
    }
}
