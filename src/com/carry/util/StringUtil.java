package com.carry.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串验证工具类
 * @author yangguoxiang
 *
 */
public class StringUtil {
	
	/**
	 * 判断字符串是否为非空(包含null与"")
	 * @param str
	 * @return boolean
	 */
	public static boolean isNotEmpty(String str){
		return str == null || "".equals(str.trim()) || "null".equals(str.trim().toLowerCase()) ? false : true;
	}
	
	/**
	 * 判断字符串是否为空(包含null与"")
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmpty(String str){
		return str == null || "".equals(str.trim()) || "null".equals(str.trim().toLowerCase()) ? true : false;
	}
	
	/**
	 * 判断Object是否为空
	 * @param obj
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public static boolean ObjectIsEmpty(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String && (obj.equals("") || obj.equals("0"))) {
			return true;
		} else if (obj instanceof Number &&((Number) obj).doubleValue() == 0) {
			return true;
		} else if (obj instanceof Boolean && !((Boolean) obj)){
			return true;
		} else if (obj instanceof Collection && ((Collection) obj).isEmpty()){
			return true;
		} else if (obj instanceof Map && ((Map) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static boolean ObjectIsEmpty2(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String && (obj.equals(""))) {
			return true;
		} else if (obj instanceof Boolean && !((Boolean) obj)){
			return true;
		} else if (obj instanceof Collection && ((Collection) obj).isEmpty()){
			return true;
		} else if (obj instanceof Map && ((Map) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Collection obj){
		if( obj.isEmpty() ){
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Map obj){
		if(obj==null){
			return true;
		}else if( obj.isEmpty() ){
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isEmpty(Integer obj){
		if( obj.doubleValue()==0 ){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 格式化字符串，如果为空，返回""
	 * @param str
	 * @return String
	 */
	public static String formatString(String str) {
		if(isEmpty(str)) {  
            return "";  
        } else {  
            return str;  
        }
	}
	
	
	/**
	 * 判断两个字符串是否相同
	 * @param str1 值1
	 * @param str2 值2
	 * @return boolean
	 */
	public static boolean equals(String str1, String str2) {
		if(str1==null && str2 == null){
			return true;
		}
		if(str1 == null || str2 == null){
			return false;
		}
		return str1.equals(str2);
	}
	
	
	public static boolean equalsObj(Object obj1, Object obj2) {
		boolean bool = true;
		if(ObjectIsEmpty(obj1) && !ObjectIsEmpty(obj2)) {
			bool = false;
		}else if(!ObjectIsEmpty(obj1) && ObjectIsEmpty(obj2)){
			bool = false;
		}else if(!ObjectIsEmpty(obj1)&& !ObjectIsEmpty(obj2)){
			if(obj1 instanceof Date || obj2 instanceof Date){
				
			}else if(obj1 instanceof Collection || obj2 instanceof Collection){
				
			}else{
				bool = StringUtil.equals(StringUtil.toString(obj1), StringUtil.toString(obj2));
			}
		}
		return bool;
	}
	
	/**
	 * 判断是否为数字
	 * @param str
	 * @return boolean
	 */
	public static boolean isNumber(String str) {
		try{
			Integer.parseInt(str);
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	/**
	 * 判断是否为浮点数或者整数
	 * @param str
	 * @return boolean
	 */
	public static boolean isNumeric(String str){
          Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
          Matcher isNum = pattern.matcher(str);
          if( !isNum.matches() ){
                return false;
          }
          return true;
    }
	
	/**
	 * 判断是否为正确的邮件格式
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmail(String str){
		if(isEmpty(str))
			return false;
		return str.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
	}
	
	/**
	 * 判断字符串是否为合法手机号 11位 13 14 15 18开头
	 * @param str
	 * @return boolean
	 */
	public static boolean isMobile(String str){
		if(isEmpty(str))
			return false;
		return str.matches("^(13|14|15|18)\\d{9}$");
	}
	
	/**
	 * Check that the given CharSequence is neither <code>null</code> nor of length 0.
	 * Note: Will return <code>true</code> for a CharSequence that purely consists of whitespace.
	 * <p><pre>
	 * StringUtil.hasLength(null) = false
	 * StringUtil.hasLength("") = false
	 * StringUtil.hasLength(" ") = true
	 * StringUtil.hasLength("Hello") = true
	 * </pre>
	 * @param str the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not null and has length
	 */
	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}
	
	public static String replace(String inString, String oldPattern, String newPattern) {
		if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
			return inString;
		}
		StringBuilder sb = new StringBuilder();
		int pos = 0;
		int index = inString.indexOf(oldPattern);
		int patLen = oldPattern.length();
		while (index >= 0) {
			sb.append(inString.substring(pos, index));
			sb.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sb.append(inString.substring(pos));
		return sb.toString();
	}
	
	/**
	 * Check that the given String is neither <code>null</code> nor of length 0.
	 * Note: Will return <code>true</code> for a String that purely consists of whitespace.
	 * @param str the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not null and has length
	 */
	public static boolean hasLength(String str) {
		return hasLength((CharSequence) str);
	}
	
	public static byte[] getUtf8Bytes(String str){
		if(hasLength(str)){
			try {
				return str.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		return null;
	}


	public static String getStringFromUtf8Bytes(byte[] tmpArray) {
		if(tmpArray != null && tmpArray.length > 0){
			try {
				return new String(tmpArray,"UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		return null;
	}
	
	public static String toString(Object obj) {
		String str = "";
		if(obj!=null){
			str = obj.toString();
		}else{
			str = "";
		}
		return str;
	}
	
	public static int toInt(Object obj) {
		int count = 0;
		if(obj!=null){
			if(obj instanceof BigInteger){
				count = Integer.valueOf(obj.toString());
			}else{
				count = Integer.parseInt(String.valueOf(obj));
			}
		}else{
			count = 0;
		}
		return count;
	}
	
	public static int toIntOfBigInteger(BigInteger obj) {
		int count = 0;
		if(obj!=null){
			count = Integer.parseInt(String.valueOf(obj));
		}else{
			count = 0;
		}
		return count;
	}
	
	public static BigDecimal stringToBigDecimal(String str) {
		BigDecimal bd = null;
		if(str!=null){
			bd = new BigDecimal(str);
		}
		return bd;
	}
	
	public static int toIntOfLong(long lg) {
		int i = 0;
		i = Integer.parseInt(String.valueOf(lg));
		return i;
	}

	//禁止实例化
	private StringUtil(){}

	public static void main(String[] args) {
		String a = "91610131726291097U";
		a = a.substring(8,17);
		System.out.println(a);
	}
}
