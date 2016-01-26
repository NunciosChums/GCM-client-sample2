package kr.susemi99.gcm.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import kr.susemi99.gcm.MainActivity;
import kr.susemi99.gcm.R;

public class GCMMessageListenerService extends GcmListenerService
{
  private static final int NOTIFICATION_ID = 91732846;

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

    if (data.containsKey("message"))
      sendNotification(data.getString("message"));
  }

  private void sendNotification(String message)
  {
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, intent, PendingIntent.FLAG_ONE_SHOT);

    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(getString(R.string.app_name)).setContentText(message)
                                                                                         .setAutoCancel(true).setSound(defaultSoundUri).setPriority(NotificationCompat.PRIORITY_MAX)
                                                                                         .setContentIntent(pendingIntent);

    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
  }
}
