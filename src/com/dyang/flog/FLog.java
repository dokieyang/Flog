package com.dyang.flog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;

import android.os.Environment;
import android.util.Log;

public class FLog {

	private static boolean OPEN_FILE_LOG = true;

	private static boolean OPEN_LOGCAT = true;

	public static String logFolderPath;

	public static final int VERBOSE = 2;

	public static final int DEBUG = 3;

	public static final int INFO = 4;

	public static final int WARN = 5;

	public static final int ERROR = 6;

	public static final int ASSERT = 7;

	public static int logLevel = 2;

	private static FLog mInstance = null;
	
	public synchronized static FLog getInstance(){
		if(mInstance == null){
			init();
			mInstance = new FLog();
		}
		return mInstance;
	}
	
	private FLog(){  }
	
	private static void init(){
		if( isSdcardMounted()){
			logFolderPath = Environment.getExternalStorageDirectory().toString() + "/" + "FLog/"; 
		}
	}
	public void setLogLevel(int level) {
		logLevel = level;
	}

	public void v(String tag, String msg) {
		print2Logcat(VERBOSE, tag, msg);
		write2File(VERBOSE, tag, msg);
	}

	public void d(String tag, String msg) {
		print2Logcat(VERBOSE, tag, msg);
		write2File(VERBOSE, tag, msg);
	}

	public void i(String tag, String msg) {
		print2Logcat(INFO, tag, msg);
		write2File(INFO, tag, msg);
	}

	public void w(String tag, String msg) {
		print2Logcat(WARN, tag, msg);
		write2File(WARN, tag, msg);
	}

	public void e(String tag, String msg) {
		print2Logcat(ERROR, tag, msg);
		write2File(ERROR, tag, msg);
	}

	private void print2Logcat(int level, String tag, String msg) {
		if(!OPEN_LOGCAT){
			return;
		}
		
		if (level >= logLevel) {
			switch (level) {
			case VERBOSE:
				Log.v(tag, msg);
				break;
			case INFO:
				Log.i(tag, msg);
				break;
			case DEBUG:
				Log.d(tag, msg);
				break;
			case WARN:
				Log.w(tag, msg);
				break;
			case ERROR:
				Log.e(tag, msg);
				break;
	
			default:
				break;
			}
		}
	}

	private void write2File(int level, String tag, String msg) {
		
		if( !isSdcardMounted() || !OPEN_FILE_LOG){
			return;
		}
		
		if(logFolderPath == null || logFolderPath.isEmpty()){
			return;
		}
		File lfp = new File(logFolderPath);
		lfp.mkdirs();
		
		if( !lfp.isDirectory() ){
			return;
		}
		
		File logPath = new File(lfp, "log.txt");
		if( !logPath.exists()){
			try {
				logPath.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		if (level >= logLevel) {
			String content = getCurrTime() + ": ";
			String levelFlag = convertLevel2Flag(level);
			content += levelFlag;
			content += ": ";
			content += msg;
			OutputStreamWriter osw = null; 
			try {
				osw = new OutputStreamWriter(new FileOutputStream(logPath, true));
				osw.write(content + "\r\n");
				osw.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if( osw != null ){
					try {
						osw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			
		}
	}

	private String convertLevel2Flag(int level){
		switch (level) {
		case VERBOSE: return "VERBOSE";
		case INFO:    return "INFO";
		case DEBUG:   return "DEBUG";
		case WARN:    return "WARN";
		case ERROR:   return "ERROR";
		default:
			break;
		}
		return "UNKNOW";
	}
	
	private static boolean isSdcardMounted(){
		boolean ret = Environment.getExternalStorageState() .equals(android.os.Environment.MEDIA_MOUNTED);
		return ret;
	}
	
	/**
	 * 
	 * @param folderPath  保存log的文件夹，必须是sdcard得目录如：/sdcard/folderPath/
	 */
	public void setLogFolderPath(String folderPath){
		logFolderPath = folderPath;
	}
	
	public String getLogFolderPath(){
		return logFolderPath;
	}
	
	public static boolean isOPEN_FILE_LOG() {
		return OPEN_FILE_LOG;
	}

	public static void setOPEN_FILE_LOG(boolean open) {
		OPEN_FILE_LOG = open;
	}

	public static boolean isOPEN_LOGCAT() {
		return OPEN_LOGCAT;
	}

	public static void setOPEN_LOGCAT(boolean open) {
		OPEN_LOGCAT = open;
	}
	
	private String getCurrTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		return format.format(System.currentTimeMillis()).toString();
				
	}
	

}
