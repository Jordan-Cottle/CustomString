import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Random;

/**
 * Tests for CustomString objects to make sure their method implementations match the String class.
 *
 * @author  Jordan Cottle
 * @version 3/2/2019
 */
public class CustomStringTest
{
    private String[] strings;
    private static Random r;

    /**
     * Sets up a random object, and records the seed to recreate random components of the tests as needed
     */
    @BeforeClass
    public static void setUpRandom(){
        long seed = System.currentTimeMillis();
        r = new Random(seed);
        System.out.println("Using " + seed + " for random seed");
    }

    /**
     * Generates the set of words for each test case, every test uses all of the words
     * <br/>
     * Add new words to the array in this method to have them applied in every test case.
     * <br/>
     * Called before every test case method, so the randomized words will be different for every test.
     */
    @Before
    public void setUp()
    {
        strings = new String[] {"Hello", "World", "Hello World",
            "Test", "Testing",
            randomString(), randomString(), randomString()};
    }

    /**
     * Generates a random string
     * 
     * @return a randomized string
     */
    private static String randomString(){
        StringBuilder str = new StringBuilder();

        int wordCount = r.nextInt(6) + 3;
        for(int j = 0; j < wordCount; j++){

            int letterCount = r.nextInt(12) + 3;
            boolean firstLetter = true;
            for(int k = 0; k<letterCount; k++){
                if(firstLetter){
                    // add a random uppercase letter between A and Z
                    str.append(randomChar(true));
                    firstLetter = false;
                }
                else{
                    // add a random lowecase letter between a and z
                    str.append(randomChar(false));
                }
            } // end letter loop
            //spaces seperate words
            str.append(" ");
        } // end word loop

        str.setCharAt(str.length() - 1, '.');
        return str.toString();
    }

    /**
     * Generates a random character
     * 
     * @return A random uppercase or lowercase character
     */
    private static char randomChar(){
        boolean upperCase = r.nextBoolean();
        char character = (char) (r.nextInt(90-65) + 65);
        return upperCase ? character: (char) (character + 32);
    }

    /**
     * Generates a random character of a specified case
     * 
     * @param upperCase Whether or not the letter should eb upper case or not
     * 
     * @return A random character that matches the desired case
     */
    private static char randomChar(boolean upperCase){
        char character = (char) (r.nextInt(90-65) + 65);
        return upperCase ? character: (char) (character + 32);
    }

    /**
     * Tests charAt method by comparing it's behavior directly to normal String.charAt() return value
     */
    @Test
    public void testCharAt(){
        for(String string: strings){
            CustomString cString = new CustomString(string);
            int index = r.nextInt(string.length());
            assertEquals(string.charAt(index), cString.charAt(index));
            
            // test exception
            try {
                cString.charAt(cString.length());
                fail("Expected IllegalArgumentException from indexing outside of the custom string's length.");
            }
            catch(IllegalArgumentException badIndex){
                continue;  // unneccessary, but explicity declares the desired effect
            }
        }
    }

    /**
     * Tests indexOf method by comparing directly with String.indexOf() return value
     * <br/>
     * First loop tests by finding the first index of all lowercase vowels
     * <br/><br/>
     * Second loop tests by finding all indicies of 10 random characters<br/>
     *   - Also tests for input validation by catching the IllegalArgumentException when trying to index out of bounds
     */
    @Test
    public void testIndexOf(){
        char[] vowels = {'a', 'e', 'i', 'o', 'u'};
        for(String string: strings){
            CustomString cString = new CustomString(string);
            // test for finding vowels
            for(char vowel: vowels){
                assertEquals(string.indexOf(vowel), cString.indexOf(vowel));
            }

            // test 10 random characters
            for(int i = 0; i < 10; i++){
                char character = randomChar();
                int index = 0;

                // test finding all of the indexes of the character
                while(index != -1){
                    assertEquals(string.indexOf(character, index), cString.indexOf(character, index));
                    index = cString.indexOf(character, index);

                    if(index != -1){
                        index++;
                    }
                    
                    // test exception
                    if(index >= string.length()){
                        try{
                            cString.indexOf(character, index);
                            fail("Expected IllegalArgumentException from trying to index CustomString past it's length");
                        }
                        catch(IllegalArgumentException e){
                            index = -1;
                        }
                    }
                } // end search for all char indexes loop
            } // end random char loop
        } // end string loop
    }

