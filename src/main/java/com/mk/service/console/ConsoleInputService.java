package com.mk.service.console;

import com.mk.contract.Stoppable;

import java.util.Scanner;

/**
 * Class used to handle console input.
 *
 * @author Pavel Fursov
 */
public class ConsoleInputService implements Runnable, Stoppable {

    private static final String TERMINATION_COMMAND = "exit";

    private boolean isRunning = true;

    /**
     * Waits for 'exit' command to terminate application.
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (isRunning && !scanner.nextLine().trim().equalsIgnoreCase(TERMINATION_COMMAND)) {
            System.out.println("Type 'exit' and press 'ENTER' key to terminate application.");
        }
    }

    @Override
    public void stop() {
        System.out.println("Stopping ConsoleInputService...");
        isRunning = false;
    }
}
