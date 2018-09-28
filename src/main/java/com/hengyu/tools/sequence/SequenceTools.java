package com.hengyu.tools.sequence;

import java.text.*;
import java.util.Calendar;

/**
 * 根据时间生成唯一序列ID<br>
 * 时间精确到秒，ID最大值为99999且循环使用
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/6/8
 * Time：21:32
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
public class SequenceTools {
    private static final FieldPosition HELPER_POSITION = new FieldPosition(0);

    /** 时间：精确到秒 */
    private final static Format dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");

    private final static NumberFormat numberFormat = new DecimalFormat("00000");

    private static int seq = 0;

    private static final int MAX = 99999;

    public static synchronized String generateSequenceNo() {

        Calendar rightNow = Calendar.getInstance();

        StringBuffer sb = new StringBuffer();

        dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);

        numberFormat.format(seq, sb, HELPER_POSITION);

        if (seq == MAX) {
            seq = 0;
        } else {
            seq++;
        }

        return sb.toString();
    }

    public static void main(String[] args)
    {
        //2017060821323900000
        //2017060821325600000
        //2017060821330800000
        System.out.println(generateSequenceNo());
    }
}
