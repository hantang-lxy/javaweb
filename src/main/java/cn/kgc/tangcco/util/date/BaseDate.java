package cn.kgc.tangcco.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 李昊哲
 */
public abstract class BaseDate {
    /**
     * 默认时间字符串模板
     */
    private final static String pattern = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式化
     * @param date	日期
     * @return		日期格式化后的字符串 格式为	yyyy-MM-dd HH:mm:ss
     */
    public static String getDateString(Date date) {
        return getDateString(date, pattern);
    }
    /**
     * 日期格式化
     * @param date    日期
     * @param pattern 格式样式
     * @return 日期格式化后的字符串
     */
    public static String getDateString(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
    /**
     *
     * @param source		被解析的日期字符串
     * @param pattern		解析格式
     * @return				解析后的日期
     * @throws ParseException	ParseException
     */
    public static Date parse(String source) throws ParseException {
        return new SimpleDateFormat(pattern).parse(source);
    }
    /**
     *
     * @param source		被解析的日期字符串
     * @param pattern		解析格式
     * @return				解析后的日期
     * @throws ParseException	ParseException
     */
    public static Date parse(String source, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(source);
    }
}
