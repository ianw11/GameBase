package io.github.ianw11.gamebase.engine;

public class UtilityFunctions {
   
   /**
    * Shuffle an array in place with Fisher-Yates
    * @param array The array to shuffle
    */
   public static <K> void shuffleArray(final K[] array) {
      final int arrayLength = array.length;
      final Random random = Random.getInstance();
      for (int i = 0; i < arrayLength; ++i) {
         final int index = random.nextInt(arrayLength);
         
         // Swap
         final K holder = array[index];
         array[index] = array[i];
         array[i] = holder;
      }
   }
   
}
