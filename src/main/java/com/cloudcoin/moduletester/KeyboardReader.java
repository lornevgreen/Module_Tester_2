package com.cloudcoin.moduletester;
/*
Copyright (c) 2010, J Boyd Trolinger and Eric Weesner

Changes added 10/15/2014 by Sean H. Worthington

All rights reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list 
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this list 
of conditions and the following disclaimer in the documentation and/or other materials 
provided with the distribution.

Neither the name of the Butte College Department of Computer Science nor the names of its 
contributors may be used to endorse or promote products derived from this software without 
specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
import java.io.*;
import java.util.*;

/**
 * CinReader is a class designed to provide simplified and
 * robust handling of keyboard inputs.
 * CinReader provides methods for handling boolean, char, 
 * int, long, double, and String inputs.
 * Prompts and datatype-specific error messages can be 
 * customized via mutator methods.
 *
 * @author J Boyd Trolinger
 * @author Eric Weesner
 * @author Sean Worthington
 * @version 1.1
 */
public class KeyboardReader
{
    public static final int INT_MESSAGE = 0;
    public static final int DOUBLE_MESSAGE = 1;
    public static final int CHAR_MESSAGE = 2;
    public static final int STRING_MESSAGE = 3;
    public static final int BOOLEAN_MESSAGE = 4;
    public static final int LONG_MESSAGE = 5;

    public static final int NUM_ERROR_MESSAGES = 6;
    
    private static final String DEFAULT_ERROR_MESSAGE = "Please reenter. ";
    
    private String prompt = "> ";
    private String [] errorMessages;
        
    
    /**
     * Default constructor.
     * Sets default values as follows:
     * <ul>
     * <li>prompt: "> "</li>
     * <li>all error messages: "Please reenter. "</li>
     * </ul>
     */
    public KeyboardReader ()
    {
        prompt = "> ";
        setDefaultMessages();
    }
    
    /**
     * Overloaded constructor. Set prompt to a user supplied String.
     *
     * @param newPrompt the user supplied value for the prompt String.
     */
    public KeyboardReader (String newPrompt)
    {
        prompt = newPrompt;
        setDefaultMessages();
    }
    
    /**
     * Overloaded constructor. Set prompt to a user supplied String and 
     * datatype-specific error messages to a user supplied String array.
     * The String array should contain six (NUM_ERROR_MESSAGES) error messages for datatypes 
     * in the following order: int, double, char, String, boolean, long.
     *
     * @param newPrompt the user supplied value for the prompt String.
     * @param newErrorMessages the user supplied values for datatype-specific
     *                            error messages.
     */
    public KeyboardReader (String newPrompt, String [] newErrorMessages)
    {
        prompt = newPrompt;
        if (newErrorMessages != null)
            setErrorMessages(newErrorMessages);
        else
            setDefaultMessages();
    }
    
    
    /**
     * Set the value of the prompt String to a user supplied value.
     *
     * @param newPrompt the user supplied value for the prompt String. 
     */
    public void setPrompt (String newPrompt)
    {
        prompt = newPrompt;
    }
    
    /**
     * Set the String array used to output error messages.
     * The array should contain six (NUM_ERROR_MESSAGES) error messages for datatypes 
     * in the following order: int, double, char, String, boolean, long.
     *
     * @param newErrorMessages the user supplied values for datatype-specific
     *  error messages.
     */
    public void setErrorMessages (String [] newErrorMessages)
    {
        if (newErrorMessages != null)
        {
            int diff = errorMessages.length - newErrorMessages.length;
            // NEED A MINIMUM OF NUM_ERROR_MESSAGES ERROR MESSAGES TO AVOID ERRORS
            if (diff > 0)
            {
                errorMessages = new String[NUM_ERROR_MESSAGES];
                for (int i=0; i<errorMessages.length; i++)
                {
                    if (i < newErrorMessages.length)
                        errorMessages[i] = new String(newErrorMessages[i]);
                    else
                        errorMessages[i] = new String(DEFAULT_ERROR_MESSAGE);
                }
            }
            else
                errorMessages = newErrorMessages;
        }
    }
    
