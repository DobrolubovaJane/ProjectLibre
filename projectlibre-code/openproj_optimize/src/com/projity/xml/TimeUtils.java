package xml;

public class TimeUtils {
    private TimeUtils(){}

    public static double getTimeInHours(String str) {
            /*PT32H0M0S*/
            System.out.println("str "+str);
        if (str == "" || str == null) {
            return 0.0;
        }
        String hoursStr = str.substring(str.indexOf('T') + 1, str.indexOf('H'));
        String minStr = str.substring(str.indexOf('H')+1, str.indexOf('M'));
        String secStr = str.substring(str.indexOf('M')+1, str.indexOf('S'));

        double time = Double.valueOf(hoursStr) + Double.valueOf(minStr)/60 + Double.valueOf(secStr)/360;
        return time;

    }

    public static String getTimeForXML(double time) {
            /*PT32H0M0S*/
        int hours = (int) time;
        time -= hours;
        int minutes = (int) (time * 60) ;
        time = time * 60 % 1;
        int seconds = (int) (time * 60) ;
        return "PT" + hours+ "H" + minutes + "M" + seconds + "S";

    }
}
