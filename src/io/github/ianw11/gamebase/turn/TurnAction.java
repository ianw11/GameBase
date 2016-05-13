package io.github.ianw11.gamebase.turn;

import io.github.ianw11.gamebase.io.InputMethod;

public abstract class TurnAction {
   
   private static class TerminalAction extends TurnAction {
      @Override
      public TurnActionResult doAction(final InputMethod inputMethod) {
         throw new IllegalStateException("TERMINAL_ACTION should not be executed");
      }
      
      @Override
      public TurnAction getNextAction() {
         throw new IllegalStateException("No action after TERMINAL_ACTION");
      }
      
      @Override
      public String getTag() { return null; }
      @Override
      public Object getData() { return null; }
   }
   public static final TurnAction TERMINAL_ACTION = new TerminalAction();
   
   /**
    * Represents the state of an action during a turn.
    * 
    * If a user needs to input a piece to move and where to move
    * it, there should be 2 successes: one for the piece selection and
    * one for the destination selection.
    */
   public enum TurnActionResult {
      SUCCESS,
      RETRY,
      BACK,
      FAILURE;
   }
   
   public abstract TurnActionResult doAction(InputMethod inputMethod);
   
   public TurnAction getNextAction() {
      return TERMINAL_ACTION;
   }
   
   public abstract String getTag();
   public abstract Object getData();
}
