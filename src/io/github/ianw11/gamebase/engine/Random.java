package io.github.ianw11.gamebase.engine;

public class Random {
   
   private final java.util.Random mRandom;
   private final int mSeed;
   
   private static Random mInstance;
   
   public static Random getInstance() {
      if (mInstance == null) {
         mInstance = new Random();
      }
      
      return mInstance;
   }
   
   private Random() {
      mRandom = new java.util.Random();
      mSeed = mRandom.nextInt();
      
      reset();
   }
   
   public void reset() {
      mRandom.setSeed(mSeed);
   }
   
   public int nextInt() {
      return mRandom.nextInt();
   }
   
   public int nextInt(final int bound) {
      return mRandom.nextInt(bound);
   }

}
