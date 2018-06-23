import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Comparator;

/**
 * Jeremy Sommers
 * TODO: Complete the implementation of this class.
 * 
 * The keys in the heap must be stored in an array.
 * 
 * There may be duplicate keys in the heap.
 * 
 * The constructor takes an argument that specifies how objects in the 
 * heap are to be compared. This argument is a java.util.Comparator, 
 * which has a compare() method that has the same signature and behavior 
 * as the compareTo() method found in the Comparable interface. 
 * 
 * Here are some examples of a Comparator<String>:
 *    (s, t) -> s.compareTo(t);
 *    (s, t) -> t.length() - s.length();
 *    (s, t) -> t.toLowerCase().compareTo(s.toLowerCase());
 *    (s, t) -> s.length() <= 3 ? -1 : 1;  
 */

public class Heap<E> implements PriorityQueue<E> {
  protected List<E> keys = new LinkedList<E>();
  private Comparator<E> comparator;
  private E[] array;
  
  private final int MAX_CAPACITY = 100;
  
  /**
   * TODO
   * 
   * Creates a heap whose elements are prioritized by the comparator.
   */
  public Heap(Comparator<E> comparator) {
    this.keys = keys;
    this.comparator = comparator;
    this.array = (E[]) new Object[MAX_CAPACITY];
  }

  /**
   * Returns the comparator on which the keys in this heap are prioritized.
   */
  public Comparator<E> comparator() {
    return comparator;
  }

  /**
   * TODO
   * 
   * Returns the top of this heap. This will be the highest priority key. 
   * @throws NoSuchElementException if the heap is empty.
   */
  public E peek() {
	  if (this.isEmpty())
		  throw new NoSuchElementException();
	  else {
		  return this.keys.get(0);
	  }
  }

  /**
   * TODO
   * 
   * Inserts the given key into this heap. Uses siftUp().
   */
  public void insert(E key) {
	  if (key == null)
		  throw new NoSuchElementException();
	  else {
	  this.keys.add(key);
	  for (int i = 0; i < this.keys.size(); i++) {
		  this.array[i] = this.keys.get(i);
	  }
	  siftUp(this.size() - 1);
	  }
  }

  /**
   * TODO
   * 
   * Removes and returns the highest priority key in this heap.
   * @throws NoSuchElementException if the heap is empty.
   */
  public E delete() {
	  if (this.keys.isEmpty())
		  throw new NoSuchElementException();
	  else {
         E ans = this.peek();
         swap(keys.size() - 1, 0);
         this.keys.remove(keys.size() - 1);
         this.array[keys.size()] = null;
         siftDown(0);
         return ans;
	  }
  }

  /**
   * TODO
   * 
   * Restores the heap property by sifting the key at position p down
   * into the heap.
   */
  public void siftDown(int p) {
	  if (!this.isEmpty())
		  siftDownHelper(this.array, p);
  }
  private void siftDownHelper(E[] a, int p) {
	    int leftChild = getLeft(p);
	    if (leftChild < this.keys.size()) {
	      int maxChild = leftChild;
	      int rightChild = leftChild + 1;
	      if (rightChild < this.keys.size() && 
this.comparator.compare(a[rightChild], a[maxChild]) < 0)
	        maxChild = rightChild;
	      if (this.comparator.compare(a[maxChild], a[p]) < 0) {
	        this.swap(p, maxChild);
	        siftDownHelper(a, maxChild);
	      }
	    }
	  }
  
  /**
   * TODO
   * 
   * Restores the heap property by sifting the key at position q up
   * into the heap. (Used by insert()).
   */
  public void siftUp(int q) {
	  if (!this.isEmpty())
		  siftUpHelper(q, 0);
	  }
	  //finish
  private void siftUpHelper(int q, int n) {
	  int parent = getParent(q);
	  if (parent >= 0) {
		  if (this.comparator.compare(this.array[parent], this.array[q]) > 0) {
			  this.swap(parent, q);
			  siftUpHelper(parent, n);
		  }
			  
	  }
  }

  /**
   * TODO
   * 
   * Exchanges the elements in the heap at the given indices in keys.
   */
  public void swap(int i, int j) {
    swapHelper(this.array, i, j);
  }
  private void swapHelper(E[] a, int i, int j) {
	  Collections.swap(this.keys, i, j);
	  E temp = a[i];
	  a[i] = a[j];
	  a[j] = temp;
  }
  
  /**
   * Returns the number of keys in this heap.
   */
  public int size() {
    return keys.size();
  }

  /**
   * Returns a textual representation of this heap.
   */
  public String toString() {
    return keys.toString();
  }
  
  /**
   * TODO
   * 
   * Returns the index of the left child of p.
   */
  public static int getLeft(int p) {
    return 2 * p + 1;
  }

  /**
   * TODO
   * 
   * Returns the index of the right child of p.
   */
  public static int getRight(int p) {
    return getLeft(p) + 1;
  }

  /**
   * TODO
   * 
   * Returns the index of the parent of p.
   */
  public static int getParent(int p) {
    return (p - 1) / 2;
  }
  
  public static void main(String[] args) {
	  
  }
}