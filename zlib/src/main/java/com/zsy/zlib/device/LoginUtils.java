package com.zsy.zlib.device;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.os.Environment;

public class LoginUtils {
	
	public static boolean SaveInfo(String username,String pwd ){
		
		String temp = username+"##"+pwd;
		File file = new File("/data/data/cn.itcast.bancount/info.txt");
		
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(temp.getBytes());
			fos.close();
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	public static String[] readInfo(){
		String[] strs = null;
		
		File file = new File("/data/data/cn.itcast.bancount/info.txt");
		
		try {
			FileInputStream inputStream = new FileInputStream(file);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		    String str = reader.readLine();
		    
		   strs =  str.split("##");
		   
		   reader.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return strs;
	}
	public static String[] readInfoSDcard(){
		String[] strs = null;
		
		File file = new File(Environment.getExternalStorageDirectory(),"info.txt");
		
		try {
			FileInputStream inputStream = new FileInputStream(file);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			
			String str = reader.readLine();
			
			strs =  str.split("##");
			
			reader.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return strs;
	}

		
       public static boolean SaveInfo(Context context , String username,String password )
       {
    	  String temp = username+"##"+password;
    	  
    	  File file = new File (context.getFilesDir(),"info2.txt");
    	  
    	  try {
		
//    		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file))) ; 
//    		String readLine = reader.readLine();
//    		reader.close();
//    		  String[] split = readLine.split("##");
    	 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
    	 
    	 writer.write(temp);
    	 
    	 writer.close();
    		
    		return true;
    		  
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		} 
		
    	   
    	   
       }
       public static boolean SaveInfo2(Context context , String username,String password )
       {
    	   String temp = username+"##"+password;
    	   
    	   //File file = new File (context.getFilesDir(),"info22.txt");
    	   
    	   try {
    		   FileOutputStream fos = context.openFileOutput("info22.txt",context.MODE_PRIVATE);
    		   
    		   fos.write(temp.getBytes());
    		   
    		   fos.close();

    		   //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
    		   
    		  // writer.write(temp);
    		   
    		   //writer.close();
    		   
    		   return true;
    		   
    	   } catch (Exception e) {
    		   // TODO: handle exception
    		   e.printStackTrace();
    		   return false;
    	   }   
    	   
    	   
    	   
       }
		
		public static boolean saveInfoSDcard (Context context, String username , String password){
			
			String temp = username +"##" + password;
			File file = new File(Environment.getExternalStorageDirectory(),"info33.txt");
			try {
				
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(temp.getBytes());
				fos.close();
				
				return true;
			  } catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				
				return false;
			}
			
			 
		}
		
		
	
	
}