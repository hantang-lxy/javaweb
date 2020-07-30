package cn.kgc.tangcco.util.localdatetime;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author 李昊哲
 */
public abstract class BaseLocalDateTime {
    /**
     * 时间格式化模板字符串
     */
    private static final String formatter = "yyyy-MM-dd HH:mm:ss";
    /**
     * 获取默认时间格式化模板
     *
     * @return 默认时间格式化模板
     * @param formatter
     */
    public static DateTimeFormatter getPattern() {
        return getPattern(formatter);
    }
    /**
     * 获取时间格式化模板
     *
     * @return 时间格式化模板
     */
    public static DateTimeFormatter getPattern(String formatter) {
        return DateTimeFormatter.ofPattern(formatter);
    }
    /**
     * 获取当前北京时间的默认格式化后的字符串
     *
     * @return 当前北京时间的默认格式化后的字符串
     */
    public static String getStringByCurrentLocalDateTime() {
        return getStringByCurrentLocalDateTime(formatter);
    }

    /**
     * 获取当前北京时间的格式化后的字符串
     *
     * @param formatter 格式化字符串
     * @return 当前北京时间的格式化后的字符串
     */
    public static String getStringByCurrentLocalDateTime(String formatter) {
        return LocalDateTime.now(ZoneOffset.of("+8")).format(getPattern(formatter));
    }

    /**
     * 根据传入的字符串解析为LocalDateTime
     *
     * @param text 传入的时间格式字符串
     * @return 按照默认模板解析后的LocalDateTime
     */
    public static LocalDateTime getLocalDateTimeByString(String text) {
        return getLocalDateTimeByString(text,formatter);
    }

    /**
     * 根据传入的字符串解析为LocalDateTime
     *
     * @param text      传入的时间格式字符串
     * @param formatter 格式化字符串
     * @return 解析后的LocalDateTime
     */
    public static LocalDateTime getLocalDateTimeByString(String text, String formatter) {
        return LocalDateTime.parse(text, getPattern(formatter));
    }
    /**
     * 将LocalDateTime转换成Date
     * @param localDateTime	LocalDateTime
     * @return	Date        Date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));
    }
    /**
     * 将Date转换成LocalDateTime
     * @param date	Date        日期
     * @return	LocalDateTime   LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
    }

    /**
     * 将时间毫秒数解析为LocalDateTime
     * @param time  时间毫秒数
     * @return      LocalDateTime
     */
    public static LocalDateTime millisToLocalDateTime(long time) {
        return new Date(time).toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
    }
}
