package io.github.ianw11.gamebase.engine;

public interface GameStateListener {
   public void onPreGameInit();
   public void onPreRound();
   public void onPostRound();
   public void onPreTurn();
   public void onPostTurn();
}