    /**
     * Set the value of a specific datatype-specific error message.
     * Use static variables for the first argument:
     * <ul>
     * <li>int message: CinReader.INT_MESSAGE</li>
     * <li>double message: CinReader.DOUBLE_MESSAGE</li>
     * <li>char message: CinReader.CHAR_MESSAGE</li>
     * <li>String message: CinReader.STRING_MESSAGE</li>
     * <li>boolean message: CinReader.BOOLEAN_MESSAGE</li>
     * <li>long message: CinReader.LONG_MESSAGE</li>
     * </ul>
     * 
     * @param idx the error message to set
     * @param msg the error message to be displayed for the datatype
     */
    public void setErrorMessage (int idx, String msg)
    {
        if (idx >= 0 && idx < errorMessages.length)
            errorMessages[idx] = msg;
    }
    
    /**
     * Set the value of the error message to display for <i>String inputs.
     *
     * @param msg the error message
     */
    public void setErrorMessageString (String msg)
    {
        errorMessages[STRING_MESSAGE] = msg;
    }
    
    /**
     * Set the value of the error message to display for <i>int</i> inputs.
     *
     * @param msg the error message
     */
    public void setErrorMessageInt (String msg)
    {
        errorMessages[INT_MESSAGE] = msg;
    }
    
    /**
     * Set the value of the error message to display for <i>double</i> inputs.
     *
     * @param msg the error message
     */
    public void setErrorMessageDouble (String msg)
    {
        errorMessages[DOUBLE_MESSAGE] = msg;
    }
    
    /**
     * Set the value of the error message to display for <i>char</i> inputs.
     *
     * @param msg the error message
     */
    public void setErrorMessageChar (String msg)
    {
        errorMessages[CHAR_MESSAGE] = msg;
    }
    
    /**
     * Set the value of the error message to display for <i>boolean</i> inputs.
     *
     * @param msg the error message
     */
    public void setErrorMessageBoolean (String msg)
    {
        errorMessages[BOOLEAN_MESSAGE] = msg;
    }
    
    /**
     * Set the value of the error message to display for <i>long</i> inputs.
     *
     * @since 1.1
     * @param msg the error message
     */
    public void setErrorMessageLong (String msg)
    {
        errorMessages[LONG_MESSAGE] = msg;
    }
    
    /**
     * Read a String in from the keyboard. May be empty and there is no
     * limit to the length of the input String.
     *
     * @return a String containing the user input. 
     */
    public String readString()
    { 
        char theChar = 'x';
        String result = "";
        boolean done = false;
        
        while (!done)
        {
            theChar = nextChar();
            if (theChar == '\n')
                done = true;
            else if (theChar == '\r'){}
            else 
                result = result + theChar;
        }
        
        return result;
    }

    /**
     * Read a String in from the keyboard.
     *
     * @param allowEmpty if true, may return an empty String; if false, 
     *  value returned is guaranteed to be of minimum length 1
     * @return a String containing the user input. 
     */
    public String readString (boolean allowEmpty)
    {
        String result = readString();
        if (!allowEmpty)
        {
            while (result.length() == 0)
            {
                System.out.println("Empty input not allowed. " + errorMessages[STRING_MESSAGE]);
                System.out.print(prompt);
                result = readString();
            }
        }
        return result;
    }
    
    
    /**
     * Read a String in from the keyboard.
     * This method added by Sean H. Worthington 10/15/2014
     *
     * @param args an array of allowable strings. 
     * @return a String containing the user input. 
     */
    public String readString (String[] args )
    {
        String result = readString();
        List valid = Arrays.asList( args );
        while ( !valid.contains(result) )
        {
           System.out.println("Please enter one of the following: " + Arrays.toString(args));
           System.out.print(prompt);
           result = readString( args );
        }
        return result;
    }
    
    
    
    
    /**
     * Read a String in from the keyboard.
     *
     * @param charLimit the maximum length of the returned String; if input 
     *  length is greater than the limit, the value is truncated to the 
     *  maximum length
     * @return a String containing the user input. 
     */
    public String readString (int charLimit)
    {
        String result = readString();
        if (result.length() > charLimit)
            result = result.substring(0, charLimit);
        return result;
    }
    
    /**
     * Read a String in from the keyboard.
     *
     * @param allowEmpty if true, may return an empty String; if false, 
     *  value returned is guaranteed to be of minimum length 1
     * @param charLimit the maximum length of the returned String; if input 
     *  length is greater than the limit, the value is truncated to the 
     *  maximum length
     * @return a String containing the user input. 
     */
    public String readString (boolean allowEmpty, int charLimit)
    {
        String result = readString(allowEmpty);
        if (result.length() > charLimit)
            result = result.substring(0, charLimit);
        return result;
    }
    
