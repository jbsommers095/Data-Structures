import java.util.HashMap;

/**
 * Jeremy Sommers
 * TODO: Complete the implementation of this class.
 */

public class FrequencyTable extends HashMap<Character, Integer> {
  /**
   * Constructs an empty table.
   */
  public FrequencyTable() {
    super();
  }
    
  /**
   * TODO: Make use of get() and put().
   * 
   * Constructs a table of character counts from the given text string.
   */
  private String text;
  public FrequencyTable(String text) {
    for (int i = 0; i < text.length(); i++) {
    	put(Character.valueOf(text.charAt(i)), 1); 
    	// Every character inside the string
        // Will have a frequency >= 1
    }
    this.text = text;
  }
  
  /**
   * TODO
   * 
   * Returns the count associated with the given character. In the case that
   * there is no association of ch in the map, return 0.
   */
  @Override
  public Integer get(Object ch) {
    if (!this.containsKey((Character) ch))
    return 0;
    
    if (this.text == null) {
    	return super.get((Character) ch);
    }
    String c = Character.valueOf((Character) ch).toString();
    
    if (!this.text.contains(c))
    	return super.get((Character) ch);
    
		 Integer count = 0;
		  for (int i = 0; i < this.text.length(); i++) {
			  if (Character.valueOf(this.text.charAt(i)).equals((Character) ch)) {
				  count += 1;
			  }
		  }
		  return count;
	 }
}
