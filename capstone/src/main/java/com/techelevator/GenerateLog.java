package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

// This file creates a log file in the current project to log every transaction happening when the vending machine application is run
public class GenerateLog {
    public static void log(String message) {
        // Giving the path to create a log file

        String logPath = "capstone/logs/log.txt";

        File logFile = new File( logPath );

        try (PrintWriter writer = new PrintWriter( new FileOutputStream( logFile, true ) )) {

            writer.println( message );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println( "In the catch block if log():" + e.getMessage() );

        }

    }
}
