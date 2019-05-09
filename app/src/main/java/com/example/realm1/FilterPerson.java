package com.example.realm1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;

public class FilterPerson extends AppCompatActivity implements RealmModel {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_person);

        findViewById(R.id.buscar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                RealmResults<Persona> people;

                ListView data = findViewById(R.id.person_list);
                RadioButton f = findViewById(R.id.radioButtonF);

                EditText desdeEditText = findViewById(R.id.ageFrom);
                EditText hastaEditText = findViewById(R.id.ageTo);

                String desde = desdeEditText.getText().toString();
                String hasta = hastaEditText.getText().toString();

                if (f.isChecked()) {
                    if (!desde.isEmpty() && hasta.isEmpty()) {
                        people = realm.where(Persona.class).equalTo("gender", "F").greaterThanOrEqualTo("age", Integer.parseInt(desde)).lessThanOrEqualTo("age", 99).findAll();
                    } else if (desde.isEmpty() && !hasta.isEmpty()) {
                        people = realm.where(Persona.class).equalTo("gender", "F").greaterThanOrEqualTo("age", 0).lessThanOrEqualTo("age", Integer.parseInt(hasta)).findAll();
                    } else if (!desde.isEmpty() && !hasta.isEmpty()) {
                        people = realm.where(Persona.class).equalTo("gender", "F").greaterThanOrEqualTo("age", Integer.parseInt(desde)).lessThanOrEqualTo("age", Integer.parseInt(hasta)).findAll();
                    } else {
                        people = realm.where(Persona.class).equalTo("gender", "F").findAll();
                    }
                } else {
                    if (!desde.isEmpty() && hasta.isEmpty()) {
                        people = realm.where(Persona.class).equalTo("gender", "M").greaterThanOrEqualTo("age", Integer.parseInt(desde)).lessThanOrEqualTo("age", 99).findAll();
                    } else if (desde.isEmpty() && !hasta.isEmpty()) {
                        people = realm.where(Persona.class).equalTo("gender", "M").greaterThanOrEqualTo("age", 0).lessThanOrEqualTo("age", Integer.parseInt(hasta)).findAll();
                    } else if (!desde.isEmpty() && !hasta.isEmpty()) {
                        people = realm.where(Persona.class).equalTo("gender", "M").greaterThanOrEqualTo("age", Integer.parseInt(desde)).lessThanOrEqualTo("age", Integer.parseInt(hasta)).findAll();
                    } else {
                        people = realm.where(Persona.class).equalTo("gender", "M").findAll();
                    }
                }

                ListPersonaAdapter personaAdapter = new ListPersonaAdapter(people);

                data.setAdapter(personaAdapter);

                personaAdapter.notifyDataSetChanged();
            }
        });
    }

}