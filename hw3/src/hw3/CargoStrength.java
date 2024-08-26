package hw3;

/**
 * This enumeration contains the accepted CargoStrength values with their
 * assigned final integer value and a single character identifier
 * @author Jason Chen
 *      112515450
 *      Recitation 4
 *      Homework 3
 */
public enum CargoStrength {
    FRAGILE(1, 'F'),
    MODERATE(2, 'M'),
    STURDY(3, 'S');
    final int strengthLevel;
    final char abbrev;

    /**
     * This constructor assists with assigning an integer strength level and
     * character abbreviation to the CargoStrength
     * @param strengthLevel
     *      Strength level of the enumeration
     * @param abbrev
     *      Abbreviation of the enumeration
     */
    CargoStrength(int strengthLevel, char abbrev){
        this.strengthLevel = strengthLevel;
        this.abbrev = abbrev;
    }
}