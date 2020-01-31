import javax.swing.*;
/**
 * Class EinfachEingabe - input class for input of simple input types
 * via simple dialog box.
 * eg. int, char, String,float or boolean.
 *
 * @author Bruce Quig
 * @author Michael Kolling
 * @author Eugene Ageenko
 *
 * @version 1.2
 * Modified (Mar 19, 2003): methods converted to static, constructor is hidden.
 * Major corrections in behaviour. Support default value for the input.
 * 
 * Translated by s_hansse(September 30th,2009)
 * http://cojobo.net/~s_hansse
 * 
 * Data type Double added by Lukas Breuer(November 22th, 2019)
 * https://cojobo.net/~l.breuer/
 */
public class EinfachEingabe {
    // instance variables
    static final String STRING_TITLE = "Bitte einen String eingeben";
    static final String CHAR_TITLE = "Bitte ein Char eingeben";
    static final String INT_TITLE = "Bitte ein Int eingeben";
    static final String BOOLEAN_TITLE = "Waehle wahr oder falsch";
    static final String FLOAT_TITLE = "Bitte ein Float eingeben";
    static final String DOUBLE_TITLE = "Bitte ein Double eingeben";
    static final String TRUE = "Wahr";
    static final String FALSE = "Falsch";
    static final String EMPTY_STRING = "";
    /**
     *  No constructor by default.
     */
    private EinfachEingabe() {
    }
    /**
     ** String input from the user via a simple dialog.
     ** @param prompt the message string to be displayed inside dialog
     ** @return String input from the user.
     **/
    public static String getString(String prompt) {
        return getString(prompt,"");
    }
    /**
     ** String input from the user via a simple dialog.
     ** @param prompt the message string to be displayed inside dialog
     ** @param initialValue input string that is initially displayed as selected by the user
     ** @return String input from the user.
     **/
    public static String getString(String prompt, String initialValue) {
        Object[] commentArray = {prompt, EMPTY_STRING, EMPTY_STRING};
        Object[] options = {"OK"};
        boolean validResponse = false;
        String result = null;
        while (!validResponse) {
            final JOptionPane optionPane = new JOptionPane(commentArray,
                                                           JOptionPane.QUESTION_MESSAGE,
                                                           JOptionPane.OK_CANCEL_OPTION,
                                                           null,
                                                           options,
                                                           options[0]);
            optionPane.setWantsInput(true);
            optionPane.setInitialSelectionValue(initialValue);  // EA: added
            JDialog dialog = optionPane.createDialog(null, STRING_TITLE);
            dialog.pack();
            dialog.setVisible(true);
            Object response = optionPane.getInputValue();
            if (response != JOptionPane.UNINITIALIZED_VALUE) {
                result = (String) response;
                if (result != null) // EA: added for completnes
                    validResponse = true;
                else {
                    commentArray[1] = "Ungueltig : ";
                    commentArray[2] = "Du musst einen gueltigen String eingeben";
                }
            } else {
                commentArray[1] = "Du must einen String eingeben";
                commentArray[2] = EMPTY_STRING;
            }
        }
        return result;
    }
    /**
     ** returns character input from the user via a simple dialog.
     ** @param prompt the message string to be displayed inside dialog
     ** @return the input character
     **/
    public static char getChar(String prompt) {
        return getChar(prompt,"");
    }
    /**
     ** returns character input from the user via a simple dialog.
     ** @param prompt the message string to be displayed inside dialog
     ** @param initialValue input value that is initially displayed as selected by the user
     ** @return the input character
     **/
    public static char getChar(String prompt, char initialValue) {
        return getChar(prompt,Character.toString(initialValue));
    }
    /**
     ** returns character input from the user via a simple dialog.
     ** @param prompt the message string to be displayed inside dialog
     ** @param initialValue input string that is initially displayed as selected by the user
     ** @return the input character
     **/
    public static char getChar(String prompt, String initialValue) {
        char response = (initialValue != null) ? initialValue.charAt(0) : '-'; // EA: modified
        String result = null;
        Object[] commentArray = {prompt, EMPTY_STRING, EMPTY_STRING};
        Object[] options = {"OK"};
        boolean validResponse = false;
        while (!validResponse) {
            final JOptionPane optionPane = new JOptionPane(commentArray,
                                                           JOptionPane.QUESTION_MESSAGE,
                                                           JOptionPane.OK_CANCEL_OPTION,
                                                           null,
                                                           options,
                                                           options[0]);
            optionPane.setWantsInput(true);
            optionPane.setInitialSelectionValue(initialValue);  // EA: added
            JDialog dialog = optionPane.createDialog(null, CHAR_TITLE);
            dialog.pack();
            dialog.setVisible(true);
            result = null; // EA: added for convinience;
            // EA: why character processed in another way that integer?
            // EA: meaning that with check for uinitialized case then assignment?
            Object input = optionPane.getInputValue();
            if (input != JOptionPane.UNINITIALIZED_VALUE) {
                result = (String) input;
                if (result != null) {
                    if (result.length() == 1) {
                        response = result.charAt(0);
                        validResponse = true;
                    } else {
                        commentArray[1] = "Ungueltig : " + result;
                        commentArray[2] = "Du musst einen gueltigen Buchstaben eingeben";
                    }
                } else {
                    commentArray[1] = "Ungueltig"; // EA: corrected, no point to print null-object. Question: when it is possible to have null objects?
                    commentArray[2] = "Du darfst nur einen Buchstaben eingeben";
                }
            } else {
                commentArray[1] = "Du darfst nur einen Buchstaben eingeben";  //EA: error corrected, result removed
                commentArray[2] = EMPTY_STRING; //EA: cannot use result since it is not initialized
            }
        }
        return response;
    }
    /**
     ** boolean selection from the user via a simple dialog.
     ** @param  prompt message to appear in dialog
     ** @param  trueText message to appear on true "button"
     ** @param  falseText message to appear on "false" button
     ** @return boolean selection from the user
     **/
    public static boolean getBoolean(String prompt, String trueText, String falseText) {
        Object[] commentArray = {prompt, EMPTY_STRING};
        boolean validResponse = false;
        int result = -1;
        while (!validResponse) {
            Object[] options = {trueText, falseText};
            result = JOptionPane.showOptionDialog(null,
                                                  commentArray,
                                                  BOOLEAN_TITLE,
                                                  JOptionPane.YES_NO_OPTION,
                                                  JOptionPane.QUESTION_MESSAGE,
                                                  null, //don't use a custom Icon
                                                  options, //the titles of buttons
                                                  trueText); //the title of the default button, EA: CORRECTED from TRUE
            // check true or false buttons pressed
            if (result == JOptionPane.YES_OPTION || result == JOptionPane.NO_OPTION) // CORRECTED from 0:1
            {
                validResponse = true;
            } else {
                commentArray[1] = "Ungueltige Wahl: Du musst Wahr oder Falsch waehlen";
            }
        }
        return (result == 0);
    }
    /**
     ** boolean selection from the user via a simple dialog.
     ** @param  prompt message to appear in dialog
     ** @return boolean selection from the user
     **/
    public static boolean getBoolean(String prompt) {
        return getBoolean(prompt, TRUE, FALSE);
    }
    /**
     ** returns integer input from the user via a simple dialog.
     ** @param prompt the message string to be displayed inside dialog
     ** @return the input integer
     */
    public static int getInt(String prompt) {
        return getInt(prompt,"");
    }
    /**
     ** returns integer input from the user via a simple dialog.
     ** @param prompt the message string to be displayed inside dialog
     ** @param initialValue input value that is initially displayed as selected by the user
     ** @return the input integer
     **/
    public static int getInt(String prompt, int initialValue) {
        return getInt(prompt,Integer.toString(initialValue));
    }
    /**
     ** returns integer input from the user via a simple dialog.
     ** @param prompt the message string to be displayed inside dialog
     ** @param initialValue input string that is initially displayed as selected by the user
     ** @return the input integer
     **/
    public static int getInt(String prompt, String initialValue) {
        Object[] commentArray = {prompt, EMPTY_STRING, EMPTY_STRING};
        Object[] options = {"OK"};
        boolean validResponse = false;
        int response = 0;
        while (!validResponse) {
            final JOptionPane optionPane = new JOptionPane(commentArray,
                                                           JOptionPane.QUESTION_MESSAGE,
                                                           JOptionPane.OK_CANCEL_OPTION,
                                                           null,
                                                           options,
                                                           options[0]);
            optionPane.setWantsInput(true);
            optionPane.setInitialSelectionValue(initialValue);  // EA: added
            JDialog dialog = optionPane.createDialog(null, INT_TITLE);
            dialog.pack();
            dialog.setVisible(true);
            // EA: rewritten as in getChar function
            // EA: added or corrected non-portable check for uninitialized value situation
            Object input = optionPane.getInputValue();
            if (input == JOptionPane.UNINITIALIZED_VALUE) {
                commentArray[1] = "Du musst eine Zahl eingeben"; // EA: explanatory text added
                commentArray[2] = EMPTY_STRING;
            } else {
                String result = (String) input;
                if (result == null) { // EA: added for completnes, but is this situation possible?
                    commentArray[1] = "Ungueltig:";
                    commentArray[2] = "Du musst eine gueltige Zahl eingeben";
                } else {
                    try {
                        //workaround for BlueJ bug - misses first exception after compilation
                        //response = Integer.parseInt(result); // EA: ?
                        response = Integer.parseInt(result);
                        validResponse = true;
                    } catch (NumberFormatException exception) {
                        commentArray[1] = "Ungueltig: " + result;
                        commentArray[2] = "Du musst eine gueltige Zahl eingeben";
                        initialValue = result; // EA: added
                    }
                }
            }
        }
        return response;
    }
    /**
     ** returns a float input from the user via a simple dialog.
     ** @param prompt the message string to be displayed inside dialog
     ** @return the input float
     **/
    public static float getFloat(String prompt) {
        return getFloat(prompt,"");
    }
    /**
     ** returns float input from the user via a simple dialog.
     ** @param prompt the message string to be displayed inside dialog
     ** @param initialValue input value that is initially displayed as selected by the user
     ** @return the input float
     **/
    public static float getFloat(String prompt, float initialValue) {
        return getFloat(prompt,Float.toString(initialValue));
    }
    /**
     ** returns float input from the user via a simple dialog.
     ** @param prompt the message string to be displayed inside dialog
     ** @param initialValue input string that is initially displayed as selected by the user
     ** @return the input float
     **/
    public static float getFloat(String prompt, String initialValue) {
        Object[] options = {"OK"};
        Object[] commentArray = {prompt, EMPTY_STRING, EMPTY_STRING};
        boolean validResponse = false;
        float response = 0.0f;
        while (!validResponse) {
            final JOptionPane optionPane = new JOptionPane(commentArray,
                                                           JOptionPane.QUESTION_MESSAGE,
                                                           JOptionPane.OK_CANCEL_OPTION,
                                                           null,
                                                           options,
                                                           options[0]);
            optionPane.setWantsInput(true);
            optionPane.setInitialSelectionValue(initialValue);  // EA: added
            JDialog dialog = optionPane.createDialog(null, FLOAT_TITLE);
            dialog.pack();
            dialog.setVisible(true);
            Object input = optionPane.getInputValue();
            if (input == JOptionPane.UNINITIALIZED_VALUE) {
                commentArray[1] = "Du musst ein Float eingeben"; // EA: explanatory text added
                commentArray[2] = EMPTY_STRING;
            } else {
                String result = (String) input;
                if (result == null) { // EA: added for completnes, but is this situation possible?
                    commentArray[1] = "Ungueltig:";
                    commentArray[2] = "Du musst ein gueltiges Float eingeben";
                } else {
                    // convert String to float
                    try {
                        // workaround for BlueJ bug - misses first exception after recompilation?
                        response = Float.valueOf(result).floatValue();
                        response = Float.valueOf(result).floatValue();
                        validResponse = true;
                    } catch (NumberFormatException exception) {
                        // EA: case with uninitialized value is moved up
                        commentArray[1] = "Ungueltig: " + result;
                        commentArray[2] = "Du musst ein gueltiges Float eingeben";
                        initialValue = result;    // EA: corrected
                    }
                }
            }
        }
        return response;
    }
    
