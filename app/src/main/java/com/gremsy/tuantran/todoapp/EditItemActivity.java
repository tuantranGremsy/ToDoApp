package com.gremsy.tuantran.todoapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;


public class EditItemActivity extends AppCompatActivity {
    EditText editView;
    Button saveBtn;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editView = (EditText) findViewById(R.id.editView);
        editView.requestFocus();
        editView.setFocusableInTouchMode(true);
        editView.setFocusable(true);
        editView.setText(getIntent().getStringExtra("ItemName"));
        editView.setSelection(editView.length());
        editView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        position = getIntent().getIntExtra("ItemPosition",0);
        Log.d("position", ""+position);

        // Save button
        saveBtn = (Button)findViewById(R.id.saveButton);


    }

    public void saveItem(View view) {
        Intent returnData = new Intent();
        returnData.putExtra("Item",editView.getText().toString());
        returnData.putExtra("ItemPosition",position);
        setResult(RESULT_OK, returnData);
        finish();
    }
}
