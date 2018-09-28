package com.hengyu.tools.math;

import com.hengyu.tools.parse.ParseTools;
import com.hengyu.tools.validate.ValidateTools;

/**
 * 数量转换工具
 * ===============================
 * Created with IntelliJ IDEA.
 * User：于起宇
 * Date：2017/8/7
 * Time：15:55
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class CountTools
{
    /**
     * 格式化数值
     * @param value
     * @return
     */
    public static String formatter(long value)
    {
        //格式化后的值
        String formatter_value = ParseTools.toString(value);
        CountUnitEnum countUnitEnum = null;
        /**
         * 万-十万格式化
         */
        if(CountUnitEnum.WAN.getValue() <= value && value < CountUnitEnum.SWAN.getValue())
        {
            countUnitEnum = CountUnitEnum.WAN;
        }
        /**
         * 十万-百万格式化
         */
        else if(CountUnitEnum.SWAN.getValue() <= value && value < CountUnitEnum.BWAN.getValue())
        {
            countUnitEnum = CountUnitEnum.SWAN;
        }
        /**
         * 百万-千万格式化
         */
        else if(CountUnitEnum.BWAN.getValue() <= value && value < CountUnitEnum.QWAN.getValue())
        {
            countUnitEnum = CountUnitEnum.BWAN;
        }
        /**
         * 百万-千万格式化
         */
        else if(CountUnitEnum.QWAN.getValue() <= value && value < CountUnitEnum.YI.getValue())
        {
            countUnitEnum = CountUnitEnum.QWAN;
        }
        /**
         * 亿转换
         */
        else if(value > CountUnitEnum.YI.getValue())
        {
            countUnitEnum = CountUnitEnum.YI;
        }
        return ValidateTools.isEmpty(countUnitEnum) ? formatter_value : ParseTools.toDouble(value / ParseTools.toDouble(countUnitEnum.getValue()),"#.0") + countUnitEnum.getUnit();
    }

    /**
     * 转换单位枚举
     */
    enum CountUnitEnum
    {
        WAN(10000,"万"),
        SWAN(100000,"十万"),
        BWAN(1000000,"百万"),
        QWAN(10000000,"千万"),
        YI(100000000,"亿"),;

        //单位比例
        private int value;
        //单位名称
        private String unit;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        CountUnitEnum(int value, String unit) {
            this.value = value;
            this.unit = unit;
        }
    }
}
