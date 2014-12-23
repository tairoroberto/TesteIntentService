package br.comtairoroberto.testeintentservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ServicoConexao extends Service  implements CountListener{
	private int count = 0;
	private boolean active = true;

	//Variavel para ser retornada pelo onBinder
	private Controller controller= new Controller();
	
	class Controller extends Binder{
		public CountListener getCountListener() {
			return(ServicoConexao.this);
		}
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return controller;
	}
	
	//Ciclo de vida do serviço
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("Script", "onCreate()");
		// Metodo para inciar o contador
		//setThread();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Metodo para inciar o contador
		setThread();
		Log.i("Script", "onStartCommand()");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		active = false;
	}
	
	public void setThread() {
		new Thread(){
			public void run() {
				while (active && count < 100) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						// TODO: handle exception
					}
					count++;
					Log.i("Script", "Contador: "+count);
				}
			}
		}.start();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count;
	}

}
