package com.jflyg.project_tddc73;

import android.content.Context;
import android.graphics.Color;
import android.R.color;

/**
 *  Class PasswordStrength, containing the strength, the color and string values associated with the strength,
 *  as well as hints to help the user strengthen the password.
 */
public class PasswordStrength {

    private String PassText = "";
    private int Strength = 0;
    private String Hints = "";

    private int[] StrengthColors = {
            Color.parseColor("#B30000"), //Weak
            Color.parseColor("#B30000"), //Weak
            Color.parseColor("#CCA300"), //Fair
            Color.parseColor("#007ACC"), //Good
            Color.parseColor("#2EB82E"), //Strong
            Color.parseColor("#2EB82E") //Very Strong
    };

    private String[] StrengthStrings = {
            "Weak",
            "Weak",
            "Fair",
            "Good",
            "Strong",
            "Very Strong"
    };

    private String [] StrengthHints = { "> Use a longer password",
                                        "> Use a less common password",
                                        "> Add a number",
                                        "> Add a special character",
                                        "> Use both Capitals and lowercase letters"};

    //Soruce: https://techviral.net/common-passwords-might-surprise/
    private String [] CommonPass = {"123456",
                                    "password",
                                    "123456789",
                                    "12345678",
                                    "12345",
                                    "111111",
                                    "1234567",
                                    "sunshine",
                                    "qwerty",
                                    "iloveyou"};

    /**
     * Creates a new PassWordStrength from a String
     * @param s the password string
     */
    public PasswordStrength (String s){
        PassText = s;
    }

    /**
     * Returns the color associated with the strength (an integer) of the password
     * @return      the color at position of Stength
     */
    public int getColor(){
        return StrengthColors[Strength];
    }


    /**
     * Returns the Strength of the password in textform given a Strength (int)
     * @return the Strength string at position Strength.
     */
    public String getStrengthString(){
        return StrengthStrings[Strength];
    }

    /**
     * Returns the relevant hints that can strengthen the password
     * @return a string of password hints
     */
    public String getHints(){

        return Hints;
    }

    /**
     * Get the current strength of the password
     * @return the integer value of Strength
     */
    public int getStrengh(){

        return Strength;
    }

    /**
     * Get the highest possible strength value
     * @return the integer value representing the maximal strength
     */
    public int getMaxStrength(){
        return (StrengthColors.length - 1);
    }

    /**
     * Generates a strength value for the password and a set of hints dependent on which conditions are met.
     * <br>The following conditions are tested:
     * <br> The length of the password is greater than 8.
     * <br>The password isn't one of the 10 most common. (2018)
     * <br>The password contains at least one special character.
     * <br>The password contains at least one number.
     * <br>The password contains both upper- and lowercase letters.
     */
    public void generateStrength(){

        //highest
        int tmpStrength = 5;
        String tmpHints = "";

        // check length
        if(PassText.length() < 8){
            tmpStrength--;
            tmpHints = (tmpHints + "\n" + StrengthHints[0]);
        }

        // check common passwords
        for(int i = 0; i < CommonPass.length; i++){

            if((CommonPass[i].equals(PassText))){
                tmpStrength--;
                tmpHints = tmpHints + "\n" + StrengthHints[1];
                break;
            }
        }

        // check numbers
        if(!PassText.matches(".*\\d+.*")){
            tmpStrength--;
            tmpHints = tmpHints + "\n" + StrengthHints[2];
        }

        // check special char
        if(!PassText.matches(".*[^A-Za-z0-9].*")){
            tmpStrength--;
            tmpHints = tmpHints + "\n" + StrengthHints[3];
        }

        // check if capitals
        if( (PassText.equals(PassText.toLowerCase())) || (PassText.equals(PassText.toUpperCase()))){
            tmpStrength--;
            tmpHints = tmpHints + "\n" + StrengthHints[4];
        }


        Hints = tmpHints;
        Strength = tmpStrength;
    }



}
