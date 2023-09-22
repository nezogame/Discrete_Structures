package org.denys.hudymov;

import org.denys.hudymov.deque.ConsoleInterface;
import org.denys.hudymov.multilist.TreeConsoleInterface;

public class App {

    public static void main(String[] args) {
        ConsoleInterface consoleInterface = new ConsoleInterface();
        consoleInterface.run();
        TreeConsoleInterface treeConsoleInterface = new TreeConsoleInterface();
        treeConsoleInterface.run();
    }
}
