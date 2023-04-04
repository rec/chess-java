package com.editev.chess;


public class GameTree extends Chess {//extends Lists {
    public final GameTree parent;
    public final short        move;

    long timeOfLastUse;
    public long getTimeOfLastUse() { return timeOfLastUse;                       }
    public void use()              { timeOfLastUse = System.currentTimeMillis(); }
    
    GameHistory game;
    
    public GameTree(                     ) { this( null, NO_MOVE );          }
    public GameTree( GameTree parent, short move ) { 
        this.parent = parent; 
        this.move = move; 
        use(); 
    }
}