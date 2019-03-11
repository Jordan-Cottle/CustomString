import java.util.Scanner;
import java.util.Random;
/**
 * Quick demo of CustomString methods. Mostly repurposed code from labs and in class exercises
 *
 * @author Jordan Cottle
 * @version 3/5/2019
 */
public class CustomStringDemo
{
    public static void main(String[] args){
        CustomString str;
        Scanner in = new Scanner(System.in);
        
        System.out.println("Tell me something cool: ");
        str = new CustomString(in.nextLine());
        
        System.out.println("Enter an integer to find the character at that index: ");
        int index = in.nextInt();
        in.nextLine(); //Clear newline from buffer
        
        // possible use for the exception, although checking index parameter before using it is preferred. 
        char c;
        try{
            c = str.charAt(index);
        }
        catch(IllegalArgumentException badIndex){
            System.err.println("You chose an index that was outside the string!");
            c = '\0';
        }
        System.out.printf("Your chosen character = %c\n", c);
        
        // bad use for catching the exception, loop should be fixed instead
        // making the exception a checked exception would encourage this
        for(int i = 0; i <= str.length(); i++){
            try{
                System.out.printf("'%c' ", str.charAt(i));
            }
            catch(IllegalArgumentException badIndex){
                System.err.println("String indexed too far!");
            }
        }
        System.out.println();
        
        // Print out each index of a provided character
        System.out.print("Enter a character to search for in your string: ");
        char search = in.nextLine().charAt(0);
        
        index = 0;
        while(index != -1){
            // another error that should be handled in another way (happens when the search character is found at the end of the string)
            try{
                index = str.indexOf(search, index);
            }
            catch(IllegalArgumentException badIndex){
                System.err.println("Bad starting index for str.indexOf()!");
                index = -1;
            }
            
            if(index != -1)
                System.out.printf("%c found at index %d\n", search, index++);
        }
        
        // Print out each index of a provided character, starting from the end of the string
        System.out.print("Enter another character to search for in your string: ");
        char reverseSearch = in.nextLine().charAt(0);
        
        index = str.length();
        while(index != -1){
            // This error should definitely be handled in another way
            try{
                index = str.lastIndexOf(reverseSearch, index);
            }
            catch(IllegalArgumentException badIndex){
                System.err.println("Bad starting index for str.lastIndexOf()!");
                index--;
            }
            
            if(index != -1)
                System.out.printf("%c found at index %d\n", reverseSearch, index--);
        }
        
        // replace random character with user-defined character
        Random r = new Random();
        
        System.out.println("Enter your favorite character: ");
        char replacement = in.nextLine().charAt(0);  
        char toReplace = str.charAt(r.nextInt(str.length()));
        
        System.out.printf("Your new and improved string is: %s\n", str.replace(toReplace, replacement));
        
        System.out.printf("All caps version: %s\n", str.toUpperCase());
        System.out.printf("No caps version: %s\n", str.toLowerCase());
        
        CustomString magicWord = new CustomString("Pa22w0rd");
        if(str.equals(magicWord)){
            System.out.println("Your string is the magic word!");
        }
        else{
            System.out.println("Sorry, your string is not the magic word");
        }
        
        if(magicWord.equals(new CustomString("Pa22w0rd"))){
            System.out.printf("The magic word is %s\n", magicWord);
        }
        
        // Alphabatize two words, comparing differences between compareTo and compareToIgnoreCase
        CustomString animal = new CustomString("aardvark");
        CustomString fruit = new CustomString("Apple");
        
        System.out.print("According to the case-sensitive ascii code: ");
        if(animal.compareTo(fruit) < 0){
            System.out.printf("%s comes before %s\n", animal, fruit);
        }
        else if(fruit.compareTo(animal) < 0){
            System.out.printf("%s comes before %s\n", fruit, animal);
        }
        else{
            System.out.printf("%s and %s are the same\n", animal, fruit);
        }
        
        System.out.print("Ignoring case: ");
        if(animal.compareToIgnoreCase(fruit) < 0){
            System.out.printf("%s comes before %s\n", animal, fruit);
        }
        else if (fruit.compareToIgnoreCase(animal) < 0){
            System.out.printf("%s comes before %s\n", fruit, animal);
        }
        else{
            System.out.printf("%s and %s are the same\n", animal, fruit);
        }
        
        // count spaces in sentence for crude estimate of #of words in string
        int wordCount = 1;
        int spaceIndex = str.indexOf(' ');
        while(spaceIndex != -1 && spaceIndex < str.length()-1){
            wordCount++;
            spaceIndex = str.indexOf(' ', spaceIndex+1);
        }
        
        // use substring to break string into words along spaces
        CustomString[] words = new CustomString [wordCount];
        int start = 0;
        int end = str.indexOf(' ');
        for(int i = 0; i < wordCount; i++){
            words[i] = str.substring(start, end);
            
            // skip start/end calculation if on last iteration
            if(i == wordCount-1) break;
            // avoids trying to index past the string's length
            
            start = end + 1;
            end = str.indexOf(' ', end+1);

            if(end == -1){
                end = str.length(); 
            }           
        }
        
        // print the words in the string
        for(CustomString word: words){
            System.out.println(word);
        }
    }
}
