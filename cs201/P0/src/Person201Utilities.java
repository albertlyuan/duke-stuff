public class Person201Utilities {
    public static double distance(Person201 a, Person201 b) {
        double alat = a.getLatitude();
        double along = a.getLongitude();
        double blat = b.getLatitude();
        double blong = b.getLongitude();
        double diffInLat = alat - blat;
        double diffInLong = along - blong;
        return Math.sqrt(Math.pow(diffInLat, 2.0) + Math.pow(diffInLong, 2.0));
    }
    // code taken from https://rosettacode.org/wiki/Haversine_formula#Java

}
