package com.example.realm1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import io.realm.Realm;

public class NewPerson extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_person);

        findViewById(R.id.addPerson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText dni = findViewById(R.id.newDni);
                EditText name = findViewById(R.id.newName);
                EditText surname = findViewById(R.id.newSurname);
                EditText age = findViewById(R.id.newAge);

                RadioGroup radioButtonGroup = findViewById(R.id.radioNewPerson);
                int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
                View radioButton = radioButtonGroup.findViewById(radioButtonID);
                int idx = radioButtonGroup.indexOfChild(radioButton);
                RadioButton r = (RadioButton) radioButtonGroup.getChildAt(idx);
                String gender = r.getText().toString();

                if (dni.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Falta el DNI",Toast.LENGTH_SHORT).show();
                } else {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    Persona persona = realm.createObject(Persona.class, dni.getText().toString());
                    persona.setName(name.getText().toString());
                    persona.setSurname(surname.getText().toString());
                    persona.setAge(Integer.parseInt(age.getText().toString()));
                    persona.setGender(gender);
                    realm.commitTransaction();
                    finish();
                }
            }
        });
    }
}
