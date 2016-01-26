package kr.susemi99.gcm.services;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by susemi99 on 2016. 1. 26..
 */
public class InstanceIDUpdateListenerService extends InstanceIDListenerService
{
  /**
   * Called if InstanceID token is updated. This may occur if the security of
   * the previous token had been compromised. This call is initiated by the
   * InstanceID provider.
   */
  @Override
  public void onTokenRefresh()
  {
    super.onTokenRefresh();
    Intent intent = new Intent(this, RegistrationIntentService.class);
    startService(intent);
  }
}
