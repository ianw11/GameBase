package io.github.ianw11.gamebase.turn;

import java.util.ArrayList;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class TurnTree {

   private class TreeNode {
      private final Turn mSelf;
      private final TreeNode mPrevious;
      private final List<TreeNode> mNextTurns;
      
      public TreeNode(final Turn self, final TreeNode previous) {
         mSelf = self;
         mPrevious = previous;
         mNextTurns = new ArrayList<TreeNode>();
      }
      
      public void addNext(final TreeNode next) {
         mNextTurns.add(next);
      }
      
      public Turn getTurn() {
         return mSelf;
      }
      
      public TreeNode getPrev() {
         return mPrevious;
      }
      
      public TreeNode getNodeAt(final int i) {
         return mNextTurns.get(i);
      }
      
      /**
       * Recursive method to find the Node with the most options.
       * @return The number of options
       */
      public int getWidestNodeSize() {
         int max = mNextTurns.size();
         for (final TreeNode node : mNextTurns) {
            final int potential = node.getWidestNodeSize();
            if (potential > max) {
               max = potential;
            }
         }
         return max;
      }
   }
   
   
   private final TreeNode mHead;
   
   private TreeNode mCurrNode;
   
   public TurnTree() {
      mHead = new TreeNode(null, null);
      mCurrNode = mHead;
   }
   
   public void addTurn(final Turn turn) {
      final TreeNode node = new TreeNode(turn, mCurrNode);
      mCurrNode.addNext(node);
      mCurrNode = node;
   }
   
   public Turn rewind() {
      final Turn ret = mCurrNode.getTurn();
      mCurrNode = mCurrNode.getPrev();
      return ret;
   }
   
   public Turn advanceToNextAt(final int choice) {
      mCurrNode = mCurrNode.getNodeAt(choice);
      return mCurrNode.getTurn();
   }
   
   public int[] findPathToTurn(final Turn turn) {
      throw new NotImplementedException();
   }
   
   
}
