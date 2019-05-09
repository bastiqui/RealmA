package com.example.realm1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class ModifyPerson extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_persona);

        final EditText dni = findViewById(R.id.modifyDni);
        final EditText name = findViewById(R.id.modifyName);
        final EditText surname = findViewById(R.id.modifySurname);
        final EditText age = findViewById(R.id.modifyAge);
        RadioButton m = findViewById(R.id.radioButtonM);
        RadioButton f = findViewById(R.id.radioButtonF);

        if (getIntent().getStringExtra("gender").equals("M")) {
            m.setChecked(true);
        } else {
            f.setChecked(true);
        }

        Integer getEdat = getIntent().getIntExtra("age",0);
        final String getDni = getIntent().getStringExtra("dni");

        dni.setText(getDni);
        name.setText(getIntent().getStringExtra("name"));
        surname.setText(getIntent().getStringExtra("surname"));
        age.setText(String.valueOf(getEdat));

        findViewById(R.id.modPerson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dni.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Falta el DNI",Toast.LENGTH_SHORT).show();
                } else {
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            RadioGroup radioButtonGroup = findViewById(R.id.radioModPerson);
                            int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
                            View radioButton = radioButtonGroup.findViewById(radioButtonID);
                            int idx = radioButtonGroup.indexOfChild(radioButton);
                            RadioButton r = (RadioButton) radioButtonGroup.getChildAt(idx);
                            String gender = r.getText().toString();

                            RealmResults<Persona> personas = realm.where(Persona.class).equalTo("dni", getDni).findAll();
                            personas.setValue("name", name.getText().toString());
                            personas.setValue("surname", surname.getText().toString());
                            personas.setValue("age", Integer.parseInt(age.getText().toString()));
                            personas.setValue("gender", gender);
                        }
                    });
                    finish();
                }
            }
        });
    }
}