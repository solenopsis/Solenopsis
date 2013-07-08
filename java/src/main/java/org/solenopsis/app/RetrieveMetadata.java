package org.solenopsis.app;

import java.util.logging.LogManager;
import org.flossware.util.properties.impl.FilePropertiesMgr;
import org.solenopsis.lasius.credentials.Credentials;
import org.solenopsis.lasius.credentials.impl.PropertiesCredentials;
import org.solenopsis.lasius.wsimport.security.SecurityMgr;
import org.solenopsis.lasius.wsimport.security.impl.PartnerSecurityMgr;
import org.solenopsis.lasius.wsimport.session.mgr.SessionMgr;
import org.solenopsis.lasius.wsimport.session.mgr.impl.SingleSessionMgr;
import org.solenopsis.lasius.wsimport.util.SalesforceWebServiceUtil;
import org.solenopsis.metadata.Org;
import org.solenopsis.metadata.impl.wsimport.DefaultWsimportOrg;


/**
 *
 * The purpose of this class is emit metadata to the console.
 *
 * @author sfloess
 *
 */
public class RetrieveMetadata {
    public static Org getOrg(final SessionMgr sessionMgr, final Credentials creds) throws Exception {
        return new DefaultWsimportOrg(SalesforceWebServiceUtil.createMetadataPort(sessionMgr), creds.getApiVersion());
    }
    public static void main(final String[] args) throws Exception {
        LogManager.getLogManager().readConfiguration(RetrieveMetadata.class.getResourceAsStream("/logging.properties"));

        final String env = "test-dev.properties";
//        final String env = "dev.properties";


        Credentials credentials = new PropertiesCredentials(new FilePropertiesMgr(System.getProperty("user.home") + "/.solenopsis/credentials/" + env));
        final SessionMgr sessionMgr = new SingleSessionMgr(credentials, new PartnerSecurityMgr());

        final int total = 5;

        final long startTime = System.currentTimeMillis();

        for (int loop = 0 ; loop < total; loop++) {
            final Org org = getOrg(sessionMgr, credentials);
        }

        final long stopTime = System.currentTimeMillis();

        System.out.println("Total time [" + (stopTime - startTime) + " ms]");
        System.out.println("Avg time   [" + ((stopTime - startTime) / total) + " ms]");
        //System.out.println(org);
    }
}
