package com.example.asafkorem.async;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AsynctaskActivity extends AppCompatActivity {

    Button start;
    Button cancel;
    Button create;
    TextView text;
    Counter counter;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);

        text = (TextView) findViewById(R.id.textView);
        create = (Button) findViewById(R.id.create);
        start = (Button) findViewById(R.id.start);
        cancel = (Button) findViewById(R.id.cancel);
        start.setClickable(false);
        cancel.setClickable(false);

        text.setText("Task Waiting for Create");

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTask();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTask();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTask();
            }
        });


    }

    @SuppressLint("SetTextI18n")
    protected void createTask() {
        create.setClickable(false);
        start.setClickable(true);
        cancel.setClickable(false);
        toastIt("Creating task");

        text.setText("Waiting for Start");
        counter = new Counter();
    }

    protected void startTask() {
        create.setClickable(false);
        start.setClickable(false);
        cancel.setClickable(true);
        toastIt("Starting task");

        counter.execute();
    }

    @SuppressLint("SetTextI18n")
    protected void cancelTask() {
        create.setClickable(true);
        start.setClickable(false);
        cancel.setClickable(false);
        toastIt("Canceling task");

        text.setText("Task Waiting for Create");
        counter.cancel(true);
    }

    protected void toastIt(String toToast)
    {
        Toast.makeText(AsynctaskActivity.this, toToast, Toast.LENGTH_SHORT).show();
    }

    private class Counter extends AsyncTask<String, String, String> {
        private String msg;
        @Override
        protected String doInBackground(String ...strings) {
            int count = 10;
            int start = 1;
            for(int i = start; i <= count; i ++)
            {
                try {
                    msg = Integer.toString(i);
                    publishProgress(msg);
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                    return e.getMessage();
                }
                if(isCancelled()) break;
            }
            return msg;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            toastIt("DONE!");
            text.setText("Done Task!");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            create.setClickable(true);
            start.setClickable(false);
            cancel.setClickable(false);

            text.setText("Task Waiting for Create");
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPreExecute() {
            text.setText("Start Count");
        }

        @Override
        protected void onProgressUpdate(String... values) {
            text.setText(values[0]);
        }
    }
}
