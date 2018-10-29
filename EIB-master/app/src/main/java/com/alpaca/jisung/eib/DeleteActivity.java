package com.alpaca.jisung.eib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


public class DeleteActivity extends AppCompatActivity {

    Button delete;
    Button mainBtn,searchBtn;

    Intent intent;
    String[] name;

    ListView listView;
    ListAdapter adapter;

    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_delete);

        init();

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "ItemBook1.db", null, 1);

        name = dbHelper.getResult();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, name);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);

        arrayList = new ArrayList<>(Arrays.asList(name));
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, R.id.itemName, arrayList);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SparseBooleanArray checkedItems = listView.getCheckedItemPositions();

                int count = arrayAdapter.getCount() ;
                for (int i = count-1; i >= 0; i--) {
                    if (checkedItems.get(i)) {
                        arrayList.remove(i);
                        dbHelper.delete(arrayList.get(i));
                        }
                } // 모든 선택 상태 초기화.

                listView.clearChoices() ;
                arrayAdapter.notifyDataSetChanged();

                intent = new Intent(getApplicationContext(),MainActivity.class); // 다음 넘어갈 클래스 지정
                startActivity(intent);

            }
        });


        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class); // 다음 넘어갈 클래스 지정
                startActivity(intent);

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class); // 다음 넘어갈 클래스 지정
                startActivity(intent);

            }
        });
    }

    public void init( ){
        mainBtn = (Button)findViewById(R.id.mainBtn);
        searchBtn = (Button)findViewById(R.id.searchBtn);
        delete = (Button)findViewById(R.id.delete);//id 값 찾기 (xml)

    }

}


