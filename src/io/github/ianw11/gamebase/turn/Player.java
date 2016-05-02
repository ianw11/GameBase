package io.github.ianw11.gamebase.turn;

import io.github.ianw11.gamebase.io.InputMethod;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class Player {

   private final int mId;
   private final String mName;
   private final InputMethod mInputMethod;
   
   public Player(final int id, final String name, final InputMethod inputMethod) {
      mId = id;
      mName = name;
      mInputMethod = inputMethod;
   }
   
   public String getName() {
      return mName;
   }
   
   /**
    * Method used to determine the correct initial action to take.
    * Left abstract so the implementer can change the initial action as needed during the game
    * @return The TurnAction that will be executed first
    */
   protected abstract TurnAction getInitialTurnAction();
   
   /**
    * Creates and executes a new Turn, querying necessary input from a user
    * @param gameTurnNumber The current turn of the game
    * @return A Turn object for the RulesEngine to process
    */
   public final Turn getNextTurn(final int gameTurnNumber) {
      final Turn currentTurn = new Turn(gameTurnNumber, this);
      final boolean success = currentTurn.execute();
      if (!success) {
         // TODO: Need to decide what will go here
         throw new NotImplementedException();
      }
      return currentTurn;
   }
   
   protected final InputMethod getInputMethod() {
      return mInputMethod;
   }
}
