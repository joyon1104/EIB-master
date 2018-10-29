package com.alpaca.jisung.eib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class InsertActivity extends AppCompatActivity {

    Button insert, register;
    Button mainBtn,searchBtn;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        init();

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "ItemBook1.db", null, 1);
        final EditText etitemName = (EditText) findViewById(R.id.itemName);
        final EditText etitemMemo = (EditText) findViewById(R.id.itemMemo);


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

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = etitemName.getText().toString();
                String itemMemo = etitemMemo.getText().toString();
                String itemPlace = "";

                //입력값이 누락된게 없는지 확인
                if (itemName.length() == 0) {
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //DB 삽입 -> 결과 출력 -> 입력필드 초기화
                    dbHelper.insert(itemName, itemMemo,itemPlace);
                    Toast.makeText(getApplicationContext(), "데이터 생성",
                            Toast.LENGTH_SHORT).show();
                    etitemName.setText(null);
                    etitemMemo.setText(null);
                }
            }
        });

            register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(InsertActivity.this, RegisterActivity.class);
                startActivity(intent);
                }
            });
    }


    public void init( ){
        mainBtn = (Button)findViewById(R.id.mainBtn);
        searchBtn = (Button)findViewById(R.id.searchBtn);
        insert= (Button) findViewById(R.id.insert);
        register = (Button) findViewById(R.id.register);
    }

}


