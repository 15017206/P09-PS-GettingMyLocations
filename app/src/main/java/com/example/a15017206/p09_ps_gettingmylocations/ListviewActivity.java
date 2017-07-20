package com.example.a15017206.p09_ps_gettingmylocations;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ListviewActivity extends AppCompatActivity {

    String folderLocation;
    String TAG = "ListviewActivity>>";
    ListView lv1;
    Button btnRefresh;
    TextView tv1;
    ArrayList<String> data3 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        lv1 = (ListView) findViewById(R.id.lv2);
        btnRefresh = (Button) findViewById(R.id.button4);
        tv1 = (TextView) findViewById(R.id.textView);

        Intent i = getIntent();
        data3 = i.getStringArrayListExtra("");

        Log.i(TAG, "onCreate: " + data3);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, data3);
        lv1.setAdapter(adapter);
        tv1.setText("No. of records: "+data3.size());

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/P09";
                File targetFile = new File(folderLocation, "data.txt");

                if (targetFile.exists() == true) {
                    String data = "";
//                    ArrayList<String> data2 = new ArrayList<String>();

                    try {
                        FileReader reader = new FileReader(targetFile);
                        BufferedReader br = new BufferedReader(reader);
                        String line = br.readLine();
                        data3.clear();
                        while (line != null) {
                            data += line + "\n";
//                            data2.add(line+"");
//                            data3.clear();
                            data3.add(line);
                            line = br.readLine();
                            tv1.setText("No. of records: "+data3.size());
                            adapter.notifyDataSetChanged();
                        }
                        br.close();
                        reader.close();

                    } catch (Exception e) {
                        Toast.makeText(ListviewActivity.this, "Failed to read!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
