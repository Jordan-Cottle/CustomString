
/**
 * Implements some of the same behaviors as the standard java String class
 *
 * @author Jordan Cottle
 * @version 3/5/2019
 */
public class CustomString
{
    private final char[] characters;
    
    /**
     * Creates a new CustomString from a String object
     * 
     * @param str The string containing the characters to use for this CustomString object
     */
    public CustomString(String str)
    {
        characters = str.toCharArray();
    }

    /**
     * Creates a new CustomString from an array of characters
     * 
     * @param characters The array of characters to use as a source for this CustomString
     */
    public CustomString(char[] characters){
        this.characters = characters;
    }

    /**
     * Gets the length of the CustomString
     * 
     * @return The number of characters in this CustomString
     */
    public int length(){
        return characters.length;
    }

    /**
     * Finds the character at a given index
     * 
     * @param index The 0 based index of the desired character
     * 
     * @return The character at the specified index
     */
    public char charAt(int index){
        if(index >= characters.length){
            throw new IllegalArgumentException("Specified index is outside the range of the CustomString's character array!"); 
        }
        return characters[index];
    }

    /**
     * Finds the index of the first occurance of the specified character
     * 
     * @param character The character to look for the index of
     * 
     * @return The index of the first occurance of the character in this CustomString or -1 if the character is not found
     */
    public int indexOf(char character){
        for(int i = 0; i < characters.length; i++){
            if (character == characters[i]){
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds the index of the first occurance of the specified character at or after a specified starting index
     * 
     * @param character The character to look for the index of
     * @param startIndex The index to start looking for the character at, inclusive
     * 
     * @return The index of the first occurance of the character at or after the specified starting index in this CustomString or -1 if the character is not found
     */
    public int indexOf(char character, int startIndex){
        if(startIndex >= characters.length){
            throw new IllegalArgumentException("Specified index is outside the range of the CustomString's character array!"); 
        }

        for(int i = startIndex; i < characters.length; i++){
            if (character == characters[i]){
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds the index of the last occurance of the specified character
     * 
     * @param character The character to look for the index of
     * 
     * @return The index of the last occurance of the character in this CustomString or -1 if the character is not found
     */
    public int lastIndexOf(char character){
        for(int i = characters.length -1; i >= 0; i--){
            if (character == characters[i]){
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds the index of the last occurance of the specified character, searching up to a specified index
     * 
     * @param character The character to look for the index of
     * @param endIndex The last index to search to, any characters after this index will be ignored
     * 
     * @return The index of the last occurance of the character in this CustomString ignoring any past the specified index or -1 if the character is not found
     */
    public int lastIndexOf(char character, int endIndex){
        if(endIndex >= characters.length){
            throw new IllegalArgumentException("Specified index is outside the range of the CustomString's character array!"); 
        }
        for(int i = endIndex; i >= 0; i--){
            if (character == characters[i]){
                return i;
            }
        }
        return -1;
    }

    /**
     * Creates a new CustomString with a specified character replaced by another specified character
     * 
     * @param oldChar The character to replace
     * @param The new character to replace the oldChar with
     * 
     * @return A new CustomString object with the specified replacement made
     */
    public CustomString replace(char oldChar, char newChar){
        char[] newCharacters = new char[characters.length];
        int index = 0;
        for(char character: characters){
            if (character == oldChar){
                newCharacters[index] = newChar;
            }
            else{
                newCharacters[index] = characters[index];
            }
            index++;
        }

        return new CustomString(newCharacters);
    }

    /**
     * Creates a new CustomString object with all of the lower case letters replaced with their upper case versions
     * 
     * @return A new CustomString object with all of the lower case letters replaced with their upper case versions
     */
    public CustomString toUpperCase(){
        char[] newCharacters = new char[characters.length];
        int index = 0;
        for(char character: characters){
            if (character >= 'a' && character <= 'z'){
                newCharacters[index] = (char) (character - 32);
            }
            else{
                newCharacters[index] = characters[index];
            }
            index++;
        }

        return new CustomString(newCharacters);
    }

    /**
     * Creates a new CustomString object with all of the upper case letters replaced with their lower case versions
     * 
     * @return A new CustomString object with all of the upper case letters replaced with their lower case versions
     */
    public CustomString toLowerCase(){
        char[] newCharacters = new char[characters.length];
        int index = 0;
        for(char character: characters){
            if (character >= 'A' && character <= 'Z'){
                newCharacters[index] = (char) (character + 32);
            }
            else{
                newCharacters[index] = characters[index];
            }
            index++;
        }

        return new CustomString(newCharacters);
    }

    /**
     * Compares another object to this CustomString and returns true if they are the same
     * 
     * @param o The other object to compare this CustomString to
     * 
     * @return Whether or not this CustomString and the specified object are equal
     */
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(this.getClass() != o.getClass()){
            return false;
        }
        
        CustomString other = (CustomString) o;
        
        if(this.characters.length != other.characters.length){
            return false;
        }
        
        for(int i = 0; i < characters.length; i++){
            if(this.characters[i] != other.characters[i]){
                return false;
            }
        }
        
        return true;
    }

    /**
     * Compares this CustomString to another CustomString character by character.
     * 
     * @param other The other CustomString to compare this one to
     * 
     * @return The difference in the ascii coce between the first two different characters or 0 if the CustomStrings are the same.
     */
    public int compareTo(CustomString other){
        if(this.equals(other)){
            return 0;
        }
        
        int minLength = this.characters.length < other.characters.length ? this.characters.length: other.characters.length;
        int i;
        for(i = 0; i < minLength; i++){
            char thisChar = this.characters[i];
            char otherChar = other.characters[i];
            if(thisChar == otherChar){
                continue;
            }
            
            return thisChar - otherChar;
        }
        
        // consider longer words as less than shorter words
        // return this - other of next character (where one of them is the null character - ASCII 0)
        return this.characters.length < other.characters.length ? 0 - other.characters[i]: this.characters[i] - 0; 
    }

    /**
     * Compares this CustomString to another CustomString character by character, ignoring differences between upper and lower case letters
     * 
     * @param other The other CustomString to compare this one to
     * 
     * @return The difference in the ascii coce between the first two different characters or 0 if the CustomStrings are the same.
     */
    public int compareToIgnoreCase(CustomString other){
        return this.toUpperCase().compareTo(other.toUpperCase());
    }

    /**
     * Creates a new CustomString object from characters starting at a specified index up to, but not including the ending index.
     * 
     * @param start The index of the first character to include in the new CustomString substring
     * @param end The index of the first character to exclude in the new CustomString substring
     * 
     * @return A new CustomString object containing the characters starting at the starting index, up to but not including the ending index.
     */
    public CustomString substring(int start, int end){
        if (end < start){
            throw new IllegalArgumentException("The end of the substring cannot be before the start of the substring");
        }
        if (end > this.length()){
            throw new IllegalArgumentException("The end of the substring cannot be greater than the length of this string");
        }
        
        int subStringLength = end - start;
        char [] newCharacters = new char[subStringLength];
        
        for(int i = start; i < end; i++){
            newCharacters[i-start] = characters[i];
        }
        
        return new CustomString(newCharacters);
    }

    /**
     * Converts this CustomString object's character array into a standard String object
     * 
     * @return A string object created from this CustomString's character array
     */
    public String toString(){
        return new String(characters);
    }
}
