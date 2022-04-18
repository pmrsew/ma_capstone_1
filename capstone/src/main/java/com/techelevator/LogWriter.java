package com.techelevator;

import java.io.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

// This file creates a log file in the current project to log every transaction happening when the vending machine application is run
public class LogWriter {


    public static void log(String message) {
        // Giving the path to create a log file

        String logPath = "log.txt";
        File logFile = new File( logPath );

        try (PrintWriter writer = new PrintWriter( new FileOutputStream( logFile, true ) )) {

            writer.println( message );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println( "In the catch block of LogWriter file:" + e.getMessage() );

        }

    }
}
