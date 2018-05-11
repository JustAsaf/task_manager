package com.example.asafkorem.async;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ThreadActivity extends AppCompatActivity {

    Button start;
    Button cancel;
    Button create;
    TextView text;
    Thread runner;
    Handler handler;
    boolean is_canceled;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);

        is_canceled = false;
        handler = new Handler();
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
        is_canceled = false;
        runner = new Thread(new Counter());
    }

    protected void startTask() {
        create.setClickable(false);
        start.setClickable(false);
        cancel.setClickable(true);
        toastIt("Starting task");

        runner.start();
    }

    @SuppressLint("SetTextI18n")
    protected void cancelTask() {
        create.setClickable(true);
        start.setClickable(false);
        cancel.setClickable(false);
        toastIt("Canceling task");

        text.setText("Task Waiting for Create");
        is_canceled = true;
    }

    protected void toastIt(String toToast)
    {
        Toast.makeText(ThreadActivity.this, toToast, Toast.LENGTH_SHORT).show();
    }

    private class Counter implements Runnable{

        @Override
        public void run() {
            Looper.prepare();
            for(int i = 1; i <= 10; i++)
            {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(is_canceled)
                {
                    break;
                }
                final int finalI = i;
                handler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        text.setText(Integer.toString(finalI));
                    }
                });
            }
            if(!is_canceled)
            {
                onPostExecute();
            }
            is_canceled = false;
        }

        @SuppressLint("SetTextI18n")
        void onPostExecute() {
            toastIt("DONE!");
            text.setText("Done Task!");

            create.setClickable(true);
            start.setClickable(false);
            cancel.setClickable(false);
        }

    }
}
