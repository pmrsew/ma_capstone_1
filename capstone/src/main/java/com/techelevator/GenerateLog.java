package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class GenerateLog {
    public static void log(String message) {
        // To get the path of current working directory
        String currentDir = System.getProperty( "user.dir" );
        String logPath = currentDir + "\\capstone\\logs\\log.txt";


        File logFile = new File( logPath );

        try(PrintWriter writer = new PrintWriter( new FileOutputStream( logFile , true))){

            writer.println(message);

        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("In the catch block if log():" + e.getMessage());

        }


    }
}
