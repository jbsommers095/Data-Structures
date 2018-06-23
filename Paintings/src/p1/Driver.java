package p1;

import java.awt.Color;

/**
 * TODO
 * @author Jeremy Sommers
 */

public class Driver {
  
  private static int numCollisions;
  
  /**
   * TODO
   * 
   * Return the ColorTable associated with this image, assuming the color key space
   * is restricted to bitsPerChannel. Increment numCollisions after each increment.
   */
  public static ColorTable vectorize(Image image, int bitsPerChannel) {
   int imgWidth = image.getWidth();
   int imgHeight = image.getHeight();
   ColorTable table = new ColorTable(1, bitsPerChannel, Constants.QUADRATIC, 0.49);
   for (int y = 0; y < imgHeight; y+=10) {
	   for (int x = 0; x < imgWidth; x+=10) {
     Color color = Util.colorToBits(image.getColor(x, y), bitsPerChannel);
     table.increment(color);
     numCollisions++;
	   }
   }
    return table;
  }

  /**
   * TODO
   * 
   * Return the result of running Util.cosineSimilarity() on the vectorized images.
   * 
   * Note: If you compute the similarity of an image with itself, it should be close to 1.0.
   */
  public static double similarity(Image image1, Image image2, int bitsPerChannel) {
   ColorTable A = vectorize(image1, bitsPerChannel);
   ColorTable B = vectorize(image2, bitsPerChannel);
    return Util.cosineSimilarity(A, B);
  }
  /**
   * Uses the Painting images and all 8 bitsPerChannel values to compute and print 
   * out a table of collision counts.
   */
  public static void allPairsTest() {
    Painting[] paintings = Painting.values();
    int n = paintings.length;
    for (int y = 0; y < n; y++) {
      for (int x = y + 1; x < n; x++) {
        System.out.println(paintings[y].get().getName() + 
            " and " + 
            paintings[x].get().getName() + ":");
        for (int bitsPerChannel = 1; bitsPerChannel <= 8; bitsPerChannel++) {
          numCollisions = 0;
          System.out.println(String.format("   %d: %.2f %d", 
              bitsPerChannel,
              similarity(paintings[x].get(), paintings[y].get(), bitsPerChannel),
              numCollisions));
        }
        System.out.println();
      }
    }
  }

  /**
   * Simple testing
   */  
  public static void main(String[] args) {
    System.out.println(Constants.TITLE);
    Image mona = Painting.MONA_LISA.get();
    Image starry = Painting.STARRY_NIGHT.get();
    Image christina = Painting.CHRISTINAS_WORLD.get();
    Image degas = Painting.BLUE_DANCERS.get();
    Image picasso = new Image("images/picasso.jpg");
    System.out.println("It looks like all three test images were successfully loaded.");
    System.out.println("mona's dimensions are " + 
        mona.getWidth() + " x " + mona.getHeight());
    System.out.println("starry's dimenstions are " + 
        starry.getWidth() + " x " + starry.getHeight());
    System.out.println("christina's dimensions are " + 
        christina.getWidth() + " x " + christina.getHeight());
    System.out.println(picasso.getWidth() + ", " + picasso.getHeight());
    System.out.println(degas.getWidth() + ", " + degas.getHeight());
    ColorTable A = vectorize(picasso, 8);
    ColorTable B = vectorize(degas, 8);
    ColorTable C = vectorize(mona, 8);
    ColorTable D = vectorize(starry, 8);
    System.out.println(similarity(mona, starry, 8));
    System.out.println(similarity(picasso, degas, 8));
    System.out.println(similarity(starry, mona, 8));
  }
}
