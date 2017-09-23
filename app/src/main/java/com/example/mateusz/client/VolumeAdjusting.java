package com.example.mateusz.client;


import android.app.Activity;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class VolumeAdjusting extends Activity implements View.OnClickListener {

    private final static String increaseVolume="UP";
    private final static String decreaseVolume="MIN";
    private int changedVolumeSpeed;
    private String intValueOfChangedSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volume_adjusting);
        Button wifi = (Button) findViewById(R.id.wifiStatus);
        Button volumePLUS = (Button) findViewById(R.id.volumePLUS);
        Button volumeMIN = (Button) findViewById(R.id.volumeMIN);
        Button returnToPreviousMenu = (Button)findViewById(R.id.returnToPreviousMenu);
        Button increaseVolumeOfVolumeChanging = (Button) findViewById(R.id.increaseSpeedOfVolumeChanging);
        Button decreaseVolumeOfVolumeChanging = (Button) findViewById(R.id.decreaseSpeedOfVolumeChanging);

        returnToPreviousMenu.setOnClickListener(this);
        wifi.setOnClickListener(this);

        volumePLUS.setOnClickListener(this);
        volumeMIN.setOnClickListener(this);
        increaseVolumeOfVolumeChanging.setOnClickListener(this);
        decreaseVolumeOfVolumeChanging.setOnClickListener(this);

        TelephonyManager tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tManager.listen(new PhoneStateManager(this), PhoneStateListener.LISTEN_CALL_STATE);  //wymagana konstrukcja, nie dodawalem dodatkowych "listenerow", bo po co ;)

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.wifiStatus:
                NetworkInfo wifiInfo = NetworkingInformation.createWifiManager(VolumeAdjusting.this);
                NetworkingInformation.checkIfWifiConnectionIsEstabilished(wifiInfo,VolumeAdjusting.this);

                NetworkInfo mobileConnectionInfo = NetworkingInformation.createMobileConnectionManager(VolumeAdjusting.this);
                NetworkingInformation.checkIfMobileConnectionIsEstabilished(mobileConnectionInfo,VolumeAdjusting.this);
                break;

            case R.id.volumePLUS:
                createSocketWithGivenMessage(increaseVolume);
                break;

            case R.id.volumeMIN:
                createSocketWithGivenMessage(decreaseVolume);
                break;

            case R.id.returnToPreviousMenu:
                Intent previousMenu = new Intent(this, MainMenu.class);
                startActivity(previousMenu);
                break;

            case R.id.increaseSpeedOfVolumeChanging:
                changedVolumeSpeed=changedVolumeSpeed+1;
                Toast.makeText(VolumeAdjusting.this, "Current speed value: " + changedVolumeSpeed,
                        Toast.LENGTH_LONG).show();
                createSocketWithGivenMessage(changedVolumeSpeed+"");
                break;

            case R.id.decreaseSpeedOfVolumeChanging:
                if(changedVolumeSpeed>0)
                {
                    changedVolumeSpeed = changedVolumeSpeed - 1;
                    Toast.makeText(VolumeAdjusting.this, "Current speed value: " + changedVolumeSpeed,
                            Toast.LENGTH_LONG).show();


                    createSocketWithGivenMessage(changedVolumeSpeed + "");
                }
                else
                {
                    Toast.makeText(VolumeAdjusting.this, "Current speed value: " + changedVolumeSpeed,
                            Toast.LENGTH_LONG).show();
                }

                break;

            default:
                break;

        }
    }

    public static void createSocketWithGivenMessage(final String message)
    {
        System.out.println("dupa");
        new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    Socket client = new Socket(MainMenu.getIpAdress(), MainMenu.getPort());


                        PrintWriter printwriter = new PrintWriter(client.getOutputStream(), true);
                        printwriter.write(message);
                        printwriter.flush();
                        printwriter.close();
                        client.close();



                } catch (UnknownHostException e) {

                                        //JAK POLACZYC THREAD Z MOZLIWOSCIA OTWARCIA AKTYWNOSCI MAINMENU ZA POMOCA INTENT
                    //DOPISAC SENSOWNY KOD -> WYSWIETLENIE INFO, ZE USER COS NAMOTAL W WPISYWANIU HOSTA

                } catch (IOException e) {


                }
            }
        }).start();

    }



}