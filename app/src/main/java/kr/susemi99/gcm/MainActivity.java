package kr.susemi99.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import kr.susemi99.gcm.managers.PreferenceManager;
import kr.susemi99.gcm.services.RegistrationIntentService;

public class MainActivity extends AppCompatActivity
{
  private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

  private TextView textHello;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    textHello = (TextView) findViewById(R.id.text_hello);

    if (checkPlayServices())
      startService(new Intent(this, RegistrationIntentService.class));
  }

  @Override
  protected void onResume()
  {
    super.onResume();

    IntentFilter filter = new IntentFilter();
    filter.addAction(getString(R.string.action_registration_complete));
    LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
  }

  @Override
  protected void onPause()
  {
    super.onPause();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
  }

  /**
   * Check the device to make sure it has the Google Play Services APK. If
   * it doesn't, display a dialog that allows users to download the APK from
   * the Google Play Store or enable it in the device's system settings.
   */
  private boolean checkPlayServices()
  {
    GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
    int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
    if (resultCode != ConnectionResult.SUCCESS)
    {
      if (apiAvailability.isUserResolvableError(resultCode))
      {
        apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
      }
      else
      {
        Log.i("MainActivity | checkPlayServices", "This device is not supported.|");
        finish();
      }
      return false;
    }
    return true;
  }

  ////////////////////////////////////////
  // broadcast receiver
  ///////////////////////////////////////
  private BroadcastReceiver receiver = new BroadcastReceiver()
  {
    @Override
    public void onReceive(Context context, Intent intent)
    {
      boolean sentToken = PreferenceManager.instance(getApplicationContext()).sentToken();
      textHello.append("\n");
      textHello.append(sentToken ? getString(R.string.gcm_send_message) : getString(R.string.token_error_message));
    }
  };
}
