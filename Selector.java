import java.util.Arrays;

/**
* Defines a library of selection methods
* on arrays of ints.
*
* @author   Blake Schilleci (wbs0013@auburn.edu)
* 
* @version  2018-01-22
*
*/
public final class Selector {

   /**
    * Can't instantiate this class.
    *
    * D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
    *
    */
   private Selector() { }


   /**
    * Selects the minimum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int min(int[] a) {
   
      
      if ((a == null) || (a.length == 0)) {            
         
         throw new IllegalArgumentException();
         
      }
      
      else {
      
         int min = a[0];
      
      
         for (int i = 0; i < a.length; i++) {
               
            if (a[i] < min) {
            
               min = a[i];
            
            }
               
         }
      
         return min;
      
      }
   
   }


   /**
    * Selects the maximum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int max(int[] a) {
     
      
      if ((a == null) || (a.length == 0)) {            
         
         throw new IllegalArgumentException();
         
      }
      
      else {
      
         int max = a[0];
      
      
         for (int i = 0; i < a.length; i++) {
               
            if (a[i] > max) {
            
               max = a[i];
            
            }
               
         }
      
         return max;
      
      }
   
   }
      
   /**
    * Selects the kth minimum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth minimum value. Note that there is no kth
    * minimum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmin(int[] a, int k) {
      
      
      if ((a == null) || (a.length == 0)) {            
         
         throw new IllegalArgumentException();
        
      }
      
      if ((k > a.length) || (k < 1)) {
      
         throw new IllegalArgumentException();
      
      }
      
      int[] b = Arrays.copyOf(a, a.length);
      
      int sameValues = b.length;
               
      for (int i = 0; i < sameValues; i++) {
      
         for (int j = i + 1; j < sameValues; j++) {
         
            if (b[i] == b[j]) {
            
               b[j] = b[sameValues - 1]; 
            
               sameValues--;
            
               j--;
            }
         }
      }
      
      int[] bb = Arrays.copyOf(b, sameValues);
      
      Arrays.sort(bb);
      
      if (k > bb.length) {
      
         throw new IllegalArgumentException();
      
      }
   
      
      return bb[k - 1];
    
         
         
   }
      


   /**
    * Selects the kth maximum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth maximum value. Note that there is no kth
    * maximum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmax(int[] a, int k) {
   
      if ((a == null) || (a.length == 0)) {            
         
         throw new IllegalArgumentException();
        
      }
      
      if ((k > a.length) || (k < 1)) {
      
         throw new IllegalArgumentException();
      
      }
      
      int[] b = Arrays.copyOf(a, a.length);
      
      int sameValues = b.length;
               
      for (int i = 0; i < sameValues; i++) {
      
         for (int j = i + 1; j < sameValues; j++) {
         
            if (b[i] == b[j]) {
            
               b[j] = b[sameValues - 1]; 
            
               sameValues--;
            
               j--;
            }
         }
      }
      
      int[] bb = Arrays.copyOf(b, sameValues);
      
      Arrays.sort(bb);
      
      if (k > bb.length) {
      
         throw new IllegalArgumentException();
      
      }
      
      return bb[bb.length - k];
    
         
   }
   


   /**
    * Returns an array containing all the values in a in the
    * range [low..high]; that is, all the values that are greater
    * than or equal to low and less than or equal to high,
    * including duplicate values. The length of the returned array
    * is the same as the number of values in the range [low..high].
    * If there are no qualifying values, this method returns a
    * zero-length array. Note that low and high do not have
    * to be actual values in a. This method throws an
    * IllegalArgumentException if a is null or has zero length.
    * The array a is not changed by this method.
    */
   public static int[] range(int[] a, int low, int high) {
        
         
      if ((a == null) || (a.length == 0)) {            
         
         throw new IllegalArgumentException();
         
      }
      
      else {
      
         int length = 0;
      
         for (int i = 0; i < a.length; i++) {
         
            if (a[i] >= low && a[i] <= high) {
            
               length = length + 1;
            
            }
            
         }
         
         int x = 0;
         
         int[] range = new int[length];
      
         for (int j = 0; j < a.length; j++) {
         
            if (a[j] >= low && a[j] <= high) {
            
               range[x] = a[j];
            
               x++;
            
            }
         
         
         
         }
         
         return range;
      
      }
   
   
   }

   /**
    * Returns the smallest value in a that is greater than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int ceiling(int[] a, int key) {
   
      boolean foundKey = false;
      
      if ((a == null) || (a.length == 0)) {            
         
         throw new IllegalArgumentException();
         
      }  
      
      else {
      
         int min = a[0];
      
         for (int i = 0; i < a.length; i++) {
               
            if (a[i] >= key) {
            
               min = a[i];
               
               foundKey = true;
            
            }
               
         }
         
         for (int i = 0; i < a.length; i++) {
         
            if (a[i] < min && a[i] >= key) { 
            
               min = a[i];
            
            }
         
         }
         
         if (!foundKey) {
         
            throw new IllegalArgumentException();
         
         }
      
         return min;
      
      }   
      
   }


   /**
    * Returns the largest value in a that is less than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int floor(int[] a, int key) {
   
      boolean foundKey = false;
      
      if ((a == null) || (a.length == 0)) {            
         
         throw new IllegalArgumentException();
         
      }
        
      
      else {
      
         int max = a[0];
      
               
         for (int i = 0; i < a.length; i++) {
               
            if (a[i] <= key) {
            
               max = a[i];
               
               foundKey = true;
            
            }
               
         }
         
         for (int i = 0; i < a.length; i++) {
         
         
            if (a[i] > max && a[i] <= key) {
            
               max = a[i];
            
            }
         
         }
         
         if (!foundKey) {
         
            throw new IllegalArgumentException();
         
         }
      
         return max;
      
      }   
      
   }
   
}