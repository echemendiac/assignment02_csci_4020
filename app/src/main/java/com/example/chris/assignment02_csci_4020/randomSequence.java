package com.example.chris.assignment02_csci_4020;

import java.util.Vector;
import java.util.Random;


public class randomSequence {
    private Vector sequence = new Vector();
    private int nextRand;
    private Random rand;

    //default constructor
    public randomSequence(){
        this.rand = new Random();
    }

    //get next random number, add to sequence, and return sequence
    public Vector getSequence(){
        this.nextRand = (this.rand.nextInt(4)+ 1);
        this.sequence.add(nextRand);
        return this.sequence;
    }
}
