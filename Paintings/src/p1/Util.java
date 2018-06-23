package p1;

import java.awt.Color;

import java.util.*;

import p1.ColorTable.Colorfreq;

public class Util {
  
  /*
   * TODO
   * 
   * Compute the cosine similarity using the formula given here: 
   * 
   *    https://en.wikipedia.org/wiki/Cosine_similarity
   *    
   * Hints:
   * -- The result of multiplying in the dot product should be of type double. 
   * -- Consider defining two private helper methods: one to compute the dot product 
   *    and one to find the vector magnitude.
   * -- Use a ColorTable.iterator() to traverse the vector of frequency counts in the
   *    color table.
   */
  public static double cosineSimilarity(ColorTable A, ColorTable B) {
    double dotProduct = 0;
    double magA = 0;
    double magB = 0;
    Iterator iteratorA = A.iterator();
    Iterator iteratorB = B.iterator();
    //System.out.println(iteratorA.hasNext());
    int szA = A.getCapacity();
    int szB = B.getCapacity();
    if (szA < szB) {
    while(iteratorA.hasNext()) {
        long aVal = iteratorA.next();
        long bVal = iteratorB.next();
        dotProduct += aVal * bVal;
        magA += Math.pow(aVal, 2);
        magB += Math.pow(bVal, 2);
    } 
    }
    else {
    	while (iteratorB.hasNext()) {
    		long aVal = iteratorA.next();
            long bVal = iteratorB.next();
            dotProduct += aVal * bVal;
            magA += Math.pow(aVal, 2);
            magB += Math.pow(bVal, 2);
    	}
    }
    return dotProduct / (Math.sqrt(magA) * Math.sqrt(magB));
  }
 
  /**
   * Returns true iff n is a prime number. We handles several common cases quickly, and then 
   * use a variation of the Sieve of Eratosthenes.
   */
  public static boolean isPrime(int n) {
    if (n < 2) 
      return false;
    if (n == 2 || n == 3) 
      return true;
    if (n % 2 == 0 || n % 3 == 0) 
      return false;
    long sqrtN = (long) Math.sqrt(n) + 1;
    for (int i = 6; i <= sqrtN; i += 6) {
      if (n % (i - 1) == 0 || n % (i + 1) == 0) 
        return false;
    }
    return true;
  }
    
  /**
   * The 3 components of a Color are packed into one 32-bit int. The result
   * is used as a hash code for Colors in the ColorTable.
   * 
   * Each color component occupies exactly bitsPerChanel bits in the encoding.
   * 
   * The color components are shifted to the right to drop the lower order bits.
   * Then the three reduced color components are packed into the lower order 
   * 3 * bitsPerChannel bits of the returned code.
   */
  public static int pack(Color color, int bitsPerChannel) {
    int r = color.getRed(), g = color.getGreen(), b = color.getBlue(); 
    if (bitsPerChannel >= 1 && bitsPerChannel <= 8) {
      int leftovers = 8 - bitsPerChannel;
      int mask = (1 << bitsPerChannel) - 1; // In binary, this is bitsPerChannel ones.
      // Isolate the higher bitsPerChannel bits of each color component byte by
      // shifting right and masking off the higher order bits.
      r >>= leftovers; 
      r &= mask;
      g >>= leftovers; 
      g &= mask;
      b >>= leftovers; 
      b &= mask;
      // Finally, pack the color components into an int by left shifting into position
      // and xor-ing together.
      return (((r << bitsPerChannel) ^ g) << bitsPerChannel) ^ b;
    }
    else {
      throw new RuntimeException(String.format("Unsupported number of bits per channel: %d",
          bitsPerChannel));
    }
  }
  
  /**
   * Undoes the last step in the pack operation to reconstitute the code into a Color object.
   */
  public static Color unpack(int code, int bitsPerChannel) {
    int r = code, g = code, b = code;
    if (bitsPerChannel >= 1 && bitsPerChannel <= 8) {
      int mask = (1 << bitsPerChannel) - 1; // In binary, this is bitsPerChannel ones.
      int leftovers = 8 - bitsPerChannel;
      // Isolate the higher bitsPerChannel bits of each color component byte.
      b &= mask;
      b <<= leftovers;
      g >>= bitsPerChannel;
      g &= mask;
      g <<= leftovers;
      r >>= 2 * bitsPerChannel;
      r &= mask;
      r <<= leftovers;
      return new Color(r, g, b);
    }
    else 
      throw new RuntimeException("Unsupported number of bits per channel; use an int in the range [1..8]");    
  }
  // Convert the color to bitsPerChannel
  /*
   * Examples:
   * bitsPerchannel = 1
   * color = (0, 0, 150);
   * color should equal (0, 0, 255);
   * bitsPerChannel = 2
   * color = (0, 34, 70)
   * color should equal(0, 0, 127);
   * Take 2 ^ (9 - bitsPerChannel)
   */
  // Grabs every number in between 0 and 255 given bitsPerChannel
  // 1 bit means 0x80, (2) 0x40, (3) 0x20, (4) 0x10, (5) 0x08, (6) 0x04, (7) 0x02, (8) 0x01
  public static int numsWithZero(int num, int bitsPerChannel) {
   int incrementor = (int) (Math.pow(2, 8 - bitsPerChannel));
   int maxThreshold = 256 - incrementor;
   int min = 0;
   int max = incrementor;
   while (max <= maxThreshold && num >= min && num >= max) {
   min += incrementor;
   max += incrementor;
   }
   return min;
  }
  public static Color colorToBits(Color color, int bitsPerChannel) {
   int red = color.getRed();
   int green = color.getGreen();
   int blue = color.getBlue();
   red = numsWithZero(red, bitsPerChannel);
   green = numsWithZero(green, bitsPerChannel);
   blue = numsWithZero(blue, bitsPerChannel);
   color = new Color(red, green, blue);
   return color;
  }

  /**
   * Simple testing.
   */
  public static void main(String[] args) {
    System.out.println(isPrime(Constants.MAX_CAPACITY));
    int j = 536870896;
    System.out.println(Constants.MAX_CAPACITY == 4 * j + 3);
        
    int black = pack(Color.BLACK, 6);
    System.out.println("black encoded in " + (3 * 6) + " bits: " + black);
    int white = pack(Color.WHITE, 8);
    System.out.println("white encoded in " + (3 * 8) + " bits: " + white);
    white = pack(Color.WHITE, 1);
    System.out.println("white encoded in " + (3 * 1) + " bits: " + white);
    int green = pack(Color.GREEN, 3);
    System.out.println("green encoded in " + (3 * 3) + " bits: " + green);
    green = pack(Color.GREEN, 4);
    System.out.println("green encoded in " + (3 * 4) + " bits: " + green);
    System.out.println(unpack(green, 4));
    Color col = colorToBits(Color.RED, 8);
    System.out.println(col + ", " + Color.RED);
    ColorTable table = new ColorTable(13, 2, Constants.LINEAR, 0.49);
    for (int i = 0; i < 20 * 17; i += 17)
      table.put(new Color(i * i),  i);
  }
}