    /**
     * Read an int from the keyboard.
     * Uses Java Integer class (valueOf, intValue) methods 
     * to ensure return value is a valid int.
     *
     * @return an int containing the user input.
     */
    public int readInt()  
    {
        String inputString = "";
        int number = 0;
        boolean done = false;

        while (!done)
        {
            try
            {
                inputString = readString();
                inputString = inputString.trim();
                number = (Integer.valueOf(inputString).intValue());
                done = true; 
            }
            catch (NumberFormatException e)
            {
                System.out.println("Input is not an integer. " + errorMessages[INT_MESSAGE]);
                System.out.print(prompt);
            }
        }

        return number; 
    }

    /**
     * Read an int from the keyboard.
     * Uses Java Integer class (valueOf, intValue) methods 
     * to ensure return value is a valid int.
     *
     * @param min the minimum value allowed
     * @param max the maximum value allowed
     * @return an int containing the user input that is between min and max
     *  inclusive.
     */
    public int readInt(int min, int max)  
    {
        String inputString = "";
        int number = 0;
        boolean done = false;

        while (!done)
        {
            try
            {
                inputString = readString();
                inputString = inputString.trim();
                number = (Integer.valueOf(inputString).intValue());
                if (number < min || number > max)
                    System.out.println("Please enter an integer between " + min + " and " + max);
                else
                    done = true; 
            }
            catch (NumberFormatException e)
            {
                System.out.println("Input is not an integer. " + errorMessages[INT_MESSAGE]);
                System.out.print(prompt);
            }
        }

        return number; 
    }
    
    
    
    /**
     * Read an integer from the keyboard.
     * This method added by Sean H. Worthington 10/15/2014
     *
     * @param args an array of allowable integers. 
     * @return a integer containing the user input. 
     */
    public int readInt(int[] args)  
    {
        int result = readInt();
        
       while ( !checkInArray( result, args) )
        {
           System.out.println("Please enter one of the following: " + Arrays.toString(args));
           System.out.print(prompt);
           result = readInt( args );
        }
        return result;
    }
    
    
    /**
     * Read a long from the keyboard.
     * Uses Java Long class (valueOf, longValue) methods 
     * to ensure return value is a valid long.
     *
     * @since 1.1
     * @return a long containing the user input.
     */
    public long readLong()  
    {
        String inputString = "";
        long number = 0;
        boolean done = false;

        while (!done)
        {
            try
            {
                inputString = readString();
                inputString = inputString.trim();
                number = (Long.valueOf(inputString).longValue());
                done = true; 
            }
            catch (NumberFormatException e)
            {
                System.out.println("Input is not of type long. " + errorMessages[LONG_MESSAGE]);
                System.out.print(prompt);
            }
        }

        return number; 
    }

    /**
     * Read a long from the keyboard.
     * Uses Java Long class (valueOf, longValue) methods 
     * to ensure return value is a valid long.
     *
     * @since 1.1
     * @param min the minimum value allowed
     * @param max the maximum value allowed
     * @return a long containing the user input that is between min and max
     *  inclusive.
     */
    public long readLong(long min, long max)  
    {
        String inputString = "";
        long number = 0;
        boolean done = false;

        while (!done)
        {
            try
            {
                inputString = readString();
                inputString = inputString.trim();
                number = (Long.valueOf(inputString).longValue());
                if (number < min || number > max)
                    System.out.println("Please enter a long value between " + min + " and " + max);
                else
                    done = true; 
            }
            catch (NumberFormatException e)
            {
                System.out.println("Input is not of type long. " + errorMessages[LONG_MESSAGE]);
                System.out.print(prompt);
            }
        }

        return number; 
    }

    /**
     * Read a double from the keyboard.
     * Uses Java Double class (valueOf, doubleValue) methods 
     * to ensure return value is a valid double.
     *
     * @return a double containing the user input.
     */
    public double readDouble() 
    {
        String inputString = "";
        double number = 0;
        boolean done = false;

        while (!done)
        {
            try
            {
                inputString = readString();
                inputString = inputString.trim();
                number = (Double.valueOf(inputString).doubleValue()); 
                done = true; 
            }
            catch (NumberFormatException e)
            {
                System.out.println("Input is not of type double. " + errorMessages[DOUBLE_MESSAGE]);
                System.out.print(prompt);
            }
        }

        return number;
    }
    
