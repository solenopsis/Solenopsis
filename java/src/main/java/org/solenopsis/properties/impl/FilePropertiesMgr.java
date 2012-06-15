package org.solenopsis.properties.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

/**
 *
 * Acts as a decorator using a file.
 *
 * @author sfloess
 *
 */
public final class FilePropertiesMgr extends AbstractPropertiesMgr { 
    protected final InputStreamPropertiesMgr inputStreamPropertiesMgr;
    
    /**
     * Return the input stream properties manager.
     * 
     * @return the input stream properties manager.
     */
    protected InputStreamPropertiesMgr getInputStreamPropertiesMgr() {
        return inputStreamPropertiesMgr;
    }
    
    @Override
    public Properties getProperties() {
        return getInputStreamPropertiesMgr().getProperties();
    }
    
    public FilePropertiesMgr(final File file) throws IOException {        
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);

            inputStreamPropertiesMgr = new InputStreamPropertiesMgr(fis);
        }

        finally {
            if (fis != null) {
                try {
                    fis.close();
                }

                catch (final IOException ioException) {
                    getLogger().log(Level.WARNING, "Trouble closing file input stream for file " + file, ioException);
                }
            }
        }       
    }
    
    public FilePropertiesMgr(final String fileName) throws IOException {
        this(new File(fileName));
    }
}
