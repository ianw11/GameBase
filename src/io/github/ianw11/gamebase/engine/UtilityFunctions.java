package io.github.ianw11.gamebase.engine;

import java.util.Random;

public class UtilityFunctions {
   
   /**
    * Shuffle an array in place with Fisher-Yates
    * @param array The array to shuffle
    */
   public static void shuffleArray(final Object[] array) {
      final int arrayLength = array.length;
      final Random random = new Random(System.currentTimeMillis());
      for (int i = 0; i < arrayLength; ++i) {
         final int index = random.nextInt(arrayLength);
         
         // Swap
         final Object holder = array[index];
         array[index] = array[i];
         array[i] = holder;
      }
   }
   
}
