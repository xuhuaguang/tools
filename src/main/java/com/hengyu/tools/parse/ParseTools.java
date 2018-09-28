package com.hengyu.tools.parse;

import java.text.DecimalFormat;

/**
 * 转换工具类，各个类型之间的转换
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/6/8
 * Time：21:05
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
public class ParseTools
{
    /**
     * 将object转换为string类型 <br>
     *
     * @param obj
     *            未被转换的值
     * @return String 转换后的值
     */
    public static String toString(Object obj) {
        return obj.toString().trim();
    }

    /**
     * 将string类型转换为double类型
     *
     * @param obj 未被转换的值
     * @throws NumberFormatException
     * @return Double 转换后的值
     */
    public static Double toDouble(String obj) throws NumberFormatException {
        return Double.valueOf(obj);
    }

    /**
     * 将object类型的值转换为double <br>
     *
     * @param obj
     *            未被转换的值
     * @throws NumberFormatException
     * @return Double 转换后的值
     */
    public static Double toDouble(Object obj) throws NumberFormatException {
        return Double.valueOf(toString(obj));
    }

    /**
     * 将double类型格式化成0.000保留三位小数点
     *
     * @param val 未被格式化的值
     * @return
     * @return Double 格式化后的值
     */
    public static Double toDouble(Double val) {
        if (val != null) {
            DecimalFormat format = new DecimalFormat("#0.000");
            return Double.valueOf(Double.parseDouble(format.format(val)));
        }
        return new Double(0.0D);
    }

    /**
     * 将double类型格式化，根据传入pattern进行格式化
     * @param val
     * @return
     */
    public static String toDouble(Double val,String pattern)
    {
        if (val != null) {
            DecimalFormat format = new DecimalFormat(pattern);
            return format.format(val);
        }
        return "0";
    }

    /**
     * 将object类型的值转换为float <br>
     * 2016年12月23日 上午10:22:02 toFloat
     *
     * @param obj
     *            未被转换的值
     * @return Float 转换后的值
     */
    public static Float toFloat(Object obj) {
        return Float.valueOf(Float.parseFloat(toString(obj)));
    }

    /**
     * 将object类型的值转换为integer <br>
     * 2016年12月23日 上午10:22:41 toInteger
     *
     * @param obj 未被转换的值
     * @throws NumberFormatException
     * @return Integer 转换后的值
     */
    public static Integer toInteger(Object obj) throws NumberFormatException {
        return Integer.valueOf(toString(obj));
    }

    /**
     * 将数组转换为字符串,格式化后字符串：str1,str2,str3<br>
     * 2016年12月23日 上午10:23:14 array2String
     *
     * @param objs
     * @return
     * @return String
     */
    public static String arrayToString(Object[] objs) {
        String str = "";
        for (int i = 0; i < objs.length; i++) {
            str = str + objs[i] + (i == objs.length - 1 ? "" : ",");
        }
        return str;
    }

    /**
     * 将string字符串转换根据pattern分割成数组 <br>
     * 2016年12月23日 上午10:24:57 toArray
     *
     * @param obj 未被分割的数组
     * @param pattern 分割的标识
     * @return String[] 格式化后的数组
     */
    public static String[] stringtoArray(String obj, String pattern) {
        return obj.toString().split(pattern);
    }
}
