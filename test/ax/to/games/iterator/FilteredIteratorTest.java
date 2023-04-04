package ax.to.games.iterator;

import java.util.Vector;

import to.ax.games.util.Filter;
import to.ax.games.util.FilteredIterator;

import junit.framework.TestCase;

/**
 * @author Tom Ritchford (tom@swirly.com)
 *
 */
public class FilteredIteratorTest extends TestCase {
  public Vector<Integer> doTestFilter(Filter<Integer> filter, int input[]) {
    Vector<Integer> in = new Vector<Integer>();
    for (int i: input) 
      in.add(i);
    Vector<Integer> out = new Vector<Integer>();
    for (Integer i: new FilteredIterator<Integer>(filter, in.iterator())) 
      out.add(i);
    return out;
  }
  
  public Vector<Integer> doTestFilter(Filter<Integer> filter) {
    return doTestFilter(filter, new int[] { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 });
  }

  public void testFilteredIterator() {
    Filter<Integer> all = new Filter<Integer>() {
      public boolean accepts(Integer item) {
        return true;
      }      
    };
    Filter<Integer> none = new Filter<Integer>() {
      public boolean accepts(Integer item) {
        return false;
      }      
    };
    Filter<Integer> even = new Filter<Integer>() {
      public boolean accepts(Integer item) {
        return (item % 2) == 0;
      }      
    };
    
    assertEquals(10, doTestFilter(all).size());
    assertEquals(0, doTestFilter(none).size());
    assertEquals(5, doTestFilter(even).size());
    assertEquals(8, doTestFilter(even).get(0).intValue());
  }
}
