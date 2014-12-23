package br.comtairoroberto.testeintentservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadCastTest extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.i("Script", "onReceive(): reiniciado o device.");
		intent = new Intent("SERVICE_TEST");
		context.startService(intent);
	}

}
