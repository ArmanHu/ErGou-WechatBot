package io.uouo.wechatbot.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.util.StringUtils;

import io.uouo.wechatbot.common.AjaxResult;

public class ChengyuUtil {

	/**
	 * 通过成语 找 能接龙的成语
	 * @param chengyu
	 * @return
	 */
	public static List<String> getChengyuListByChengyu(String chengyu) {
		
		//获取成语的最后一个字
		String lastWord = chengyu.substring(chengyu.length()-1);
		
		return getChengyuListByKeyword(lastWord);
	}
	
	/**
	 * 通过关键字查成语
	 * @param keyword
	 * @return
	 */
	public static List<String> getChengyuListByKeyword(String keyword) {
		
		//可用的所有成语
		List<String> list = new ArrayList<String>();
		if(!StringUtils.hasLength(keyword))
		return list;
		if(!isChineseChar(keyword.toCharArray()[0]))
		return list;
		//获取关键字的拼音
		List<String> pinyinList = getPinyinByWord(keyword);
		if(pinyinList==null||pinyinList.size()==0)
		return list;
		
		for (String pinyin : pinyinList) {
			list.addAll(getChengyuListByPinyin(pinyin)) ;
		}
		return list;
	}
	
	/**
	 * 通过首字拼音找成语
	 * @param pinyin
	 * @return
	 */
	public static List<String> getChengyuListByPinyin(String pinyin) {
		
		//可用的所有成语
		List<String> list = new ArrayList<String>();
		
		//获取拼音List
		if(!StringUtils.hasLength(pinyin))
		return list;
		
		String filePath = MyFileUtil.getFilePathByFileName(AjaxResult.CHENGYU);
		Path path = Paths.get(filePath);
	    // 注意是Paths,有s。注意不要写成“D:\100\200”这样的，严格的讲不用斜杠，虽然也能用
	    List<String> list1 = new ArrayList<String>();
		try {
			list1 = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(list1==null||list1.size()==0)
		return list;
		
	    for (String str : list1) {
	    	String[] split = str.split("=");
	    	String chengyu1 = split[0];
	    	String pinyin1 = split[1];
    		if(pinyin.equals(pinyin1.split(" ")[0])){
	    		list.add(chengyu1);
	    	}
		}
		return list;
	}
	
	public static String getRandomStringFromList(List<String> list){
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
	
	public static List<String> getPinyinByWord(String word) {
		List<String> pinyinList = new ArrayList<String>();
		
		if(!StringUtils.hasLength(word))
		return pinyinList;
		if(!isChineseChar(word.toCharArray()[0]))
		return pinyinList;
		
		//对汉字进行编码，获取编码中的数字
		String unicode = UnicodeUtil.encodeUnicode(word);
		String code = unicode.split("\\\\u")[1];

		//汉字拼音大全
		String filePath = MyFileUtil.getFilePathByFileName(AjaxResult.PINYIN);
		Path path = Paths.get(filePath);
	    List<String> list = new ArrayList<String>();
		try {
			list = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(list==null||list.size()==0)
		return pinyinList;
	    for (String str : list) {
	    	String[] split = str.split("=");
	    	String code1 = split[0];
	    	if(code.equals(code1)){
	    		//líng,xīng 可能是这种结构
	    		String[] split2 = split[1].split(",");
	    		for (String string : split2) {
	    			if(StringUtils.hasLength(string))
	    			pinyinList.add(string);
				}
	    		return pinyinList;
	    	}
		}
	    
		return pinyinList;
	}

	/**

     * 判断一个字符是否是汉字

     * PS：中文汉字的编码范围：[\u4e00-\u9fa5]

     *

     * @param c 需要判断的字符

     * @return 是汉字(true), 不是汉字(false)

     */

    public static boolean isChineseChar(char c) {

        return String.valueOf(c).matches("[\u4e00-\u9fa5]");

    }
	public static void main(String[] args) throws UnsupportedEncodingException {
		List<String> str=getPinyinByWord("猜");
		System.out.println("------------------"+str);
		
		/*String str= UnicodeUtil.encodeUnicode("意");
		String[] split = str.split("\\\\u");
		for (String string : split) {
			System.out.println("------------------"+string);
		}*/
		
		/*List<String> chengyuList = getChengyuListByChengyu("以弱毙强");
		for (String string : chengyuList) {
			System.out.println(string);
		}*/
		/*String chengyu="两情相悦";
		String a = chengyu.substring(0,1);
		System.out.println(a);*/
	}
}
