package io.github.ianw11.gamebase.pieces;

import java.util.Random;

public abstract class NSidedDie {

   private final int mNumSides;
   private final Random mRandom;
   
   private int mValue;
   
   public NSidedDie(final int numSides) {
      this(numSides, 0);
   }
   public NSidedDie(final int numSides, final int seed) {
      mNumSides = numSides;
      if (seed == 0) {
         mRandom = new Random(System.currentTimeMillis());
      } else {
         mRandom = new Random(seed);
      }
      
      roll();
   }
   
   /**
    * Rolls the die and stores the result
    */
   public final int roll() {
      mValue = mRandom.nextInt(mNumSides) + 1;
      return mValue;
   }
   
   /**
    * Gives the value of the die since it was last rolled
    * @return The current value of the die
    */
   public final int getValue() {
      return mValue;
   }
   
   /**
    * Gets the number of sides this die has
    * @return The number of sides/faces
    */
   public final int getNumSides() {
      return mNumSides;
   }
}
