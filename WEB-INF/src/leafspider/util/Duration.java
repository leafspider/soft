package leafspider.util;

public class Duration {

    public static float secs(long startMillis, long endMillis) {
        float diff = endMillis - startMillis;
		return diff/1000;
    }

    public static float mins(long startMillis, long endMillis) {
        float diff = endMillis - startMillis;
        return diff/(1000*60);
    }

    public static float hours(long startMillis, long endMillis) {
        float diff = endMillis - startMillis;
        return diff/(1000*60*60);
    }

    public static float days(long startMillis, long endMillis) {
        float diff = endMillis - startMillis;
        return diff/(1000*60*60*24);
    }

    public static float weeks(long startMillis, long endMillis) {
        float diff = endMillis - startMillis;
        return diff/(1000*60*60*24*7);
    }

}
