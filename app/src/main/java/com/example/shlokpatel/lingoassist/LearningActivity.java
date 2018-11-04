package com.example.shlokpatel.lingoassist;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class LearningActivity extends AppCompatActivity {
    ViewPager viewPager;
    FloatingActionButton actionButton;
    int[] arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        arrayList = new int[]{R.drawable.sprout,R.drawable.sprout1,R.drawable.sprout2};
        viewPager=findViewById(R.id.view_pager);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        actionButton=findViewById(R.id.btn_fab);
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
            switch (i){
                case 0 :
                    return new FragmentOne("hey is this you","hey es este tu",arrayList[0]);
                case 1 :
                    return new FragmentOne("Hi it is shlok","Hola es shlok",arrayList[1]);
                case 2 :
                    return new FragmentOne("Come over","Ven aquí",arrayList[2]);
                default:
                    return null;
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

    void AlertDialogShow(){
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Would you like to exit")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(LearningActivity.this,MainActivity.class);
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

    public void selectFragment(int position){
        viewPager.setCurrentItem(position, true);
// true is to animate the transaction
    }
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
}
