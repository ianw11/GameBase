package io.github.ianw11.gamebase.engine;

import java.util.List;

import io.github.ianw11.gamebase.turn.BasePlayer;

class PlayerOrderManager implements GameStateListener {
   
   private final BasePlayer[] mTableOrder;
   
   private final int mNumPlayers;
   
   private int mRoundStarter = 0;
   private int mCurrentPlayerNdx = 0;

   public PlayerOrderManager(final List<BasePlayer> players) {
      mNumPlayers = players.size();
      mTableOrder = players.toArray(new BasePlayer[mNumPlayers]);
   }
   
   public BasePlayer getCurrentPlayer() {
      return mTableOrder[mCurrentPlayerNdx];
   }
   
   public int getNumPlayers() {
      return mNumPlayers;
   }

   
   @Override
   public void onPreGameInit() { }

   @Override
   public void onPreRound() { }

   @Override
   public void onPostRound() {
      ++mRoundStarter;
      if (mRoundStarter == mTableOrder.length) {
         mRoundStarter = 0;
      }
      mCurrentPlayerNdx = mRoundStarter;
   }

   @Override
   public void onPreTurn() { }

   @Override
   public void onPostTurn() {
      mCurrentPlayerNdx++;
      if (mCurrentPlayerNdx == mTableOrder.length) {
         mCurrentPlayerNdx = 0;
      }
   }

}
