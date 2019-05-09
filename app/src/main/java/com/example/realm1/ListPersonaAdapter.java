package com.example.realm1;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;


import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class ListPersonaAdapter extends RealmBaseAdapter<Persona> implements ListAdapter {

    ListPersonaAdapter(@Nullable OrderedRealmCollection<Persona> data) {
        super(data);
    }

    private static class ViewHolder{
        TextView dni,name,surname,gender,age;
        ImageButton btn_delete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.dni = convertView.findViewById(R.id.dni);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.surname = convertView.findViewById(R.id.surname);
            viewHolder.age = convertView.findViewById(R.id.age);
            viewHolder.gender = convertView.findViewById(R.id.gender);
            viewHolder.btn_delete = convertView.findViewById(R.id.btn_delete);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Persona item = adapterData.get(position);
        viewHolder.dni.setText(item.getDni());
        viewHolder.name.setText(item.getName());
        viewHolder.surname.setText(item.getSurname());
        viewHolder.age.setText(String.valueOf(item.getAge()));
        viewHolder.gender.setText(item.getGender());

        viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(item);
            }
        });

        final View finalConvertView = convertView;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(finalConvertView.getContext(), ModifyPerson.class);
                intent.putExtra("dni", item.getDni());
                intent.putExtra("name", item.getName());
                intent.putExtra("surname", item.getSurname());
                intent.putExtra("age", item.getAge());
                intent.putExtra("gender", item.getGender());
                finalConvertView.getContext().startActivity(intent);
            }
        });
        return convertView;
    }


    private void delete(Persona item){
        Realm realm = io.realm.Realm.getDefaultInstance();
        final RealmResults<Persona> results = realm.where(Persona.class).equalTo("dni", item.getDni()).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
        notifyDataSetChanged();
    }
}
