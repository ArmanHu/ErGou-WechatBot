package io.uouo.wechatbot.common.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.util.StringUtils;

public class RandomTextUtil {

	public static  String getRandomText(String filePath) {
		
	    
		Path path = Paths.get(filePath);
	    // 注意是Paths,有s。注意不要写成“D:\100\200”这样的，严格的讲不用斜杠，虽然也能用
	      
	    List<String> list = new ArrayList<String>();
		try {
			list = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	    // 获取集合大小
	    int size = list.size();
	    // 获取随机数
	    int i = new Random().nextInt(size);
	    // 得到一行
	    String str = list.get(i);
	    if(!StringUtils.hasLength(str))
	    	return "";
	    
	    // 输出
		return str;
		
	}
	
	
	public static void main(String[] args) {
		String str="1111111111111\\n222222";
		String str1=str.replaceAll("\\\\n","\n");
		System.out.println("------------------"+str1);
	}
}