    /**
     * Tests lastIndexOf method by comparing directly with String.lastIndexOf() return value
     * <br/>
     * First loop tests by finding the last index of all lowercase vowels
     * <br/>
     * Second loop tests by finding all indicies of 10 random characters from last index to first
     */
    @Test
    public void testLastIndexOf(){
        char[] vowels = {'a', 'e', 'i', 'o', 'u'};
        for(String string: strings){
            CustomString cString = new CustomString(string);
            for(char vowel: vowels){
                assertEquals(string.lastIndexOf(vowel), cString.lastIndexOf(vowel));
            }

            // test 10 random characters
            for(int i = 0; i < 10; i++){
                char character = randomChar();
                int index = string.length() - 1;

                // test finding all of the indexes of the character
                while(index != -1){
                    assertEquals(string.lastIndexOf(character, index), cString.lastIndexOf(character, index));
                    index = cString.lastIndexOf(character, index);

                    if(index != -1){
                        index--;
                    }
                } // end search for all char indexes loop
            } // end random char loop
            
            // test exception being thrown
            try {
                cString.lastIndexOf('a', cString.length());
                fail("Expected IllegalArgumentException from indexing outside of the custom string's length.");
            }
            catch(IllegalArgumentException badIndex){
                continue;  // unneccessary, but explicity declares the desired effect
            }
        } // end string loop
    }

    /**
     * Tests replace method by comparing it's return value to the same String.replace() call's return value
     * <br/>
     * The first loop 'increments' each vowel in the word by 1 
     * <br/>
     * Second loop replaces each character in the original string's char array with a new random character<br/>
     *  -repeat letters in original string result in repeated tests, usually with a different replacment character<br/>
     *  -each original strings are used each iteration; changes are not kept through consecutive iterations
     */
    @Test
    public void testReplace(){
        char[] vowels = {'a', 'e', 'i', 'o', 'u'};
        for(String string: strings){
            CustomString cString = new CustomString(string);
            for(char vowel: vowels){
                assertEquals(string.replace(vowel, (char) (vowel+1)), cString.replace(vowel, (char) (vowel+1)).toString());
            }

            for(char letter: string.toCharArray()){
                char newChar = randomChar();
                assertEquals(string.replace(letter, newChar), cString.replace(letter, newChar).toString());
            }
        }
    }

    /**
     * Tests toUpperCase method by comparing its return value to String.toUpperCase()
     */
    @Test
    public void testToUpperCase(){
        for(String string: strings){
            CustomString cString = new CustomString(string);
            assertEquals(string.toUpperCase(), cString.toUpperCase().toString());
        }
    }

    /**
     * Tests toLowerCase by comparing its return value to String.toLowerCase()
     */
    @Test
    public void testToLowerCase(){
        for(String string: strings){
            CustomString cString = new CustomString(string);
            assertEquals(string.toLowerCase(), cString.toLowerCase().toString());
        }
    }

    /**
     * Tests equals method by comparing its return value to String.equals()
     * <br/>
     * Tests positive case by creating two objects from the same source data
     * <br/>
     * Test negative case (usually) by generating a random string and comparing it to the original word
     */
    @Test
    public void testEquals(){
        for(String string: strings){

            CustomString cStringA = new CustomString(string);
            CustomString cStringB = new CustomString(string);

            String other = new String(string);

            assertEquals(string.equals(other), cStringA.equals(cStringB));

            String random = randomString();
            CustomString randomCString = new CustomString(random);

            assertEquals(string.equals(random), cStringA.equals(randomCString));
        }
    }

