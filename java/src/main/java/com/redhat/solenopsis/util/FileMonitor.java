package com.redhat.solenopsis.util;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * This class will monitor a file for changes.  If the file does not exist, no
 * changes can be considered.  A change to a file is denoted by its last modified
 * time stamp changing.
 *
 * @author sfloess
 *
 */
public class FileMonitor {
    /**
     * Used for logging.
     */
    private static final Logger logger = Logger.getLogger(FileMonitor.class.getName());
    
    /**
     * The file to monitor.
     */
    private final File file;
    
    /**
     * Last time file was monitored, its date/time.
     */
    private long lastModified;
    
    /**
     * Return the logger.
     * 
     * @return the logger. 
     */
    protected Logger getLogger() {
        return logger;
    }
    
    /**
     * Return the file to monitor.
     * 
     * @return the file to monitor. 
     */
    protected File getFile() {
        return file;
    }
    
    /**
     * Return the timestamp of the last modification of the file being monitor.
     * 
     * @return the timestamp of the file last time it was monitored.
     */
    protected long getLastModified() {
        return lastModified;
    }
    
    /**
     * Set the timestamp for the file being monitored.
     * 
     * @param lastModified is the timestamp of the file being monitored.
     */
    protected void setLastModified(final long lastModified) {
        this.lastModified = lastModified;
    }
    
    /**
     * This constructor sets the file to monitor.
     * 
     * @param file the file to monitor. 
     */
    public FileMonitor(final File file) {
        this.file         = file;
        this.lastModified = 0;
    }
    
    /**
     * This constructor sets the file to monitor using a file name.
     * 
     * @param fileName the name of the file to monitor. 
     */
    public FileMonitor(final String fileName) {
        this(new File(fileName));
    }
    
    /**
     * Return true if the file being monitored exists, or false if not...  The
     * file could be deleted - in which case it technically changed, but there is
     * nothing to monitor.
     * 
     * @return true if the file exists or false if not.
     */
    public boolean exists() {
        if (getLogger().isLoggable(Level.INFO)) {
           getLogger().log(Level.INFO, "File, {0}, exists = {1}", new Object[]{getFile(), getFile().exists()}); 
        }
        
        return getFile().exists();
    }
    
    /**
     * Return true if the file changed or false if not.  Be aware, if the file does
     * not exists, its as if the file has not changed.  Call exists() to see if it
     * no longer exists.
     * 
     * @return true if the file changed or false if not.
     */
    public boolean isChanged() {
        boolean retVal = false;
        
        if (exists()) {     
            if (getLogger().isLoggable(Level.INFO)) {
                getLogger().log(Level.INFO, "File, {0} lastModified on {1} -> {2}", new Object[]{getFile(), getFile().lastModified(), getLastModified() }); 
            }
   
            retVal = getFile().lastModified() != getLastModified();
            
            setLastModified(getFile().lastModified());
        }
        
        return retVal;
    }
    
    /**
     * Using <code>sb</code>, compute the string representation of self whose
     * prefix for computation is <code>prefix</code>.
     * 
     * @param sb will have the string representation of self appended.
     * @param prefix is the prefix to be first appended prior to self's string representation.
     */
    public void toString(final StringBuilder sb, final String prefix) {
        sb.append(prefix).append("file [").append(getFile()).append("] - exists [").append(exists()).append("]");
    }
    
    /**
     * Using <code>sb</code>, compute the string representation of self.
     * 
     * @param sb will have the string representation of self appended.
     */
    public void toString(final StringBuilder sb) {
        toString(sb, "");
    }
    
    /**
     * Using <code>prefix</code> return the string representation of self.
     * 
     * @param prefix is the prefix to emit when return the string representation of self.
     * 
     * @return the string representation of self.
     */
    public String toString(final String prefix) {
        final StringBuilder sb = new StringBuilder();
        
        toString(sb, prefix);
        
        return sb.toString();
    }
    
    /**
     * Return the string representation of self.
     * 
     * @return the string representation of self.
     */
    @Override
    public String toString() {
        return toString("");
    }        
}
