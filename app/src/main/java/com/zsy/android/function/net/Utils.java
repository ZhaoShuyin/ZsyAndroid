package com.zsy.android.function.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {
public static String getStr(InputStream inputstream)throws IOException{
	
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	int len = -1;
	byte[] buffer = new byte[1024];
	while((len = inputstream.read(buffer))!=-1){
		baos.write(buffer, 0, len);
	}
	inputstream.close();
	String result = new String(baos.toByteArray());
		
	return result;
}
}
