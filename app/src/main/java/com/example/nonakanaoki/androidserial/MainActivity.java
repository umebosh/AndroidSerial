package com.example.nonakanaoki.androidserial;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import android.hardware.usb.UsbManager;
import android.content.*;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;

import java.io.IOException;

import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class MainActivity extends ActionBarActivity {
    private final String TAG = "MainActivity";
    TextView text,text2,text3;


    Handler handler = new Handler();
    Handler handler2 = new Handler();
    Handler handler3 = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        text2 = (TextView)findViewById(R.id.tocosid);
        text3 = (TextView)findViewById(R.id.lqi);

        Receive rcv = new Receive() {
            @Override
            void onMessage(final String str) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        text.setTextColor(Color.BLUE);
                        text.setText(str);
                    }
                });

            }

            @Override
            void onMessage2(final String str) {
                handler2.post(new Runnable() {
                    @Override
                    public void run() {
                        text2.setText(str);
                    }
                });

            }

            @Override
            void onMessage3(final String str) {
                handler3.post(new Runnable() {
                    @Override
                    public void run() {
                        text3.setText(str);
                    }
                });
            }
        };
        rcv.start_read_thread(this);





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
