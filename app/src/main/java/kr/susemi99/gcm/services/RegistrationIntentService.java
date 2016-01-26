package kr.susemi99.gcm.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import kr.susemi99.gcm.R;
import kr.susemi99.gcm.managers.PreferenceManager;

/**
 * Created by susemi99 on 2016. 1. 26..
 */
public class RegistrationIntentService extends IntentService
{
  public RegistrationIntentService()
  {
    super("");
  }

  @Override
  protected void onHandleIntent(Intent intent)
  {
    InstanceID instanceID = InstanceID.getInstance(this);
    try
    {
      String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
      Log.i("RegistrationIntentService | onHandleIntent", "|" + token + "|");
      PreferenceManager.instance(this).sentToken(true);

      Intent registrationCompleteIntent = new Intent(getString(R.string.action_registration_complete));
      LocalBroadcastManager.getInstance(this).sendBroadcast(registrationCompleteIntent);
    } catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
