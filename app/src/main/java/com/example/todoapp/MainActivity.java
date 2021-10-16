package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button buttonAdd;
    EditText editText2;
    RecyclerView rvItemList;
    ItemsAdapter itemsAdapter;
    ArrayList items;

    public MainActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdd = findViewById(R.id.buttonAdd);
        editText2 = findViewById(R.id.editText2);
        rvItemList = findViewById(R.id.rvItemList);

        //This load item calls the method and we no longer need a hand written list
        loadItems();
//        items.add("Buy Milk");
//        items.add("Buy egg");
//        items.add("Buy Water");



        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                //Delete item from the model
                items.remove(position);
                //notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                savedItems();
                Toast.makeText(getApplicationContext(), "Items was removed", Toast.LENGTH_SHORT).show();
            }
        };
        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        rvItemList.setAdapter(itemsAdapter);
        rvItemList.setLayoutManager(new LinearLayoutManager(this));

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = editText2.getText().toString();
                //add item to model
                items.add(todoItem);
                //notify adapter an item has been inserted
                itemsAdapter.notifyItemInserted(items.size()-1);
                //clear edit text once submit
                editText2.setText("");
                Toast.makeText(getApplicationContext(), "Items was added", Toast.LENGTH_SHORT).show();
                savedItems();
            }
        });

    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }
    //This function will load item by reading every lines of the data file
    private void loadItems(){
        try {
            items = new ArrayList<String>(FileUtils.readLines(getDataFile(),Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("Main Activity", "Error reading items", e);
            items = new ArrayList<>();
        }

    }
    //This function saves item by writing them in the data file
    private void savedItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("Main Activity", "Error writing items", e);
        }
    }
}