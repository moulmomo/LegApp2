package com.example.mohamed.legapp2;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.List;


public class AjouterLieu extends ListActivity {

    String name, major, minor, type;
    EditText nameEdit, majorEdit, minorEdit ;
    Spinner typeSpinner;
    Button ajouter, supprimer;
    private LieuDAO dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_lieu);

        dataSource = new LieuDAO(this);
        dataSource.open();

        List<Lieu> values = dataSource.getAllLieu();        //récupère le contenu de la base de données

        final ArrayAdapter<Lieu> adapter = new ArrayAdapter<Lieu>(this, android.R.layout.simple_list_item_1, values);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(adapter);

        nameEdit = (EditText)findViewById(R.id.nameEdit);
        majorEdit = (EditText)findViewById(R.id.majorEdit);
        minorEdit = (EditText)findViewById(R.id.minorEdit);
        typeSpinner = (Spinner)findViewById(R.id.typeSpinner);
        ajouter = (Button)findViewById(R.id.ajouter);
        supprimer = (Button)findViewById(R.id.supprimerPremier);

        ajouter.setOnClickListener(new View.OnClickListener(){
                @Override
            public void onClick(View v){
                    name = nameEdit.getText().toString();
                    major = majorEdit.getText().toString();
                    minor = minorEdit.getText().toString();
                    type = String.valueOf(typeSpinner.getSelectedItem());
                    Lieu lieu = dataSource.createLieu(name, major, minor, type);    //ajoute le lieu dans la base de données
                    adapter.add(lieu);                                              //ajoute le lieu dans la listView
                    adapter.notifyDataSetChanged();
            }
        });

        supprimer.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v){
                if(adapter.getCount()>0) {
                    Lieu lieu = adapter.getItem(0);
                    dataSource.deleteLieu(lieu);            //supprime le lieu de la base de données
                    adapter.remove(lieu);                   //supprime le lieu de la listView
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ajouter_lieu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }

}
