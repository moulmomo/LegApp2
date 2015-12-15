package com.example.mohamed.legapp2;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.List;


public class AjouterLieu extends ListActivity {

    String name, major, minor, type;
    String text = " ";
    TextView listeLieux;
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

        List<Lieu> values = dataSource.getAllLieu();

        final ArrayAdapter<Lieu> adapter = new ArrayAdapter<Lieu>(this, android.R.layout.simple_list_item_1, values);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(adapter);

        /*int l = values.size();
        for(int i=0;i<l;i++){
            text = text +"("+values.get(i).getName()+" , "+values.get(i).getMajor()+values.get(i).getMinor()+" , "+values.get(i).getType()+")";
        }

        listeLieux.setText(text);*/

        nameEdit = (EditText)findViewById(R.id.nameEdit);
        majorEdit = (EditText)findViewById(R.id.majorEdit);
        minorEdit = (EditText)findViewById(R.id.minorEdit);
        typeSpinner = (Spinner)findViewById(R.id.typeSpinner);
        ajouter = (Button)findViewById(R.id.ajouter);
        supprimer = (Button)findViewById(R.id.supprimerPremier);


        ajouter.setOnClickListener(ajouterListener);
        supprimer.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v){
                if(adapter.getCount()>0) {


                    Lieu lieu = adapter.getItem(0);
                    dataSource.deleteLieu(lieu);
                    adapter.remove(lieu);
                }
            }
        });
        //nameEdit.addTextChangedListener(textWatcher);

    }


    private View.OnClickListener ajouterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            name = nameEdit.getText().toString();
            major = majorEdit.getText().toString();
            minor = minorEdit.getText().toString();
            type = String.valueOf(typeSpinner.getSelectedItem());

            ArrayAdapter<Lieu> adapter = (ArrayAdapter<Lieu>) getListAdapter();
            Lieu lieu;
            lieu = dataSource.createLieu(name, major, minor, type);
            adapter.add(lieu);
            adapter.notifyDataSetChanged();
        }
    };
    /*private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            result.setText(defaut);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ajouter_lieu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
