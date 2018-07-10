package jp.ac.doshisha.mikilab.testappksato;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Light> lights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ネットワーク設定
        SocketClient.setEndpoint(new InetSocketAddress("172.20.11.58",44344));

        // lightの情報をサーバから持ってくる
        new Thread(new Runnable() {
            @Override
            public void run() {
                lights = SocketClient.getLights();
            }
        }).start();
    }

    public void onButtonClick(View v){
        final ArrayList<Light> myLights = new ArrayList<>();
        for(int i=12; i<=17; i++){
            myLights.add(lights.get(i));
        }

        switch (v.getId()){
            case R.id.button_on:
                for(int i=0; i<myLights.size(); i++){
                    Light tmp_light;
                    tmp_light = myLights.get(i);
                    tmp_light.setLumPct(10);
                    tmp_light.setTemperature(4500);
                    // on_myLights.get(i).setLumPct(100.0);
                    // 拡張for文を使ってもいい
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SocketClient.dimByLights(myLights);
                    }
                }).start();
                break;

            case R.id.button_off:
                for(int i=0; i<myLights.size(); i++){
                    Light tmp_light;
                    tmp_light = myLights.get(i);
                    tmp_light.setLumPct(0);
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SocketClient.dimByLights(myLights);
                    }
                }).start();
                break;
        }
    }
}

// 12~17