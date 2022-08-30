package io.uouo.wechatbot.common.util;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.uouo.wechatbot.common.AjaxResult;
import io.uouo.wechatbot.domain.BuguaLog;
import io.uouo.wechatbot.domain.Person;

public class MyFileUtil {
	private final static Logger logger = LogManager.getLogger(MyFileUtil.class);
	
	/**
	 * 通过文件名获取文件地址
	 * @param FileName
	 * @return FilePath
	 */
	public static  String getFilePathByFileName(String FileName){
		//String filePath = MyFileUtil.class.getClassLoader().getResource(FileName).getPath();
		String filePath = Thread.currentThread().getContextClassLoader().getResource(FileName).getPath();
	    filePath = filePath.substring(1);   
	    return filePath;
	}
	
	public static <T> boolean writeFile( List<T> list,String filePath) {

        try {
        	File file = new File(filePath);
            for (T clazz : list) {
            	ObjectOutputStream oos = getOOS(file);
                oos.writeObject(clazz);
                oos.flush();
                oos.close();
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	logger.debug("将对象写入文件失败");
            return false;
        }
        return true;
    }
	
    private static ObjectOutputStream getOOS(File storageFile)
            throws IOException {
		if (storageFile.exists()) {
		    return new AppendableObjectOutputStream(new FileOutputStream(storageFile, true));
		} else {
		    return new ObjectOutputStream(new FileOutputStream(storageFile));
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> readFile(String filePath) {
        // 路径
		List<T> list = new ArrayList<T>();
		
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file); 
	        ObjectInputStream reader = getOIS(fis);
	        
	        while (true) {
	            try {
	            	
					T clazz =  (T) reader.readObject();
	            	list.add(clazz);
	            } catch (EOFException e) {
	            	logger.debug("读取完成");
	            	break;
	            }
	        }
	        fis.close();
		} catch (Exception e) {
			logger.debug("读取文件失败"+e);
			e.printStackTrace();
        }
        return list;
    }
	
	
	private static ObjectInputStream getOIS(FileInputStream fis) throws IOException {
        if(fis.getChannel().position() != 0 ) {
        	return  new ObjectInputStream(fis);
        }else{
        	return new AppendableObjectInputStream(fis);
        }
	}

	// 删除文件
    public static void deleteAndCreateFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
        	if (file.delete()) {
                logger.debug("删除单个文件" + filePath + "成功！");
            } else {
                logger.debug("删除单个文件" + filePath + "失败！");
            }
        }
        logger.debug("删除单个文件失败：" + filePath + "不存在！");
        
        try {
			if (file.createNewFile()) {
				logger.debug("新增单个文件" + filePath + "成功！");
			} else {
				logger.debug("新增单个文件" + filePath + "失败！");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    
    
    /**
     * 通过微信id获取昵称
     * @param wxid
     * @return
     */
    public static String getNickNameByWxid(String wxid){
    	List<Person> list = new ArrayList<Person>();
    	String filePath = MyFileUtil.getFilePathByFileName(AjaxResult.PERSON);
		list = MyFileUtil.readFile(filePath);
    	for (Person person : list) {
			if(wxid.equals(person.getWxid())){
				return person.getNickname();
			}
		}
		return "";
    	
    }
    
    /**
     * 获取当前的接龙状态
     * @return
     */
    /*public static String getJielongStatus(){
    	List<JielongStatus> list = new ArrayList<JielongStatus>();
    	String filePath = MyFileUtil.getFilePathByFileName(AjaxResult.JIELONGSTATUS);
		list = MyFileUtil.readFile(filePath);
    	for (JielongStatus status : list) {
			if(status!=null){
				return status.getIsJielong();
			}
		}
		return "";
    }*/
    
    /**
     * 获取当日卜卦情况
     * @param wxid
     * @return
     */
    public static String getBuguaLogByWxid(String wxid) {
    	List<BuguaLog> list = new ArrayList<BuguaLog>();
    	String filePath = MyFileUtil.getFilePathByFileName(AjaxResult.BUGUALOG);
		list = MyFileUtil.readFile(filePath);
		String date=DateTimeUtil.getDateToday();
    	for (BuguaLog buguaLog : list) {
			if(wxid.equals(buguaLog.getWxid())
					&&date.equals(buguaLog.getDate())){
				return buguaLog.getDivinationInfo();
			}
		}
		return "";
	}
    /**
	 * 测试工具类好不好用
	 * @param args
	 */
	public static void  main(String[] args) {
		/*String content = "[{'nickname':'AI','roomid':'21398419530@chatroom','wxid':'wxid_g4uasfvu37vc21'},{'nickname':'光明电信','roomid':'21398419530@chatroom','wxid':'wxid_gpl326asr22i22'},{'nickname':'小可爱老婆','roomid':'21398419530@chatroom','wxid':'wxid_z6fnflwvibkq21'}]";
		List<Person> list = new ArrayList<Person>();
		list = JSONObject.parseArray(content, Person.class);*/
		
		//通过文件名获取文件路径
		String filePath = MyFileUtil.getFilePathByFileName(AjaxResult.BUGUALOG);
		//String filePath = MyFileUtil.getFilePathByFileName(AjaxResult.PERSON);
		//MyFileUtil.writeFile(list, filePath);
		List<Object> list1 = MyFileUtil.readFile(filePath);
		for (Object clazz : list1) {
			System.out.println(clazz.toString());
		}
	}

	private static class AppendableObjectOutputStream extends ObjectOutputStream { 

		 public AppendableObjectOutputStream(OutputStream out) throws IOException { 
		  super(out); 
		 } 
		
		 @Override 
		 protected void writeStreamHeader() throws IOException { 
		 }
		 
    } 

    private static class AppendableObjectInputStream extends ObjectInputStream { 

	     public AppendableObjectInputStream(InputStream in) throws IOException { 
	    	 super(in); 
	     } 
	
	     @Override 
	     protected void readStreamHeader() throws IOException {
	    	 
	     } 
    } 
	
}
