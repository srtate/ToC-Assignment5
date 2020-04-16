import java.util.*;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.PrintStream;

/**
 *  This is a class to work with DFAs over a binary alphabet. It was
 *  created as a starting point for an assignment in the Spring 2020
 *  Theory of Computing class at UNCG (CSC 452/652), and probably
 *  isn't useful for much outside of that.
 *
 *  @author Stephen Tate
 */
public class DFA {
    private int[][] trans; // Transition function (for n states this is [n][2])
    private boolean[] isAccept;  // For each state, true if an accepting state

    /**
     * An inner class for exceptions when reading a DFA.
     */
    public static class InputFormatException extends Exception {
	public InputFormatException(String msg) {
	    super(msg);
	}
    }

    /**
     * This is an internal constructor that simply allocates arrays for
     * a DFA with nstates states. This is useful if you are
     * constructing a new DFA within this class.
     *
     * @param nstates The number of states in the new DFA.
     */
    private DFA(int nstates) {
	// For internal use only.
	trans = new int[nstates][2];
	isAccept = new boolean[nstates];
    }

    /**
     * Constructs a DFA object from an input stream. Input is a
     * standard text file with the following data: The first line
     * contains two numbers, the number of states (n) and the number
     * of accepting states (f). This is followed by n lines that each
     * describe the transition function leaving that state with 3
     * numbers: First the current state number (states are numbered 0
     * through n-1), then the state that is transitioned to with input
     * 0, and then the state that is transitioned to with input
     * 1. Finally, after all transitions are defined, there is a line
     * listing the f accepting states. Note that the start state is
     * always state 0. This constructor does rudimentary "sanity
     * checking" on the input, and throws a DFA.InputFormatException
     * exception if there is a problem.
     *
     * @param in An input stream (can be file or stdin)
     */
    public DFA(InputStream in) throws InputFormatException {
	Scanner sin = new Scanner(in);
	int nstates = sin.nextInt();
	int naccept = sin.nextInt();
	trans = new int[nstates][2];
	isAccept = new boolean[nstates];
	for (int i=0; i<nstates; i++)
	    trans[i][0] = -1;
	for (int i=0; i<nstates; i++) {
	    int s = sin.nextInt();
	    if ((s < 0) || (s >= nstates) || (trans[s][0] != -1))
		throw new InputFormatException("Bad state number "+s);
	    trans[i][0] = sin.nextInt();
	    trans[i][1] = sin.nextInt();
	}
	for (int i=0; i<naccept; i++) {
	    int s = sin.nextInt();
	    if ((s < 0) || (s >= nstates))
		throw new InputFormatException("Bad accept state "+s);
	    isAccept[s] = true;
	}
    }

    /**
     * Prints out a text description of a DFA object, in the same format
     * as described for the input constructor above.
     *
     * @param out Print stream for output
     */
    public void print(PrintStream out) {
	int nstates = this.isAccept.length;
	int naccept = 0;
	for (int i=0; i<nstates; i++)
	    naccept += (isAccept[i]?1:0);
	out.println(nstates+" "+naccept);
	for (int i=0; i<nstates; i++)
	    out.println(i+" "+trans[i][0]+" "+trans[i][1]);
        boolean firstPrint = true;
	for (int i=0; i<nstates; i++) {
	    if (isAccept[i]) {
		if (firstPrint) {
		    firstPrint = false;
		    out.print(i);
		} else {
		    out.print(" "+i);
		}
	    }
	}
	out.println();
    }
    
    /**
     * Tests if "this" DFA recognizes the same language as the "other" DFA.
     */
    public boolean isEquivalentTo(DFA other) {
	// Fill this in!
	return true;
    }

    public static void main(String argv[]) {
	DFA dfa1;
	try {
	    dfa1 = new DFA(new FileInputStream("dfa1.in"));
	} catch (java.io.FileNotFoundException e) {
	    System.err.println("Could not open file dfa1.in");
	    return;
	} catch (InputFormatException e) {
	    System.err.println("Input format error in dfa1: "+e.getMessage());
	    return;
	}

	DFA dfa2;
	try {
	    dfa2 = new DFA(new FileInputStream("dfa2.in"));
	} catch (java.io.FileNotFoundException e) {
	    System.err.println("Could not open file dfa2.in");
	    return;
	} catch (InputFormatException e) {
	    System.err.println("Input format error in dfa2: "+e.getMessage());
	    return;
	}

	if (dfa1.isEquivalentTo(dfa2)) {
	    System.out.println("These two DFAs are equivalent.");
	} else {
	    System.out.println("These two DFAs are NOT equivalent!");
	}
    }
}
