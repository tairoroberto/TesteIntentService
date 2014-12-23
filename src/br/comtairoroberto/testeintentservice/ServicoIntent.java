package br.comtairoroberto.testeintentservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class ServicoIntent extends IntentService implements CountListener{
	private int count;
	private boolean active;
	private boolean stopAll;
	private Controller controller = new Controller();
	
	public ServicoIntent() {
		super("ServicoIntentThread");
		
		// inicializa as variaveis
		count = 0;
		active = true;
		stopAll = true;
	}
	
	
	class Controller extends Binder{
		public CountListener getCountListener() {
			return(ServicoIntent.this);
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return controller;
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Logica para parar todos os servicos
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			int deligar = bundle.getInt("desligar");
			
			if (deligar == 1) {
				stopAll = false;
			}
		}
		
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		while (stopAll && active && count < 5) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count++;
			Log.i("Script", "Contador"+ count );
		}
		active = true;
		count = 0;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count;
	}

}
