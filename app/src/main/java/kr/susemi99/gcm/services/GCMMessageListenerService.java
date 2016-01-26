package kr.susemi99.gcm.services;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

public class GCMMessageListenerService extends GcmListenerService
{
  @Override
  public void onMessageReceived(String from, Bundle data)
  {
    super.onMessageReceived(from, data);
    Log.i("GCMMessageListenerService | onMessageReceived", "from : " + from);

    for (String key : data.keySet())
    {
      Object value = data.get(key);
      Log.i("GCMMessageListenerService | onMessageReceived", String.format("%s : %s (%s)", key, value.toString(), value.getClass().getName()));
    }
  }
}
