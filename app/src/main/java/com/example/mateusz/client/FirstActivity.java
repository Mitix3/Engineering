package com.example.mateusz.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

public  class FirstActivity extends Activity {

    private static final int STOPSPLASH = 0;
    //time in milliseconds
    private static final long SPLASHTIME = 3000;
    private Intent openMainMenu;

    private ImageView splash;

    //handler for splash screen
    private Handler splashHandler = new Handler() {
        /* (non-Javadoc)
         * @see android.os.Handler#handleMessage(android.os.Message)
         */
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOPSPLASH:
                    //rmy message is defined only to open new activity delayed by SPLASHTIME variable
                    splash.setVisibility(View.GONE);
                    startActivity(openMainMenu);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.first_activity);
        openMainMenu = new Intent(this,MainMenu.class);
        splash = (ImageView) findViewById(R.id.splashscreen);
        Message msg = new Message();
        msg.what = STOPSPLASH;
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);



    }
}