    /**
     ** returns a double input from the user via a simple dialog.
     ** @param prompt the message string to be displayed inside dialog
     ** @return the input double
     **/
    public static double getDouble(String prompt) {
        return getFloat(prompt,"");
    }
    /**
     ** returns double input from the user via a simple dialog.
     ** @param prompt the message string to be displayed inside dialog
     ** @param initialValue input value that is initially displayed as selected by the user
     ** @return the input double
     **/
    public static double getDouble(String prompt, double initialValue) {
        return getFloat(prompt,Double.toString(initialValue));
    }
    /**
     ** returns double input from the user via a simple dialog.
     ** @param prompt the message string to be displayed inside dialog
     ** @param initialValue input string that is initially displayed as selected by the user
     ** @return the input double
     **/
    public static double getDouble(String prompt, String initialValue) {
        Object[] options = {"OK"};
        Object[] commentArray = {prompt, EMPTY_STRING, EMPTY_STRING};
        boolean validResponse = false;
        double response = 0.0d;
        while (!validResponse) {
            final JOptionPane optionPane = new JOptionPane(commentArray,
                                                           JOptionPane.QUESTION_MESSAGE,
                                                           JOptionPane.OK_CANCEL_OPTION,
                                                           null,
                                                           options,
                                                           options[0]);
            optionPane.setWantsInput(true);
            optionPane.setInitialSelectionValue(initialValue);  // EA: added
            JDialog dialog = optionPane.createDialog(null, DOUBLE_TITLE);
            dialog.pack();
            dialog.setVisible(true);
            Object input = optionPane.getInputValue();
            if (input == JOptionPane.UNINITIALIZED_VALUE) {
                commentArray[1] = "Du musst ein Double eingeben"; // EA: explanatory text added
                commentArray[2] = EMPTY_STRING;
            } else {
                String result = (String) input;
                if (result == null) { // EA: added for completnes, but is this situation possible?
                    commentArray[1] = "Ungueltig:";
                    commentArray[2] = "Du musst ein gueltiges Double eingeben";
                } else {
                    // convert String to double
                    try {
                        // workaround for BlueJ bug - misses first exception after recompilation?
                        response = Double.valueOf(result).doubleValue();
                        response = Double.valueOf(result).doubleValue();
                        validResponse = true;
                    } catch (NumberFormatException exception) {
                        // EA: case with uninitialized value is moved up
                        commentArray[1] = "Ungueltig: " + result;
                        commentArray[2] = "Du musst ein gueltiges Double eingeben";
                        initialValue = result;    // EA: corrected
                    }
                }
            }
        }
        return response;
    }
}
