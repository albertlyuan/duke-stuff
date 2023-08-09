package debugging;


/**
 * Class that implements an algorithm to validate a credit card number using a checksum algorithm:
 *   <ul>
 *     <li>If k is even, add the k<sup>th</sup> digit to the sum.</li>
 *     <li>If k is odd, multiply the k<sup>th</sup> digit by two. If the result is greater than or equal to 10, subtract 9. Add this computed value to the sum.</li>
 *   </ul>
 *
 * If the resulting sum is divisible by 10, the credit card number passes the checksum test, otherwise the card number is not valid.
 */
public class CreditCardValidator {
    // TODO: find and fix the bug in this method
    public boolean isValid (long ccard) {
        int numLength = (int)(Math.log10(ccard) + 1); // NOTE: this line is correct
        int sum = 0;
        for (int k = numLength-1; k >= 0; k--) {
            long digit = ccard % 10;
            if (k % 2 != 0) {
                digit *= 2;
                if (digit >= 10) {
                    digit -= 9;
                }
            }
            sum += digit;
            ccard /= 10;
        }
        return (sum % 10 == 0);
    }

    public static void main (String[] args) {
        CreditCardValidator ccv = new CreditCardValidator();

        System.out.println("Expected false, got: " + ccv.isValid(254321098765432L));
        System.out.println("Expected true, got: " + ccv.isValid(2543210987654321L));
        System.out.println("Expected true, got: " + ccv.isValid(636363636363636L));
    }
}
