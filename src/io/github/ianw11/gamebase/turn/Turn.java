package io.github.ianw11.gamebase.turn;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import io.github.ianw11.gamebase.turn.TurnAction.TurnActionResult;

public class Turn {
   private boolean isTurnSuccessful;
   
   // The current action a Turn will execute
   private TurnAction mCurrentTurnAction;
   private final Stack<TurnAction> mPreviousTurnActions;
   
   /**
    * Stores the turn number of the game
    */
   private final int mGameTurn;
   /**
    * Player owning this turn
    */
   private final BasePlayer mPlayer;
   
   /**
    * All data collected from Player
    */
   private final Map<String, TurnAction> mSavedData;
   
   /**
    * CONSTRUCTOR for a Turn
    * @param gameTurn The turn number this represents
    * @param inputMethod How this turn will collect data from the player
    */
   public Turn(final int gameTurn, final BasePlayer player) {
      mCurrentTurnAction = player.getInitialTurnAction();
      mPreviousTurnActions = new Stack<TurnAction>();
      isTurnSuccessful = true;
      
      mGameTurn = gameTurn;
      mPlayer = player;
      
      mSavedData = new HashMap<String, TurnAction>();
   }
   
   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();
      
      sb.append("Player " + mPlayer.getName() + "'s turn " + mGameTurn);
      for (final String tag : mSavedData.keySet()) {
         final TurnAction action = mSavedData.get(tag);
         sb.append(" || ");
         sb.append(tag + " -> " + action.getData());
      }
      
      return sb.toString();
   }
   
   /**
    * Executes the series of TurnActions.  Once the turn is completed (TurnAction.TERMINAL_ACTION is current)
    * the state will be locked in and subsequent calls will simply return with the same result.
    * 
    * @return True if the turn was a success
    */
   public final boolean execute() {
      while (mCurrentTurnAction != TurnAction.TERMINAL_ACTION) {
         isTurnSuccessful |= updateTurnState(mCurrentTurnAction.doAction(mPlayer.getInputMethod()));
      }
      return isTurnSuccessful;
   }
   
   /**
    * Gets the player who is taking this turn.
    * @return The Player taking this turn.
    */
   public final BasePlayer getPlayer() {
      return mPlayer;
   }
   
   /**
    * Returns the data stored in a TurnAction
    * @param tag The TurnAction's tag
    * @return The data contained in the TurnAction
    */
   public final Object getTurnData(final String tag) {
      final TurnAction action = mSavedData.get(tag);
      if (action == null) {
         throw new IllegalArgumentException("Tag " + tag + " does not exist in TURN");
      }
      return action.getData();
   }
   
   private final boolean updateTurnState(final TurnActionResult result) {
      switch (result) {
      case SUCCESS:
         // Save this data point
         mSavedData.put(mCurrentTurnAction.getTag(), mCurrentTurnAction);
         
         // Update Turn state
         mPreviousTurnActions.push(mCurrentTurnAction);
         mCurrentTurnAction = mCurrentTurnAction.getNextAction();
         
         return true;
      case FAILURE:
         // Terminate this Turn
         mCurrentTurnAction = TurnAction.TERMINAL_ACTION;
         return false;
      case BACK:
         // Remove this TurnAction from the saved data
         mSavedData.remove(mCurrentTurnAction.getTag());
         
         // Update Turn state
         mCurrentTurnAction = mPreviousTurnActions.pop();
         // Fall through to return
      case RETRY:
         return false;
      default:
         throw new IllegalStateException("Illegal TurnActionResult for Turn");
      }
   }
}
