package com.example.shlokpatel.lingoassist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LanguageFragment extends AppCompatActivity {
    int preSelectedIndex = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_language);
        getSupportActionBar().setTitle("Select a language to learn");
        final SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        ListView listView = findViewById(R.id.list_view);
        final List<LanguageModel> langs = new ArrayList<>();
        langs.add(new LanguageModel(false, "Spanish", "es"));
        langs.add(new LanguageModel(false, "German", "de"));
        langs.add(new LanguageModel(false, "French", "fr"));
        langs.add(new LanguageModel(false, "Russian", "ru"));

        final LanguageAdapter adapter = new LanguageAdapter(this,langs);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                LanguageModel model = langs.get(i); //changed it to model because viewers will confused about it

                model.setSelected(true);


                // We need an editor object to make changes
                SharedPreferences.Editor edit = pref.edit();

                // Store data. you may also use putFloat(), putInt(), putLong() as requirement
                edit.putString("LOCALE_CODE", model.getLocaleCode());

                // Commit the changes
                edit.commit();

                langs.set(i, model);

                if (preSelectedIndex > -1) {

                    LanguageModel preRecord = langs.get(preSelectedIndex);
                    preRecord.setSelected(false);

                    langs.set(preSelectedIndex, preRecord);

                }

                preSelectedIndex = i;

                adapter.updateRecords(langs);
            }
        });
    }

}
