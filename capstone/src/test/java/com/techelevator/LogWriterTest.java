package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class LogWriterTest {
    @Test
    public void logfile_exists() {
        File logFile = new File( "log.txt" );
        boolean exists = logFile.exists();
        Assert.assertEquals( true, exists );
    }
}
