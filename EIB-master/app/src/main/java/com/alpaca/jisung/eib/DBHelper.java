package com.alpaca.jisung.eib;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gayoung on 2018. 4. 8..
 */

public class DBHelper extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        db.execSQL("CREATE TABLE ItemBook1 (_id INTEGER PRIMARY KEY AUTOINCREMENT, itemName TEXT not null, itemMemo TEXT not null, itemPlace TEXT);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String itemName, String itemMemo, String itemPlace) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO ItemBook1 VALUES(null, '" + itemName + "', '" + itemMemo +"', '"+ itemPlace +"');");
        //db.execSQL("INSERT INTO ItemBook1 VALUES(null, '" + itemName + "', '" + itemMemo +"');");
        db.close();
    }

    public void update(String oldItem,String itemName) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE ItemBook1 SET itemName= '" + itemName + "' WHERE itemName='" + oldItem + "';");
        db.close();
    }

    public void delete(String item) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM ItemBook1 WHERE itemName='" + item + "';");
        db.close();
    }

    public String selectData(String olditem){
        SQLiteDatabase db = getReadableDatabase();
        String itemMemo = null;
        String sql = "select * from ItemBook1 where itemName = '"+olditem+"';";
        Cursor cursor = db.rawQuery(sql, null);

        // result(Cursor 객체)가 비어 있으면 false 리턴
        if(cursor.moveToFirst()){
            itemMemo = cursor.getString(2);
        }
        return itemMemo;
    }

    public String[] getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM ItemBook1", null);
        int size = cursor.getCount();

        String[] resultArray = new String[size];
        cursor.moveToFirst();
        int i = 0;

        while (cursor.moveToNext()) {
            resultArray[i] = cursor.getString(1);
            i++;
        }
        return resultArray;
    }
}