    /**
     * Read a char from the keyboard.
     * Uses the String class method trim so the input cannot be
     * a whitespace character.
     *
     * @return a char containing the user input.
     */
    public char readChar() 
    {
        boolean done = false;
        String inputString = "";
        char nonWhite = 'x';

        while (!done)
        {
            inputString = readString();
            inputString = inputString.trim(); 
            if (inputString.length() != 1)   
            {
                System.out.println("Input must be a single character. " + errorMessages[CHAR_MESSAGE]);
                System.out.print(prompt);
            }
            else
            {                                    
                nonWhite = (inputString.charAt(0)); 
                done = true;
            }
        }

        return nonWhite;
    }
    
    
    /**
     * Read a char from the keyboard.
     * Added by Sean H. Worthington 10/15/2014
     * 
     * Restricts the characters that can be input to an array of characters.
     * 
     * @args array of characters allowable. 
     * @return a char containing the user input.
     */
    public char readChar( char[] args ) 
    {
        char theChar = 'x';
        boolean done = false;
        while (!done)
        {
            theChar = readChar();
            if(  !(new String( args ).indexOf( theChar ) == -1) )
            {
                    done = true;
                    break;
                }
            if (!done)
            {
                System.out.print("Invalid input. Please enter one of the following -> ");
                System.out.println(Arrays.toString(args));
                System.out.print("\n" + prompt);
            }
        }
        return theChar;
    }
    
    
    
    
    /**
     * Read a char from the keyboard.
     * Will allow the single space character in the range.
     *
     * @param range a String containing the list of allowable
     *  characters
     * @return a char containing the user input.
     */
    public char readChar (String range)
    {
        char theChar = 'x';
        boolean done = false;
        while (!done)
        {
            theChar = readChar();
            for (int i=0; i<range.length(); i++)
            {
                if (theChar == range.charAt(i))
                {
                    done = true;
                    break;
                }
                
            }
            if (!done)
            {
                System.out.print("Invalid input. Please enter one of the following -> ");
                for (int i=0; i<range.length(); i++)
                {
                    if (range.charAt(i) == ' ')
                        System.out.print("SPACE ");
                    else
                        System.out.print(range.charAt(i) + " ");
                }
                System.out.print("\n" + prompt);
            }
        }
        return theChar;
    }

    /**
     * Convert user input values of "true" or "false" into boolean values.
     * This method is case-insensitive: returns true if the input value is 
     * "t" or "true," returns false if the input value is "f" or "false."
     *
     * @return a boolean representing the user input.
     */
    public boolean readBoolean() 
    {
        boolean done = false;
        String inputString = "";
        boolean result = false;

        while (!done)
        {
            inputString = readString(false);
            inputString = inputString.trim(); 
            if (inputString.equalsIgnoreCase("true") || inputString.equalsIgnoreCase("t"))
            {
                result = true;
                done = true;
            }
            else if (inputString.equalsIgnoreCase("false") || inputString.equalsIgnoreCase("f"))
           {
                result = false;
                done = true;
            }
            else 
            {
                System.out.println("Input must be [t]rue or [f]alse. " + errorMessages[BOOLEAN_MESSAGE]);
                System.out.print(prompt);
            }
        }

        return result;
    }
    
    /**
     * Set all error output messages to the DEFAULT_ERROR_MESSAGE.
     */
    private void setDefaultMessages ()
    {
        errorMessages = new String[NUM_ERROR_MESSAGES];
        for (int i=0; i<errorMessages.length; i++)
            errorMessages[i] = new String(DEFAULT_ERROR_MESSAGE);
    }
    
    /**
     * Use System.in.read to read the next character from the
     * STDIN stream.
     */
    private char nextChar()
    {
        int charAsInt = -1;
        try
        {
            charAsInt = System.in.read(); 
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Fatal error. Exiting program.");
            System.exit(0);          
        }

        return (char)charAsInt;         
   }
   
   private boolean checkInArray(int currentState, int[] myArray) {
    boolean found = false;

    for (int i = 0; !found && (i < myArray.length); i++) {
        found = (myArray[i] == currentState);
    }

    return found;
   }

}
