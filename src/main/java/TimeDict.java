import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;

/**
 * @author pdn
 */
public class TimeDict {

    public static void main(String[] args) throws ParseException {

        LocalDate start = LocalDate.of(2010, 1, 1);
        LocalDate end = LocalDate.of(9999, 12, 31);


        //     总的天数
        long allDayCount = end.toEpochDay() - start.toEpochDay();

        for (int i = 0; i < allDayCount; i++) {

            //  今天属于今年的第几周
            WeekFields weekFields = WeekFields.ISO;
            int weekNumber = start.get(weekFields.weekOfWeekBasedYear());

//  当前天属于周几
            int whichDay = start.getDayOfWeek().getValue();

//        当前天所在周的周一
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String dateString = start.getYear() + "-" + start.getMonth().getValue() + "-" + start.getDayOfMonth();
            Date date = sdf.parse(dateString);
            String thisWeekMonday = sdf.format(getThisWeekMonday(date));

            // 当前天所在周的周日
            String[] split = thisWeekMonday.split("-");
            LocalDate of = LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
            LocalDate sunday = of.plus(6, ChronoUnit.DAYS);

//            当前天是否是周末
            boolean isWeekEnd = start.toString().equals(sunday.toString());
            int isOrNotWeekEnd = 0;
            if (isWeekEnd) {
                isOrNotWeekEnd = 1;
            }


//       当前天属于第几个月
            int month = start.getMonth().getValue();

            //获取月初
            LocalDate monthOfFirstDate = LocalDate.parse(start.toString(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")).with(TemporalAdjusters.firstDayOfMonth());
            //获取月末
            LocalDate monthOfLastDate = LocalDate.parse(start.toString(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")).with(TemporalAdjusters.lastDayOfMonth());


            int year = start.getYear();


            rwFile(start.toString() + "," + weekNumber + "," + whichDay + "," + thisWeekMonday
                    + "," + sunday
                    + "," + isOrNotWeekEnd
                    + "," + month + "," +
                    monthOfFirstDate + "," + monthOfLastDate + "," + year);

            start = start.plus(1, ChronoUnit.DAYS);

        }
    }


    public static void rwFile(String line) {

        FileWriter fw = null;
        //路径一定要用"\\"
        try {
            fw = new FileWriter("C:\\DateDictVersionTwo.txt", true);
            //这里向文件中输入结果123

            fw.write(line + "\n");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }
}
