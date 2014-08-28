/*
 * (c) 2009  The Echo Nest
 * See "license.txt" for terms
 *
 */
package echonest.v4.util;


import echonest.v4.util.*;

/**
 * An interface implemented by command functions typically
 * added to a command interpreter
 *
 * @see echonest.v4.util.Shell
 */

public interface ShellCommand {

    /**
     * Execute the given command.
     *  
     * @param ci	the command interpretere that invoked this command.
     * @param args	command line arguments (just like main).
     * @return		a command result
     *
     */
    public String execute(echonest.v4.util.Shell ci, String[] args) throws Exception;

    /**
     * Returns a one line description of the command
     *
     * @return a one-liner help message
     */
    public String getHelp();
}
