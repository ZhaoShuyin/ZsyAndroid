package com.zsy.android.base.aidl;

import cn.zsy.aidl.IAidl.Stub;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class AidlService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return new MyBinder();
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	public void remotemethod(){
		Log.e("aidltest", "Aidl被调用了: ");
	}
	
	public class MyBinder extends Stub{
		public void callRemoteMethod(){
			remotemethod();
		}

		@Override
		public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
			Log.e("aidltest", "Aidl被调用了: anInt=="+anInt
					+",\n aLong=="+aLong
					+",\n aBoolean=="+aBoolean
					+",\n aFloat=="+aFloat
					+",\n aDouble=="+aDouble
					+",\n aString=="+aString
			);
		}
	}
}
