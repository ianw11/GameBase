package io.github.ianw11.gamebase.engine;

import java.util.ArrayList;
import java.util.List;

import io.github.ianw11.gamebase.turn.BasePlayer;
import io.github.ianw11.gamebase.turn.Turn;
import io.github.ianw11.gamebase.turn.TurnTree;

public abstract class BaseRulesEngine {
   
   private final List<GameStateListener> mGameStateListeners = new ArrayList<GameStateListener>();
   
   private final TurnTree mTurnTree = new TurnTree();
   private final PlayerOrderManager mPlayerOrderManager;
   private int mRoundNumber = 1;
   
   public BaseRulesEngine(final List<BasePlayer> players) {
      if (players.size() < getMinNumberOfPlayers() ||
            players.size() > getMaxNumberOfPlayers()) {
         throw new IllegalArgumentException("Illegal number of players");
      }
      
      mPlayerOrderManager = new PlayerOrderManager(players);
      addGameStateListener(mPlayerOrderManager);
   }
   
   
   /*
    * ABSTRACT methods to override
    */
   
   protected abstract int getMinNumberOfPlayers();
   protected abstract int getMaxNumberOfPlayers();
   
   /**
    * The main method to get implemented.  This provides a completely built Turn and lets
    * the implementation decide what to do with it to advance game state.
    * @param currentTurn The completely built Turn.
    * @return True if the Turn was legal, False otherwise.
    */
   protected abstract boolean processTurn(final Turn currentTurn);
   
   /**
    * The method that tells the round part of the game loop when to complete the round.
    * @return True if the current round is complete
    */
   protected abstract boolean isRoundOver();
   
   /**
    * The method that ultimately ends the game loop.
    * @return True if the game is over.
    */
   protected abstract boolean isGameOver();
   
   /*
    * MAIN PUBLIC METHOD
    */
   
   public void addGameStateListener(final GameStateListener listener) {
      mGameStateListeners.add(listener);
   }
   
   public void removeGameStateListener(final GameStateListener listener) {
      mGameStateListeners.remove(listener);
   }
   
   /**
    * Runs a game from start to finish.
    */
   public final void runGame() {
      onPreGameInit();
      
      while (!isGameOver()) {
         // Set up game for the current round
         onPreRound();
         
         // Run the round
         runRound();
         
         if (!isGameOver()) {
            onPostRound();
         }
      }
      
   }
   
   private final void runRound() {
      boolean isRoundOver = false;
      while (!isGameOver() && !isRoundOver) {
         // Take a turn and if legal go to the next player
         
         onPreTurn();
         
         // Run the turn and only call postTurn if successful
         if (turn()) {
            onPostTurn();
            isRoundOver = isRoundOver();
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
         mTurnTree.addTurn(currentTurn);
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
      return mPlayerOrderManager.getCurrentPlayer();
   }
   
   /**
    * Gets the number of players in the game
    * @return The number of players in the game
    */
   public final int getNumPlayers() {
      return mPlayerOrderManager.getNumPlayers();
   }
   
   /*
    * PRIVATE lifecycle methods
    */
   private final void onPreGameInit() {
      for (final GameStateListener listener : mGameStateListeners) {
         listener.onPreGameInit();
      }
   }
   
   private final void onPreRound() {
      for (final GameStateListener listener : mGameStateListeners) {
         listener.onPreRound();
      }
   }
   
   private final void onPostRound() {
      for (final GameStateListener listener : mGameStateListeners) {
         listener.onPostRound();
      }
   }
   
   private final void onPreTurn() {
      for (final GameStateListener listener : mGameStateListeners) {
         listener.onPreTurn();
      }
   }
   
   private final void onPostTurn() {
      for (final GameStateListener listener : mGameStateListeners) {
         listener.onPostTurn();
      }
   }
}
