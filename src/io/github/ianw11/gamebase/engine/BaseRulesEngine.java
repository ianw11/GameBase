package io.github.ianw11.gamebase.engine;

import java.util.ArrayList;
import java.util.List;

import io.github.ianw11.gamebase.turn.BasePlayer;
import io.github.ianw11.gamebase.turn.Turn;

public abstract class BaseRulesEngine {
   
   /**
    * The Players currently in the game
    */
   private List<BasePlayer> mPlayers;
   private boolean mPlayersAreSet = false;
   
   /**
    * Stores the series of turns this game has taken.
    * 
    * This can be used for replays or restoring a specific state in the game.
    */
   private List<Turn> mSuccessfulTurns;
   
   private int mPlayerTurn = 0;
   private int mRoundNumber = 1;
   private BasePlayer[] mPlayerOrder; 
   
   public BaseRulesEngine() {
   }
   
   public void setPlayers(final List<BasePlayer> players) {
      if (players.size() < getMinNumberOfPlayers() ||
            players.size() > getMaxNumberOfPlayers()) {
         throw new IllegalArgumentException("Illegal number of players");
      }
      mPlayers = new ArrayList<BasePlayer>(players);
      mSuccessfulTurns = new ArrayList<Turn>();
      
      mPlayerOrder = new BasePlayer[players.size()];
      mPlayers.toArray(mPlayerOrder);
      
      mPlayersAreSet = true;
   }
   
   
   /*
    * ABSTRACT methods to override
    */
   
   protected abstract int getMinNumberOfPlayers();
   protected abstract int getMaxNumberOfPlayers();
   
   protected abstract boolean processTurn(final Turn currentTurn);
   
   protected abstract void preGameInit();
   protected abstract void preRound();
   protected abstract void postRound();
   protected abstract void preTurn();
   protected abstract void postTurn();
   
   public abstract boolean isGameOver();
   
   public final void runGame() {
      if (!mPlayersAreSet) {
         throw new IllegalStateException("Set players before starting game");
      }
      
      preGameInit();
      
      while (!isGameOver()) {
         // Set up game for the current round
         preRound();
         
         // Run the round
         runRound();
         
         if (!isGameOver()) {
            postRound();
         }
      }
      
   }
   
   private final void runRound() {
      mPlayerTurn = 0;
      
      while (!isGameOver() && mPlayerTurn != mPlayers.size()) {
         // Take a turn and if legal go to the next player
         
         preTurn();
         if (turn()) {
            ++mPlayerTurn;
            postTurn();
         }
      }
      
      ++mRoundNumber;
   }

   /**
    * The main method of the RulesEngine.
    * Each call to this method will create a Turn, query necessary information from
    * the Player, then process the Turn according to some implementation.
    * 
    * @return True if the action was legal
    */
   private final boolean turn() {
      final Turn currentTurn = getCurrentPlayer().getNextTurn(mRoundNumber);
      
      boolean isLegalAction = processTurn(currentTurn);
      if (isLegalAction) {
         mSuccessfulTurns.add(currentTurn);
      }
      
      return isLegalAction;
   }
   
   
   /*
    * PUBLIC GETTER methods to get information about the game
    */
   
   /**
    * Gets the current player in the game
    * @return The current player
    */
   public final BasePlayer getCurrentPlayer() {
      return mPlayerOrder[mPlayerTurn];
   }
   
   /**
    * Gets the series of turns that created the current game state.
    * 
    * @return List of successful Turns
    */
   public final List<Turn> getTurns() {
      return new ArrayList<Turn>(mSuccessfulTurns);
   }
   
   /*
    * PUBLIC methods to manipulate game state
    */
   
   public final void shufflePlayerOrder() {
      UtilityFunctions.shuffleArray(mPlayerOrder);
   }
   
   /**
    * Completely resets player turn order to the original order
    */
   public final void resetPlayerOrder() {
      resetPlayerOrder(0);
   }
   
   /**
    * Sets the player at the given index to be the first/next player and follows original order
    * from that point.
    * @param newStarterIndex The index/id of the new first/next player.
    */
   public final void resetPlayerOrder(final int newStarterIndex) {
      final int numPlayers = mPlayers.size();
      int currentPlayerIndex = newStarterIndex;
      for (int i = 0; i < numPlayers; ++i) {
         mPlayerOrder[i] = mPlayers.get(currentPlayerIndex++);
         
         if (currentPlayerIndex == numPlayers) {
            currentPlayerIndex = 0;
         }
      }
   }
}
