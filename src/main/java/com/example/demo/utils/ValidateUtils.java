package com.example.demo.utils;

import java.util.regex.Pattern;

/**
 * @author 团子等等俺
 * @date 2021-8-13
 */
public class ValidateUtils {

    private enum regs {
        mobileReg("^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}$"),
        fixedPhoneReg("(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +
                "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)"),
        postCodeReg("[1-9]\\d{5}"),
        emailReg("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"),
        internetURLReg(" [a-zA-z]+://[^\\s]*|" +
                "^http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$|" +
                "^https://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$"),
        dateReg("^\\d{4}-\\d{1,2}-\\d{1,2}"),
        moneyReg("^[1-9][0-9]*$|" +
                "^(0|[1-9][0-9]*)$|" +
                "^(0|-?[1-9][0-9]*)$|" +
                "^[0-9]+(.[0-9]+)?$|" +
                "^[0-9]+(.[0-9]{2})?$|" +
                "^[0-9]+(.[0-9]{1,2})?$|" +
                "^[0-9]{1,3}(,[0-9]{3})*(.[0-9]{1,2})?$|" +
                "^([0-9]+|[0-9]{1,3}(,[0-9]{3})*)(.[0-9]{1,2})?$"),
        ipAddressReg("\\d+\\.\\d+\\.\\d+\\.\\d+|" +
                "((?:(?:25[0-5]|2[0-4]\\\\d|[01]?\\\\d?\\\\d)\\\\.){3}(?:25[0-5]|2[0-4]\\\\d|[01]?\\\\d?\\\\d))"),
        QQNumberReg("[1-9][0-9]{4,}"),
        ChineseReg("^[\\u4e00-\\u9fa5]{0,}$"),
        EnglishOrNumberReg("^[A-Za-z0-9]+$|" +
                "^[A-Za-z0-9]{4,40}$");

        regs(final String reg) {
            this.regs = reg;
        }


        private final String regs;

        private String getRegs() {
            return this.regs;
        }


    }

    /**
     * 手机号
     *
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(regs.mobileReg.getRegs(), mobile);
    }

    /**
     * 区号+座机号码+分机号码
     *
     * @param fixedPhone
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isFixedPhone(String fixedPhone) {
        return Pattern.matches(regs.fixedPhoneReg.getRegs(), fixedPhone);
    }

    /**
     * 匹配中国邮政编码
     *
     * @param postCode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isPostCode(String postCode) {
        return Pattern.matches(regs.postCodeReg.getRegs(), postCode);
    }

    /**
     * 匹配邮箱格式
     *
     * @param email 邮箱
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(regs.emailReg.getRegs(), email);
    }

    /**
     * 匹配互联网链接格式
     *
     * @param internetURL 互联网链接
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isInternetURL(String internetURL) {
        return Pattern.matches(regs.internetURLReg.getRegs(), internetURL);
    }

    /**
     * 匹配时间格式
     *
     * @param date 时间格式
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isDate(String date) {
        return Pattern.matches(regs.dateReg.getRegs(), date);
    }

    /**
     * 匹配金钱格式
     *
     * @param money 时间格式
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isMoney(String money) {
        return Pattern.matches(regs.moneyReg.getRegs(), money);
    }

    /**
     * 匹配IP地址格式
     *
     * @param ipAddress 时间格式
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isIPAddress(String ipAddress) {
        return Pattern.matches(regs.ipAddressReg.getRegs(), ipAddress);
    }

    /**
     * 匹配QQ格式
     *
     * @param QQNumber 时间格式
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isQQNumber(String QQNumber) {
        return Pattern.matches(regs.QQNumberReg.getRegs(), QQNumber);
    }

    /**
     * 匹配是否为汉字格式
     *
     * @param Chinese 时间格式
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isChinese(String Chinese) {
        return Pattern.matches(regs.ChineseReg.getRegs(), Chinese);
    }

    /**
     * 匹配是否为字母或者数字
     *
     * @param EnglishOrNumber 字母或者数字格式
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isEnglishOrNumber(String EnglishOrNumber) {
        return Pattern.matches(regs.EnglishOrNumberReg.getRegs(), EnglishOrNumber);
    }


}
