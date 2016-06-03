package io.github.ianw11.gamebase.external;

import java.util.List;

import io.github.ianw11.gamebase.engine.BaseRulesEngine;
import io.github.ianw11.gamebase.engine.GameStateListener;
import io.github.ianw11.gamebase.turn.BasePlayer;

public abstract class CardGameRulesEngine extends BaseRulesEngine implements GameStateListener {
   
   private final int mMinNumPlayers;
   private final int mMaxNumPlayers;

   public CardGameRulesEngine(List<BasePlayer> players, final int minNumPlayers, final int maxNumPlayers) {
      super(players);
      
      mMinNumPlayers = minNumPlayers;
      mMaxNumPlayers = maxNumPlayers;
   }

   @Override
   protected int getMinNumberOfPlayers() {
      return mMinNumPlayers;
   }

   @Override
   protected int getMaxNumberOfPlayers() {
      return mMaxNumPlayers;
   }

}
