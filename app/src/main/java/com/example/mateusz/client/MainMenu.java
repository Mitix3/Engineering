package com.example.mateusz.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

/**
 * Created by Mateusz on 07/12/2016.
 */

public class MainMenu extends Activity implements View.OnClickListener{

    private EditText writeIpAddress;
    private EditText writePort;
    private static String ipAddressFromEditText="";
    private static String portFromEditText="";
    private static final Pattern ipAddressMatcher = Pattern.compile(
            "(([0-1]?[0-9]{1,2}\\.)|(2[0-4][0-9]\\.)|(25[0-5]\\.)){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))");
    private File path;
    private File fileToReadSave;
    private FileInputStream in;
    private byte bytes[];
    private String ipPartOfString;
    private String portPartOfString;

    public String getIpPartOfString() {
        return ipPartOfString;
    }

    public String getPortPartOfString() {
        return portPartOfString;
    }

    public void setIpPartOfString(String ipPartOfString) {
        this.ipPartOfString = ipPartOfString;
    }

    public void setPortPartOfString(String portPartOfString) {
        this.portPartOfString = portPartOfString;
    }


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        ImageButton startConnection = (ImageButton)findViewById(R.id.startConnection);
            startConnection.setOnClickListener(this);



        writeIpAddress = (EditText)findViewById(R.id.ipAdress);
        writePort = (EditText)findViewById(R.id.port);

        Button getDataFromFile = (Button)findViewById(R.id.manageDataFromFile);
            getDataFromFile.setOnClickListener(this);

        Intent returnFromNextActivity = getIntent();

        path = this.getFilesDir();



        }


    //android:background="@drawable/backgroundOfMainActivity"

public static Pattern getIpAddressMatcher(){
    return ipAddressMatcher;
}
public static String getIpAdress()
    {
        return ipAddressFromEditText;
    }
public static int getPort()
    {
        return Integer.parseInt(portFromEditText);
    }

        @Override
public void onClick(View v) {

        switch (v.getId()) {
            case R.id.startConnection:
              //  getDisplaysPixels();
                createFileIfNeeded();

                getTextFromBothTextFields();


                if(fieldsAreCorrect())
                {
                    openVolumeAdjustingActivity();

                }
                else
                {
                    Toast.makeText(MainMenu.this, "Check Port (example: 8888) and Ip Address fields (example: 192.168.1.1)",
                            Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.manageDataFromFile:

                createFileIfNeeded();

                readDataFromFile();

                String textFromFile = new String(bytes);

                divideTextFromFileIntoIpAndPortParts(textFromFile);

                writeStringsToBothTextFields();

            default:
                break;

               }
         }
public void getTextFromBothTextFields()
{
    String tempTextFromIpEditText =  writeIpAddress.getText().toString();
    String tempTextFromPortEditText = writePort.getText().toString();
    String concatenateAbove = tempTextFromIpEditText + " " + tempTextFromPortEditText;

    try {
        PrintWriter pw = new PrintWriter(fileToReadSave);
        pw.close();
        FileOutputStream stream = new FileOutputStream(fileToReadSave);
        stream.write(concatenateAbove.getBytes());
    } catch(IOException e)
    {
        Toast.makeText(MainMenu.this, "Error writing data to file, try again...",
                Toast.LENGTH_LONG).show();
    }

}

public void writeStringsToBothTextFields()
{
        writePort.setText("");
        writePort.setText(portPartOfString);
        writeIpAddress.setText("");
        writeIpAddress.setText(ipPartOfString);
    }


public void divideTextFromFileIntoIpAndPortParts(String textFromFile)
{

        for(int i=0;i<textFromFile.length();i++)
        {
            if(textFromFile.charAt(i) == ' ') {
                ipPartOfString = textFromFile.substring(0, i);
                portPartOfString = textFromFile.substring(i+1, textFromFile.length());
                break;
            }

        }
        if(!textFromFile.contains(" "))
        {
            throw new RuntimeException("File's content is formatted in weird way...");
        }

}
public void readDataFromFile()
{
    int length = (int) fileToReadSave.length();

    bytes = new byte[length];

    try {
        in = new FileInputStream(fileToReadSave);

        in.read(bytes);
    } catch(IOException e) {

    }


    finally{
        try{
            in.close();
        }
        catch(IOException e1)
        {
            Toast.makeText(MainMenu.this, "Error while closing FileInputStream",
                    Toast.LENGTH_LONG).show();
        }
    }
}
public void createFileIfNeeded()
{
    if(fileToReadSave==null || !fileToReadSave.exists()) {
        fileToReadSave = new File(path, "IpPort.txt");

    }


    }
public boolean fieldsAreCorrect()
{
        ipAddressFromEditText = writeIpAddress.getText().toString();
        portFromEditText = writePort.getText().toString();



        if(portIsNumber(portFromEditText) &&
           ipAddressIsWrittenProperly(ipAddressFromEditText))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

public void openVolumeAdjustingActivity()
{
        Intent SkipToNextActivity = new Intent(this, VolumeAdjusting.class);
        startActivity(SkipToNextActivity);
    }
public boolean portIsNumber(String port)
{
    int digitCounter=0;

    for(int i=0;i<port.length();i++)
    {
        if(Character.isDigit(port.charAt(i)))
        {
            digitCounter++;
        }

    }

    return digitCounter == port.length();
}

public boolean ipAddressIsWrittenProperly(final String ip)
{
        return ipAddressMatcher.matcher(ip).matches();
    }

public void getDisplaysPixels()
{
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        System.out.println(width+"  " + height);

    }


}




