package com.example.nonakanaoki.androidserial;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.io.IOException;

/**
 * Created by nonakanaoki on 15/04/26.
 */
public abstract class Receive {
    UsbManager manager;
    UsbSerialDriver usb;
    String data;
    //String str;

    public void start_read_thread(Context context){
        manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        usb = UsbSerialProber.acquire(manager);
        if (usb != null) {
            try{
                usb.open();
                usb.setBaudRate(38400);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

        new Thread(new Runnable(){
            public void run(){
                try{
                    while(true){
                        byte buf[] = new byte[256];
                        int num = usb.read(buf, buf.length);
                        String analogdata, id;
                        int lqi;
                        Log.v("num", String.valueOf(num));
                        if(num == 51){ //sizeが５１なら正常なデータ
                            //onMessage(new String(buf, 0, num));
                            data = new String(buf, 0, num);
                            id =data.substring(11,19);
                            lqi = Integer.parseInt(data.substring(9,11),16);

                            analogdata = data.substring(num-14,num-12);
                            Log.v("analog", analogdata);
                            Log.v("id", id);
                            Log.v("LQI",data.substring(9,11));
                            Log.v("ToCoStick", new String(buf, 0, num)); // Arduinoから受信した値をlogcat出力

                            if(analogdata.equals("00") == false){
                                onMessage2(id);
                                onMessage("Enter");
                            }else {
                                onMessage("");
                                onMessage2("");
                                onMessage3(String.valueOf(lqi));
                            }

//                                onMessage(String.valueOf(lqi));
//                                onMessage2(data);


                        }


//                         ///////////////////KAWASE!!!!!!!!!!!!
//                            //onMessage(new String(buf, 0, num));
//                            data = new String(buf, 0, num-1);
//                        float cel = Float.parseFloat(data);
////                            id = data.substring(11, 19);
////                            lqi = Integer.parseInt(data.substring(9, 11), 16);
//
////                            analogdata = data.substring(num - 14, num - 12);
////                            Log.v("analog", analogdata);
////                            Log.v("id", id);
////                            Log.v("LQI", data.substring(9, 11));
//                            Log.v("KAWASE", data); // Arduinoから受信した値をlogcat出力
//
//
//                                onMessage((cel-30)/2);
//                                onMessage2("");
////                                onMessage3(String.valueOf(lqi));
//
//
////                                onMessage(String.valueOf(lqi));
////                                onMessage2(data);



                        Thread.sleep(10);
                    }
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    abstract void onMessage(final String str);
    abstract void onMessage2(final String str);
    abstract void onMessage3(final String str);

}
