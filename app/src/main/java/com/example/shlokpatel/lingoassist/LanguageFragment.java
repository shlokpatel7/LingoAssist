package com.example.shlokpatel.lingoassist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LanguageFragment extends Fragment {

    ListView recyclerView;
    private String[] arr = {"One", "Two", "Three", "Four", "Five", "Six"};
    int preSelectedIndex=-1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_language, container, false);
        ListView listView = view.findViewById(R.id.list_view);
        final List<LanguageModel> langs = new ArrayList<>();
        langs.add(new LanguageModel(false, "Spanish"));
        langs.add(new LanguageModel(false, "Hindi"));
        langs.add(new LanguageModel(false, "German"));
        langs.add(new LanguageModel(false, "Urdu"));
        langs.add(new LanguageModel(false, "Mandarin"));
        langs.add(new LanguageModel(false, "Arabic"));
        langs.add(new LanguageModel(false, "Russian"));
        langs.add(new LanguageModel(false, "Bengali"));
        langs.add(new LanguageModel(false, "Gujarati"));
        langs.add(new LanguageModel(false, "Latin"));

        final LanguageAdapter adapter = new LanguageAdapter(getActivity(), langs);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                LanguageModel model = langs.get(i); //changed it to model because viewers will confused about it

                model.setSelected(true);

                langs.set(i, model);

                if (preSelectedIndex > -1){

                    LanguageModel preRecord = langs.get(preSelectedIndex);
                    preRecord.setSelected(false);

                    langs.set(preSelectedIndex, preRecord);

                }

                preSelectedIndex = i;

                adapter.updateRecords(langs);
            }
        });
        return view;
    }

}
