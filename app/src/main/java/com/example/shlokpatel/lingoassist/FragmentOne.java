package com.example.shlokpatel.lingoassist;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.util.Locale;


@SuppressLint("ValidFragment")
public class FragmentOne extends Fragment {
    TextView mainString,transString;
    ImageView treeImage;
    String mainText,transText;
    int picId;
    TextToSpeech tts;
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
        mainString=view.findViewById(R.id.main_string);
        transString=view.findViewById(R.id.translated_string);
        mainString.setText(mainText);
        transString.setText(transText);
        treeImage.setImageResource(picId);
        /*tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = tts.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    } else {
                        speakOut();
                    }

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }
        });
        tts.setLanguage("es","ES");
        tts.speak("Text to say aloud", TextToSpeech.QUEUE_ADD, null);*/
        return view;
    }
    /*private void speakOut() {

        String text = transString.getText().toString();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
*/
}
