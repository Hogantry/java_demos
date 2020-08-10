package com.dfz.datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

/**
 * @version V1.0
 * @author: DFZ
 * @description: java8之后的时间API
 * @date: 2020/6/12 15:40
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class DateTimeApplication {

    public static void main(String[] args) {

//        Clock
//        Instant
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();
        System.out.println(millis);
        Instant instant = clock.instant();
        System.out.println(instant);
        Date legacyDate = Date.from(instant);
        System.out.println(legacyDate);

//        ZoneId
        // 输出所有区域标识符
        System.out.println(ZoneId.getAvailableZoneIds());
        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");
        // ZoneRules[currentStandardOffset=+01:00]
        System.out.println(zone1.getRules());
        // ZoneRules[currentStandardOffset=-03:00]
        System.out.println(zone2.getRules());

//        LocalTime
        LocalTime now1 = LocalTime.now(zone1);
        LocalTime now2 = LocalTime.now(zone2);
        // false
        System.out.println(now1.isBefore(now2));
        long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
        long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);
        // -4
        System.out.println(hoursBetween);
        // -299
        System.out.println(minutesBetween);
        LocalTime late = LocalTime.of(23, 59, 59);
        System.out.println(late);
        DateTimeFormatter germanFormatter =
                DateTimeFormatter
                        .ofLocalizedTime(FormatStyle.SHORT)
                        .withLocale(Locale.GERMAN);

        LocalTime leetTime = LocalTime.parse("13:37", germanFormatter);
        System.out.println(leetTime);

//        LocalDate
        //获取现在的日期
        LocalDate today = LocalDate.now();
        System.out.println("今天的日期: " + today);
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        System.out.println("明天的日期: " + tomorrow);
        LocalDate abc = tomorrow.minusDays(1);
        System.out.println("abc的日期: " + abc);
        LocalDate yesterday = tomorrow.minusDays(2);
        System.out.println("昨天的日期: " + yesterday);
        LocalDate independenceDay = LocalDate.of(2019, Month.MARCH, 12);
        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
        System.out.println("今天是周几:" + dayOfWeek);
        String str1 = "2014==04==12 01时06分09秒";
        // 根据需要解析的日期、时间字符串定义解析所用的格式器
        DateTimeFormatter fomatter1 = DateTimeFormatter
                .ofPattern("yyyy==MM==dd HH时mm分ss秒");
        LocalDateTime dt1 = LocalDateTime.parse(str1, fomatter1);
        System.out.println(dt1);

        String str2 = "2014$$$四月$$$13 20小时";
        DateTimeFormatter fomatter2 = DateTimeFormatter
                .ofPattern("yyy$$$MMM$$$dd HH小时");
        LocalDateTime dt2 = LocalDateTime.parse(str2, fomatter2);
        System.out.println(dt2);
        LocalDateTime rightNow = LocalDateTime.now();
        String date = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(rightNow);
        System.out.println(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        System.out.println(formatter.format(rightNow));

//        LocalDateTime
        LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);

        DayOfWeek dayOfWeek2 = sylvester.getDayOfWeek();
        System.out.println(dayOfWeek2);

        Month month = sylvester.getMonth();
        System.out.println(month);

        long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
        System.out.println(minuteOfDay);
        Instant instant2 = sylvester
                .atZone(ZoneId.systemDefault())
                .toInstant();

        Date legacyDate2 = Date.from(instant2);
        System.out.println(legacyDate2);
        DateTimeFormatter formatter2 =
                DateTimeFormatter
                        .ofPattern("MMM dd, yyyy - HH:mm");
        LocalDateTime parsed = LocalDateTime.parse("Nov 03, 2014 - 07:13", formatter2);
        String string = formatter2.format(parsed);
        System.out.println(string);
    }

}
