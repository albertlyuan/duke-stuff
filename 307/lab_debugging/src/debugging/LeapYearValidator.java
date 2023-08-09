package debugging;


/**
 * Class that implements check if the given year is a leap year or not according to the rules from:
 *   http://www.timeanddate.com/date/leapyear.html
 *
 *   <ol>
 *     <li>Every year divisible by 4 is a leap year</li>
 *     <li>But every year divisible by 100 is NOT a leap year</li>
 *     <li>Unless the year is also divisible by 400, then it is still a leap year</li>
 *   </ol>
 */
public class LeapYearValidator {
    // TODO: find and fix the bug in this method
    public boolean isLeapYear (int year) {
        if (year % 400 == 0) {
            return true;
        }
        return (year % 100 != 0 && year % 4 == 0);
    }


    public static void main (String[] args) {
        LeapYearValidator lyv = new LeapYearValidator();

        System.out.println("Expected true, got: " + lyv.isLeapYear(2020));
        System.out.println("Expected true, got: " + lyv.isLeapYear(2000));
        System.out.println("Expected false, got: " + lyv.isLeapYear(1900));
    }
}
