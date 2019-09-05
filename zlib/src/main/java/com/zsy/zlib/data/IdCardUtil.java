package com.zsy.zlib.data;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * 省、直辖市代码表：
 * 11 : 北京  12 : 天津  13 : 河北 14 : 山西  15 : 内蒙古
 * 21 : 辽宁  22 : 吉林  23 : 黑龙江
 * 31 : 上海  32 : 江苏  33 : 浙江  34 : 安徽  35 : 福建   36 : 江西  37 : 山东
 * 41 : 河南  42 : 湖北  43 : 湖南  44 : 广东  45 : 广西   46 : 海南
 * 50 : 重庆  51 : 四川  52 : 贵州  53 : 云南  54 : 西藏
 * 61 : 陕西  62 : 甘肃  63 : 青海  64 : 宁夏  65 : 新疆
 * 71 : 台湾
 * 81 : 香港  82 : 澳门
 * 91 : 国外
 * 前17位分别乘特定系数相加/11 余数为最后一位
 * 0 1 2 3 4 5 6 7 8 9 10
 * 1 0 X 9 8 7 6 5 4 3 2
 * <p>
 * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
 */
public class IdCardUtil {

    //城市ID
    private static String[] cityCode = {
            "11", "12", "13", "14", "15",
            "21", "22", "23",
            "31", "32", "33", "34", "35", "36", "37",
            "41", "42", "43", "44", "45", "46",
            "50", "51", "52", "53", "54",
            "61", "62", "63", "64", "65",
            "71",
            "81", "82",
            "91"};
    //每位加权因子
    private static int power[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    //最后一位校验码
    private static String[] verifyCode = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};

    /**
     * 验证所有的身份证的合法性
     */
    public static boolean isValidate(String idcard) {
        if (idcard == null || "".equals(idcard)) {
            return false;
        }
        if (idcard.length() == 15) {
            return checkValid(idcard, 15);
        }
        if (idcard.length() == 18) {
            return validate18Idcard(idcard);
        }
        return false;
    }

    /**
     * 获取地区信息
     * Properties prop = new Properties();
     * FileInputStream fileInputStream = new FileInputStream("D:/region.properties");
     * BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
     * prop.load(bufferedReader);
     * System.out.println(prop.getProperty("110101"));
     */
    public static String getRegion(Context context, String idcard) {
        if (idcard == null || idcard.length() < 6) {
            return null;
        }
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("region.properties");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            properties.load(bufferedReader);
            String property = properties.getProperty(idcard.substring(0, 6));
            return property;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证18位的正确判断
     */
    public static boolean validate18Idcard(String idcard) {
        if (!checkValid(idcard, 18)) {
            return false;
        }
        String idcard18Code = idcard.substring(17, 18);
        String idcard17 = idcard.substring(0, 17);
        char chars[] = idcard17.toCharArray();
        int bit[] = converCharToInt(chars);
        int sum17 = getPowerSum(bit);
        return idcard18Code.equalsIgnoreCase(verifyCode[sum17 % 11]);
    }

    /**
     * 将15位的身份证转成18位身份证
     */
    public static String convertTo18(String idcard) {
        if (!checkValid(idcard, 15)) {
            return null;
        }
        String birthday = idcard.substring(6, 12);
        String completeDate = getCompleteDate(birthday);
        String idcard17 = idcard.substring(0, 6) + completeDate + idcard.substring(12, 15);
        char chars[] = idcard17.toCharArray();
        int bit[] = converCharToInt(chars);
        int sum17 = getPowerSum(bit);
        return idcard17 + verifyCode[sum17 % 11];
    }

    /**
     * 验证格式是否正确(及15位的正确判断)
     */
    private static boolean checkValid(String idcard, int length) {
        if (idcard == null || (idcard.length() != 15 & idcard.length() != 18) || !idcard.matches("^[0-9]*$")) {
            return false;
        }
        if (!checkProvinceid(idcard.substring(0, 2))) {
            return false;
        }
        boolean b = idcard.length() == 18;
        String birthday = idcard.substring(6, b ? 14 : 12);
        SimpleDateFormat sdf = new SimpleDateFormat(b ? "yyyyMMdd" : "yyMMdd");
        try {
            Date birthDate = sdf.parse(birthday);
            String tmpDate = sdf.format(birthDate);
            if (!tmpDate.equals(birthday)) {
                return false;
            }
        } catch (ParseException e1) {
            return false;
        }
        return true;
    }

    /**
     * 转换为完整的日期
     */
    private static String getCompleteDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        Date parse = null;
        try {
            parse = sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
        Calendar cday = Calendar.getInstance();
        cday.setTime(parse);
        int year = cday.get(Calendar.YEAR);
        return String.valueOf(year) + date.substring(2);
    }

    /**
     * 校验省份
     */
    private static boolean checkProvinceid(String provinceid) {
        for (String id : cityCode) {
            if (id.equals(provinceid)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     */
    private static int getPowerSum(int[] bit) {
        int sum = 0;
        if (power.length != bit.length) {
            return sum;
        }
        for (int i = 0; i < bit.length; i++) {
            for (int j = 0; j < power.length; j++) {
                if (i == j) {
                    sum = sum + bit[i] * power[j];
                }
            }
        }
        return sum;
    }

    /**
     * 将字符数组转为整型数组
     */
    private static int[] converCharToInt(char[] c) throws NumberFormatException {
        int[] a = new int[c.length];
        int k = 0;
        for (char temp : c) {
            a[k++] = Integer.parseInt(String.valueOf(temp));
        }
        return a;
    }

    /**
     * 计算隐藏生日的身份证可能的号码
     */
    public static List<String> restoreIdcard(String idCard) {
        if (idCard == null || idCard.length() != 18 || !idCard.matches("^[0-9]{10}[*]{4}[0-9]{3}[0-9xX]{1}$")) {
            return null;
        }
        List<String> list = new ArrayList<>();
        int year = Integer.parseInt(idCard.substring(6, 10));
        Calendar calendar = new GregorianCalendar();//定义一个日历，变量作为年初
        Calendar calendarEnd = new GregorianCalendar();//定义一个日历，变量作为年末
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);//设置年初的日期为1月1日
        calendarEnd.set(Calendar.YEAR, year);
        calendarEnd.set(Calendar.MONTH, 11);
        calendarEnd.set(Calendar.DAY_OF_MONTH, 31);//设置年末的日期为12月31日
        String idcard18Code = idCard.substring(17, 18);
        SimpleDateFormat sf = new SimpleDateFormat("MMdd");
        while (calendar.getTime().getTime() <= calendarEnd.getTime().getTime()) {//用一整年的日期循环
            String format = sf.format(calendar.getTime());
            String idcard17 = idCard.substring(0, 10) + format + idCard.substring(14, 17);
            char chars[] = idcard17.toCharArray();
            int bit[] = converCharToInt(chars);
            int sum17 = getPowerSum(bit);
            String s = verifyCode[sum17 % 11];
            if (s.equals(idcard18Code)) {
                list.add(idcard17 + idcard18Code);
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);//日期+1
        }
        return list;
    }

    /**
     * @param args 230882198810183513
     */
    public static void main(String[] args) {
    }


}