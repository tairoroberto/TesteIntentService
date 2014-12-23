package br.comtairoroberto.testeintentservice;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ServiceTest extends Service {
	//ArrayList de threads parar controlar o termino dos services
	public List<Worker> threads = new ArrayList<Worker>();
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//Ciclo de vida do service
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i("Script", "onCreate()");
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		//para as os servicos que estão na lista de threads
		for (int i = 0; i < threads.size(); i++) {
			threads.get(i).ativo = false;
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		Log.i("Script", "onStartCommand()");	
		Worker worker = new Worker(startId);
		worker.start();
		threads.add(worker);
		
		
		//São três tipos re retorno
		//Esse retorno indica que se o sistema matar o serviço, o serviço não voltara a execultar
		//return(START_NOT_STICKY);
		
		//Esse retorno indica que se o sistema matar o serviço, o serviço irá voltara a execultar
		//porem sem os valores que estavam alocados na intent
		//return(START_STICKY);
		
		//Esse retorno indica que se o sistema matar o serviço, o serviço irá voltara a execultar
		//porem com os valores que estavam alocados na intent
		//return(START_REDELIVER_INTENT);
		
		
		//Esse retorno indica que se o sistema matar o serviço, o serviço irá voltara a execultar
		//porem sem os valores que estavam alocados na intent ou seja o mesmo que START_STICKY
		return super.onStartCommand(intent, flags, startId);
	}
	
	//Classe Worker
	class Worker extends Thread{
		private int count = 0;
		private int startId;
		boolean ativo = true;
		
		public Worker(int startId){
			this.startId = startId;
		}
		
		
		public void run() {
			while (ativo && count < 1000) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count++;
				Log.i("Script", "Contador:"+ count);				
			}
			//Para o startCommand
			stopSelf(startId);
		}
	}

}
