import java.util.Comparator;
import java.util.*;

/**
 * TODO: Complete the implementation of this class.
 * 
 * A HuffmanTree represents a variable-length code such that the shorter the
 * bit pattern associated with a character, the more frequently that character
 * appears in the text to be encoded.
 */

public class HuffmanTree {
  
  class Node {
    protected char key;
    protected int priority;
    protected Node left, right;
    
    
    public Node(int priority, char key) {
      this(priority, key, null, null);
    }
    
    public Node(int priority, Node left, Node right) {
      this(priority, '\0', left, right);
    }
    
    public Node(int priority, char key, Node left, Node right) {
      this.key = key;
      this.priority = priority;
      this.left = left;
      this.right = right;
    }
    
    public boolean isLeaf() {
      return left == null && right == null;
    }
  }
  protected HashMap<Character, String> codes = new HashMap<Character, String>();
  protected Node root;
  
  /**
   * TODO
   * 
   * Creates a HuffmanTree from the given frequencies of letters in the
   * alphabet using the algorithm described in lecture.
   */
  public HuffmanTree(FrequencyTable charFreqs) {
    Comparator<Node> comparator = (x, y) -> {
      /**
       *  TODO: x and y are Nodes
       *  x comes before y if x's priority is less than y's priority
       */
      return x.priority - y.priority;
    };  
    PriorityQueue<Node> forest = new Heap<Node>(comparator);
    for (Character c : charFreqs.keySet()) {
    	int temp = charFreqs.get(c).intValue();
    	Node x = new Node(charFreqs.get(c).intValue(), c.charValue());
    	forest.insert(x);
    }
    while (forest.size() > 1) {
    	Node left = forest.delete();
    	Node right = forest.delete();
    	Node p = new Node(left.priority + right.priority, left, right);
    	forest.insert(p);
    }
    root = forest.peek();
    lookupHelper(root, "");
  }
  
  /**
   * TODO
   * 
   * Returns the character associated with the prefix of bits.
   * 
   * @throws DecodeException if bits does not match a character in the tree.
   */
  public char decodeChar(String bits) {
	  Node p = this.root;
	  int i = 0;
	  while(!p.isLeaf() && i < bits.length()) {
		  if (p == null)
			  throw new DecodeException(bits);
		  if (bits.charAt(i) == '0')
			  p = p.left;
		  else
			  if (bits.charAt(i) == '1')
				  p = p.right;
		  i++;
		  
	  }
	  if (p.isLeaf() && !this.codes.containsKey(p.key)) {
		  throw new DecodeException(bits);
	  }
    return p.key;
  }
    
  /**
   * TODO
   * 
   * Returns the bit string associated with the given character. Must
   * search the tree for a leaf containing the character. Every left
   * turn corresponds to a 0 in the code. Every right turn corresponds
   * to a 1. This function is used by CodeBook to populate the map.
   * 
   * @throws EncodeException if the character does not appear in the tree.
   */
  public String lookup(char ch) {
    return this.codes.get(ch);
  }
  public void lookupHelper(Node p, String s) {
	  if (p.left == null && p.right == null) {
		  this.codes.put(p.key, s);
	  }
	  if (p.left != null) {
		  lookupHelper(p.left, s + "0");
	  }
	  if (p.right != null) {
		  lookupHelper(p.right, s + "1");
	  }
  }
  public static void main(String[] args) {
	  HuffmanTree ht = new HuffmanTree(new FrequencyTable("THE CAT IN THE HAT"));
	  System.out.println(ht.lookup('H'));
  }
}

