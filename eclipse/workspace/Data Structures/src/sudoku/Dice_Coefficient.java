package sudoku;

import java.util.HashSet;

/**
 * Write a description of class Dice_Coefficient here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Dice_Coefficient
{
    private String string= "";
    private double matches;

    public Dice_Coefficient(String string) {
        this.string = string;
        matches = 0;
    }
    
    public double compareString(String newString) {
        //Makes two charcaters arrays from the Strings    	
        char[] origArray = string.toCharArray();
        char[] compArray = newString.toCharArray();
        
        
        return matches / (Math.min(origArray.length, compArray.length) - 1);
    }
}