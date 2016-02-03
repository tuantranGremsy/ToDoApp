package com.gremsy.tuantran.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> todoItems;
    ArrayAdapter<String> todoAdapter;
    ListView lvItems;
    EditText etEditText;
    private final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(todoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                todoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent editIntent = new Intent(MainActivity.this,EditItemActivity.class);
                editIntent.putExtra("ItemName", (String) lvItems.getItemAtPosition(position));
                editIntent.putExtra("ItemPosition", position);
                startActivityForResult(editIntent,REQUEST_CODE);
            }
        });


    }

    public void populateArrayItems() {
        readItems();
        todoAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, todoItems);
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try{
            FileUtils.writeLines(file, todoItems);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAddItem(View view) {
        if(etEditText.getText().toString().trim().equals("")) {
            Toast toast = Toast.makeText(this, "Item can't be empty", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }else{
            todoItems.add(etEditText.getText().toString());
            etEditText.setText("");
            writeItems();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            todoItems.set(data.getIntExtra("ItemPosition",0), data.getStringExtra("Item"));
            todoAdapter.notifyDataSetChanged();
            writeItems();
        }
    }
}
