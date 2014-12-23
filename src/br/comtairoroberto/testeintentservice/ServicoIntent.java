package br.comtairoroberto.testeintentservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;

public class ServicoIntent extends IntentService implements CountListener{
	private int count;
	private boolean active;
	private boolean stopAll;
	private ResultReceiver resultReceiver;
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
			}else{
				stopAll = true;
				resultReceiver = intent.getParcelableExtra("receiver");
			}
		}
		
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		while (stopAll && active && count < 5) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count++;
			Log.i("Script", "Contador"+ count );
			
			//Coloca o contador dentro de un Bundle para envia-lo a intent
			Bundle bundle = new Bundle();
			bundle.putInt("count", count);
			
			//Envia um conteudo pelo resultReceiver
			resultReceiver.send(1, bundle);
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