    /**
     * Tests compareTo  method by comparing its relative return value to String.compareTo()
     * <br/>
     * Test passes if both return positive values, negative values, or 0<br/>
     * - the actual value returned by the CustomString.compareTo() and String.compareTo() may differ as long as both evaluate the same when compared to 0
     * <br/>
     * Tests return value of 0 by comparing objects created from the same source data
     * <br/>
     * Tests non zero return value (usually) by generating random words and comparing them to the source values, then reversing their positions
     */
    @Test
    public void testCompareTo(){
        for(String string: strings){

            CustomString cStringA = new CustomString(string);
            CustomString cStringB = new CustomString(string);

            String other = new String(string);

            assertEquals(string.compareTo(other), cStringA.compareTo(cStringB));

            String random = randomString();
            CustomString randomCString = new CustomString(random);

            boolean expected = string.compareTo(random) > 0;
            boolean result = cStringA.compareTo(randomCString) > 0;
            assertEquals(expected, result);

            boolean reverseExpected = random.compareTo(string) > 0;
            boolean reverseResult = randomCString.compareTo(cStringA) > 0;
            assertEquals(reverseExpected, reverseResult);

            // if strings are not the same, check that swapping the position of the string in comparison changes the returned value
            if(string.compareTo(random) != 0){
                assertNotEquals(result, reverseResult);
            }
        }
    }

    /**
     * Tests compareToIgnoreCase  method by comparing its relative return value to String.compareToIgnoreCase()
     * <br/>
     * Test passes if both return positive values, negative values, or 0<br/>
     * - the actual value returned by the CustomString.compareToIgnoreCase() and String.compareToIgnoreCase() may differ as long as both evaluate the same when compared to 0
     * <br/>
     * Tests return value of 0 by comparing objects created from the same source data
     * <br/>
     * Tests non zero return value (usually) by generating random words and comparing them to the source values, then reversing their positions
     */
    @Test
    public void testCompareToIgnoreCase(){
        for(String string: strings){

            CustomString cStringA = new CustomString(string);
            CustomString cStringB = new CustomString(string);

            String other = new String(string);

            assertEquals(string.compareToIgnoreCase(other), cStringA.compareToIgnoreCase(cStringB));

            String random = randomString();
            CustomString randomCString = new CustomString(random);

            boolean expected = string.compareToIgnoreCase(random) > 0;
            boolean result = cStringA.compareToIgnoreCase(randomCString) > 0;
            assertEquals(expected, result);

            boolean reverseExpected = random.compareToIgnoreCase(string) > 0;
            boolean reverseResult = randomCString.compareToIgnoreCase(cStringA) > 0;
            assertEquals(reverseExpected, reverseResult);

            // if strings are not the same, check that swapping the position of the string in comparison changes the returned value
            if(string.compareToIgnoreCase(random) != 0){
                assertNotEquals(result, reverseResult);
            }
        }
    }

    /**
     * Tests substring method by generating random substring indicies within each word's length then compares the return value to String.substring()
     */
    @Test
    public void testSubstring(){
        for(String string: strings){
            int start = r.nextInt(string.length() - 3);
            int maxDelta = string.length() - start;
            int end = r.nextInt(maxDelta) + start;

            CustomString cString = new CustomString(string);

            assertEquals(string.substring(start, end), cString.substring(start, end).toString());
            
            try{
                cString.substring(1, 0);
                fail("Expected IllegalArgumentException from start index being greater than end index");
            }
            catch(IllegalArgumentException badArguments){
                
            }
            
            try{
                cString.substring(0, cString.length()+1);
                fail("Expected IllegalArgumentException from end index being greater than the string's length");
            }
            catch(IllegalArgumentException badIndex){
                
            }
        }
    }

    /**
     * Tests toString method to ensure that CustomString's toString method generates the same String it was created with 
     */
    @Test
    public void testToString(){
        for(String string: strings){
            CustomString cString = new CustomString(string);

            assertEquals(string, cString.toString());
        }
    }
}
