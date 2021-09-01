package com.example.demo.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * 对象转String
     * @param str 需要转换String的对象
     * @return
     */
    public static String toString(Object str){
        return isEmpty(str) ? "" : String.valueOf(str);
    }
    /**
     * 判断字符串对象是否为空
     * @param str 需校验字符串
     * @return
     */
    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }
    /**
     * 判断字符串的长度是否大于0
     * @param str 需校验字符串
     * @return " " => true; "" => false
     */
    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }
    /**
     * 判断一个字符串是否有内容
     * @param str
     * @return
     */
    public static boolean hasText(String str) {
        return (hasLength((CharSequence) str) && containsText((CharSequence) str));
    }
    /**
     * 判断字符串的内容是否为空白字符
     * isWhitespace()方法用于判断指定字符是否为空白字符
     * @param str 需校验字符串
     * @return
     */
    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    /**
     * 校验给定的字符串str是否以prefix开头，忽略大小写
     * @param str 需校验字符串
     * @param prefix 检验字符串的开头的字符串
     * @return
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        return (str != null && prefix != null && str.length() >= prefix.length() &&
                str.regionMatches(true, 0, prefix, 0, prefix.length()));
    }
    /**
     * 校验给定的字符串str是否以suffix结尾，忽略大小写
     * @param str 需校验字符串
     * @param suffix 检验字符串的结尾的字符串
     * @return
     */
    public static boolean endsWithIgnoreCase(String str, String suffix) {
        return (str != null && suffix != null && str.length() >= suffix.length() &&
                str.regionMatches(true, str.length() - suffix.length(), suffix, 0, suffix.length()));
    }
    /**
     * 判断sub在str中出现的次数
     * @param str 全字符串
     * @param sub 判断出现次数的字符串
     * @return
     */
    public static int countOccurrencesOf(String str, String sub) {
        if (!hasLength(str) || !hasLength(sub)) {
            return 0;
        }
        int count = 0;
        int pos = 0;
        int idx;
        while ((idx = str.indexOf(sub, pos)) != -1) {
            ++count;
            pos = idx + sub.length();
        }
        return count;
    }
    /**
     * 删除给定字符串中包含有的任意字符
     * @param inString 给定字符串
     * @param charsToDelete 需要删除的字符
     * @return
     */
    public static String deleteAny(String inString, String charsToDelete) {
        if (!hasLength(inString) || !hasLength(charsToDelete)) {
            return inString;
        }

        StringBuilder sb = new StringBuilder(inString.length());
        for (int i = 0; i < inString.length(); i++) {
            char c = inString.charAt(i);
            if (charsToDelete.indexOf(c) == -1) {
                sb.append(c);
            }
        }
        return sb.toString();
    }




    /**
     *  获取汉字首字母或全拼大写字母
     *
     * @param chinese 汉字
     * @param isFull  是否全拼 true:表示全拼 false表示：首字母
     *
     * @return 全拼或者首字母大写字符窜
     */
    public static String getUpperCase(String chinese,boolean isFull){
        return convertHanzi2Pinyin(chinese,isFull).toUpperCase();
    }

    /**
     * 获取汉字首字母或全拼小写字母
     *
     * @param chinese 汉字
     * @param isFull 是否全拼 true:表示全拼 false表示：首字母
     *
     * @return 全拼或者首字母小写字符窜
     */
    public static  String getLowerCase(String chinese,boolean isFull){
        return convertHanzi2Pinyin(chinese,isFull).toLowerCase();
    }

    /**
     * 将汉字转成拼音
     * <P>
     * 取首字母或全拼
     *
     * @param hanzi 汉字字符串
     * @param isFull 是否全拼 true:表示全拼 false表示：首字母
     *
     * @return 拼音
     */
    private static String convertHanzi2Pinyin(String hanzi,boolean isFull){
        /***
         * ^[\u2E80-\u9FFF]+$ 匹配所有东亚区的语言
         * ^[\u4E00-\u9FFF]+$ 匹配简体和繁体
         * ^[\u4E00-\u9FA5]+$ 匹配简体
         */
        String regExp="^[\u4E00-\u9FFF]+$";
        StringBuffer sb=new StringBuffer();
        if(hanzi==null||"".equals(hanzi.trim())){
            return "";
        }
        String pinyin="";
        for(int i=0;i<hanzi.length();i++){
            char unit=hanzi.charAt(i);
            //是汉字，则转拼音
            if(match(String.valueOf(unit),regExp)){
                pinyin=convertSingleHanzi2Pinyin(unit);
                if(isFull){
                    sb.append(pinyin);
                }
                else{
                    sb.append(pinyin.charAt(0));
                }
            }else{
                sb.append(unit);
            }
        }
        return sb.toString();
    }

    /**
     * 将单个汉字转成拼音
     *
     * @param hanzi 汉字字符
     *
     * @return 拼音
     */
    private static String convertSingleHanzi2Pinyin(char hanzi){
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String[] res;
        StringBuffer sb=new StringBuffer();
        try {
            res = PinyinHelper.toHanyuPinyinStringArray(hanzi,outputFormat);
            sb.append(res[0]);//对于多音字，只用第一个拼音
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return sb.toString();
    }

    /***
     * 匹配
     * <P>
     * 根据字符和正则表达式进行匹配
     *
     * @param str 源字符串
     * @param regex 正则表达式
     *
     * @return true：匹配成功  false：匹配失败
     */
    private static boolean match(String str,String regex){
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(str);
        return matcher.find();
    }

    /**
     * 生成无'-'的UUID
     * @return
     */
    public static String getUuid(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 生成ONUM
     * @return
     */
    public static String getOnum(){
        String dayTime = new SimpleDateFormat("MMdd").format(new Date());
        // 生成uuid的hashCode值
        int hashCode = UUID.randomUUID().toString().hashCode();
        if(hashCode < 0){
            hashCode = -hashCode;
        }
        // 5.算法处理: 0-代表前面补充0; 10-代表长度为10; d-代表参数为正数型
        return "11"+dayTime+ String.format("%010d", hashCode);
    }
}
