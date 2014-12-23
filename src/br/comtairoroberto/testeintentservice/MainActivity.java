package br.comtairoroberto.testeintentservice;

import br.comtairoroberto.testeintentservice.ServicoIntent.Controller;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements ServiceConnection{
	private CountListener countListener;
	private ServiceConnection connection;
	private ResultReceiverListener resultReceiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		connection = MainActivity.this;
		bindService(new Intent("SERVICO_INTENT"), connection , Context.BIND_AUTO_CREATE);
		
	}

	
	public void startService(View view) {		
		//Inicia o resultReceiverListeenr com null para evitar problemas futuros
		resultReceiver = null;
		resultReceiver = new ResultReceiverListener(null);
		
		Intent intent = new Intent("SERVICO_INTENT");
		intent.putExtra("receiver", resultReceiver);
		
		startService(intent);
		
	}
	
	public void stopService(View view) {		
		Intent intent = new Intent("SERVICO_INTENT");
		intent.putExtra("desligar", 1);
		startService(intent);
	}
	
	public void getCount(View view) {
		Toast.makeText(this, "Count:"+countListener.getCount(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		// TODO Auto-generated method stub
		Controller controller = (Controller)service;
		countListener = controller.getCountListener();
	}


	@Override
	public void onServiceDisconnected(ComponentName name) {
		// TODO Auto-generated method stub
		
	}
	
	private class ResultReceiverListener extends ResultReceiver{

		public ResultReceiverListener(Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			// TODO Auto-generated method stub
			if (resultCode == 1) {
				final int count = resultData.getInt("count");
				
				//Rodar o resultado na thread principal
				runOnUiThread(new Runnable() {					
					@Override
					public void run() {
						Toast.makeText(getApplicationContext(), "Contador:"+ count,Toast.LENGTH_SHORT).show();						
					}
				});
			}
		}		
	}
}
