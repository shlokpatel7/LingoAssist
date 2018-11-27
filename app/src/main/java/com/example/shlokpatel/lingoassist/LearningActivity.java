package com.example.shlokpatel.lingoassist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class LearningActivity extends AppCompatActivity {
    ViewPager viewPager;
    FloatingActionButton actionButton;
    int[] arrayList;
    ArrayList<String> textList;
    int levelCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        if (!pref.contains("LOCALE_CODE")) {
            Intent intent = new Intent(this, LanguageFragment.class);
            startActivity(intent);
        }
        levelCount = getIntent().getIntExtra("LEVEL", 0);
        arrayList = new int[]{R.drawable.sprout, R.drawable.sprout1, R.drawable.sprout2};
        textList = new ArrayList<>();
        textList.add("");
        textList.add("My");
        textList.add("Least");
        textList.add("Good Morning");
        textList.add("Great day");
        textList.add("want some food ?");
        textList.add("I’m sorry");
        textList.add("How much does it cost?");
        textList.add("What’s your name");
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        actionButton = findViewById(R.id.btn_fab);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(getItem(+1), true);
            }
        });
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int i) {
            if (levelCount == 1) {
                switch (i) {
                    case 0:
                        return new FragmentOne(textList.get(0), null, arrayList[0]);
                    case 1:
                        return new FragmentOne(textList.get(1), null, arrayList[1]);
                    case 2:
                        return new FragmentOne(textList.get(2), null, arrayList[2]);
                    default:
                        return null;
                }
            }
            else if(levelCount==2){
                switch (i) {
                    case 0:
                        return new FragmentOne(textList.get(3), null, arrayList[0]);
                    case 1:
                        return new FragmentOne(textList.get(4), null, arrayList[1]);
                    case 2:
                        return new FragmentOne(textList.get(5), null, arrayList[2]);
                    default:
                        return null;
                }
            }else {
                switch (i) {
                    case 0:
                        return new FragmentOne(textList.get(6), null, arrayList[0]);
                    case 1:
                        return new FragmentOne(textList.get(7), null, arrayList[1]);
                    case 2:
                        return new FragmentOne(textList.get(8), null, arrayList[2]);
                    default:
                        return null;
                }
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.learn_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:
                AlertDialogShow();
                break;
        }
        return true;
    }

    void AlertDialogShow() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Would you like to exit")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(LearningActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        alertDialog.show();
    }

    public void selectFragment(int position) {
        viewPager.setCurrentItem(position, true);
// true is to animate the transaction
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
}
