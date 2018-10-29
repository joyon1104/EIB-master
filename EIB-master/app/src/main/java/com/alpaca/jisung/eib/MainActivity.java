package com.alpaca.jisung.eib;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView nameText;
    Button settingBtn,searchBtn,insertBtn,deleteBtn,bluetoothBtn;

    double lat,lng;

    Intent intent;

    String[] name;

    ListAdapter adapter;
    ListView listView;

    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();//초기화


        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {//찾기버튼 클릭시 이벤트
            @Override
            public void onClick(View v) {
                getLocationData();//위치를 찾은 후

                intent = new Intent(MainActivity.this, MapsActivity.class);

                intent.putExtra("Lat", lat + 0.0000001);
                intent.putExtra("Lng", lng + 0.0000001);//값을 넘겨

                startActivity(intent);//엑티비티 실행
            }
        });

        bluetoothBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,com.bluetooth.MainActivity.class);
                startActivity(MainActivity.this.intent);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, DeleteActivity.class);
                startActivity(intent);
            }
        });


        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(intent);
            }
        });

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "ItemBook1.db", null, 1);

        name = dbHelper.getResult();

        adapter = new Item(this, name);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        arrayList = new ArrayList<>(Arrays.asList(name));
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.image_layout, R.id.itemName, arrayList);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showInputBox(arrayList.get(position), position);
                return false;
            }
        });

    }

    public void showInputBox(final String oldItem, final int index){
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "ItemBook1.db", null, 1);
        final Dialog dialog=new Dialog(MainActivity.this);
        dialog.setTitle("Input Box");
        dialog.setContentView(R.layout.editbox);
        TextView txtMessage=(TextView)dialog.findViewById(R.id.txtmsg);
        txtMessage.setText("수정하시겠습니까?");
        final EditText editText=(EditText)dialog.findViewById(R.id.txtinput);
        editText.setText(oldItem);
        final TextView textView=(TextView)dialog.findViewById(R.id.txttag);
        textView.setText(dbHelper.selectData(oldItem));
        Button bt = (Button)dialog.findViewById(R.id.save);
        Button delbt = (Button)dialog.findViewById(R.id.delete);

        delbt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dbHelper.delete(oldItem);
                Toast.makeText(getApplicationContext(), "데이터 삭제",
                        Toast.LENGTH_SHORT).show();
                editText.setText(null);
                name = dbHelper.getResult();
                adapter = new Item(MainActivity.this, name);
                listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(adapter);
                arrayList = new ArrayList<>(Arrays.asList(name));
                arrayAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.update(oldItem,editText.getText().toString());
                Toast.makeText(getApplicationContext(), "데이터 수정",
                        Toast.LENGTH_SHORT).show();
                editText.setText(null);
                name = dbHelper.getResult();
                adapter = new Item(MainActivity.this, name);
                listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(adapter);
                arrayList = new ArrayList<>(Arrays.asList(name));
                arrayAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();

    }



    public void init( ){
        nameText = (TextView)findViewById(R.id.nameText);
        settingBtn = (Button)findViewById(R.id.settingBtn);
        searchBtn = (Button)findViewById(R.id.searchBtn);
        bluetoothBtn = (Button)findViewById(R.id.bluetoothBtn);
        insertBtn = (Button)findViewById(R.id.insertBtn);//id 값 찾기 (xml)
        deleteBtn = (Button)findViewById(R.id.deleteBtn);
        nameText.setText(getIntent().getStringExtra("name"));//로그인에서 이름 정보가져와서 텍스트로 설정

    }


    public void getLocationData(){
        lat = 37.555744;
        lng = 126.970431;// 임시 설정값  gps 위치 값 넣을 것
    }//기기 위치 정보 찾는 함수

}
