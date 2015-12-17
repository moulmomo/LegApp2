package com.example.mohamed.legapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.mohamed.legapp2.Beacon;

import java.util.ArrayList;
import java.util.List;


public class LocalisationActivity extends Activity {



    private ArrayList<Beacon> beacons;
    String text1 = " ";
    String text2 = " ";
    Lieu lieu;

    private LieuDAO dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localisation);

        dataSource = new LieuDAO(this);
        dataSource.open();

        List<Lieu> values = dataSource.getAllLieu();

        final ArrayAdapter<Lieu> adapter = new ArrayAdapter<Lieu>(this, android.R.layout.simple_list_item_1, values);

        beacons = new ArrayList<Beacon>();
        Intent intent = this.getIntent();
        beacons = intent.getParcelableArrayListExtra("key");

        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);

        int l = beacons.size();
        int m = adapter.getCount();
        //le RSSI decroit avec la distance
        for (int i=0;i<l;i++){
            text1 = text1+" Balise "+(i+1)+" : ("+beacons.get(i).major +" , "+ beacons.get(i).minor+" , "+beacons.get(i).rssi+")" ;
            for (int j=0;j<m;j++){
                if(beacons.get(i).rssi > -70 && beacons.get(i).minor.equals(adapter.getItem(j).getMinor()) && beacons.get(i).major.equals(adapter.getItem(j).getMajor())) {
                    text2 = text2+adapter.getItem(j).getName() +" ";
                    lieu = adapter.getItem(j);
                }
            }
        }


        textView1.setText(text1);
        if (text2 != " ") textView2.setText(text2);
        if(lieu != null) {
            if (lieu.getType() == "Lumière") textView3.setText("Lumière allumée");
            else {
                if (lieu.getType() == "Température") textView3.setText("Température : 25°C");
                else {
                    if (lieu.getType() == "Taux de CO2") textView3.setText("Taux de CO2 : 30%");
                }
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_localisation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id) {
            case R.id.action_settings: {
                return true;
            }

            case R.id.item_menu_ajouter_lieu:
                Intent intent1 = new Intent(this, com.example.mohamed.legapp2.AjouterLieu.class);
                startActivity(intent1);
        }

        return super.onOptionsItemSelected(item);
    }
}



/*
*  android:parentActivityName=".MainActivity" >
            <meta-data
                android:name="android.support.PARENT_ACTIVITY"
                android:value="com.example.mohamed.legapp2.MainActivity" />
* */