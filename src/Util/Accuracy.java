package Util;

/**
 * The Accuracy class is a utility class that provides a single static field to manage the accuracy value used
 * in mathematical computations involving BigDecimal numbers.
 *
 * <p>BigDecimalUtil and other related classes use this class to get the current accuracy value. This class provides
 * methods to get and set the accuracy value, allowing users to control the precision of their calculations.</p>
 *
 * <p>Note: The default accuracy value is set to 10 decimal places.</p>
 *
 * <p>Usage Example:</p>
 * <pre>{@code
 * // Retrieve the current accuracy value
 * int currentAccuracy = Accuracy.getValue();
 *
 * // Set a new accuracy value
 * Accuracy.setValue(20);
 * }</pre>
 *
 * @since 1.0
 */
public abstract class Accuracy {
    private static int value = 10;

    /**
     * Retrieves the current accuracy value used in mathematical computations.
     *
     * @return The current accuracy value as an integer.
     */
    public static int getValue() {
        return value;
    }

    /**
     * Sets a new accuracy value to be used in mathematical computations.
     * The accuracy value represents the number of decimal places to consider in calculations.
     *
     * <p>Higher accuracy values may increase precision but may also impact performance.</p>
     *
     * @param newAccuracy The new accuracy value to be set.
     */
    public static void setValue(int newAccuracy) {
        value = newAccuracy;
    }
}

