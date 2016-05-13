package io.github.ianw11.gamebase.pieces;

import io.github.ianw11.gamebase.engine.Random;

public abstract class NSidedDie {

   private final int mNumSides;
   private final Random mRandom;
   
   private int mValue;
   
   public NSidedDie(final int numSides) {
      mNumSides = numSides;

      mRandom = Random.getInstance();
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
