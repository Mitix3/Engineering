package com.example.mateusz.client;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneStateManager extends PhoneStateListener {

    //private static final String TAG = "PhoneStateChanged";
    private static Context context; //Context to make Toast if required
    private static final String mutedUnmutedSpeakers="muteUnmute";

    public PhoneStateManager(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);

        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                VolumeAdjusting.createSocketWithGivenMessage(mutedUnmutedSpeakers+"");
                Toast.makeText(context, "Phone state Idle", Toast.LENGTH_LONG).show();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //when Off hook i.e in call
                VolumeAdjusting.createSocketWithGivenMessage(mutedUnmutedSpeakers+"");
                Toast.makeText(context, "Phone state Off hook", Toast.LENGTH_LONG).show();
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                VolumeAdjusting.createSocketWithGivenMessage(mutedUnmutedSpeakers+"");
                Toast.makeText(context, "Phone state Ringing", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}