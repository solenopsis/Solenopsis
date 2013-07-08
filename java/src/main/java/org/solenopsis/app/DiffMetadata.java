package org.solenopsis.app;

import java.util.logging.LogManager;
import org.flossware.util.properties.impl.FilePropertiesMgr;
import org.solenopsis.lasius.credentials.Credentials;
import org.solenopsis.lasius.credentials.impl.PropertiesCredentials;
import org.solenopsis.lasius.wsimport.security.impl.PartnerSecurityMgr;
import org.solenopsis.lasius.wsimport.session.mgr.SessionMgr;
import org.solenopsis.lasius.wsimport.session.mgr.impl.SingleSessionMgr;
import org.solenopsis.lasius.wsimport.util.SalesforceWebServiceUtil;
import org.solenopsis.metadata.Member;
import org.solenopsis.metadata.Org;
import org.solenopsis.metadata.util.Diff;
import org.solenopsis.metadata.impl.wsimport.DefaultWsimportOrg;


/**
 *
 * The purpose of this class is emit metadata to the console.
 *
 * @author sfloess
 *
 */
public class DiffMetadata {
    public static void main(final String[] args) throws Exception {
        LogManager.getLogManager().readConfiguration(DiffMetadata.class.getResourceAsStream("/logging.properties"));

        final String credentialsDir = System.getProperty("user.home") + "/.solenopsis/credentials/";

        final String devEnv = credentialsDir + "dev.properties";
        final String qaEnv  = credentialsDir + "sfloess.properties";

        Credentials devCredentials = new PropertiesCredentials(new FilePropertiesMgr(devEnv));
        Credentials qaCredentials  = new PropertiesCredentials(new FilePropertiesMgr(qaEnv));

        final SessionMgr devSessionMgr = new SingleSessionMgr(devCredentials, new PartnerSecurityMgr());
        final SessionMgr qaSessionMgr  = new SingleSessionMgr(qaCredentials,  new PartnerSecurityMgr());

        final Org devOrg = new DefaultWsimportOrg(SalesforceWebServiceUtil.createMetadataPort(devSessionMgr), devCredentials.getApiVersion());
        final Org qaOrg  = new DefaultWsimportOrg(SalesforceWebServiceUtil.createMetadataPort(qaSessionMgr),  qaCredentials.getApiVersion());

        System.out.println("Added:");

        for(final Member member : Diff.computeAdded(devOrg, qaOrg)) {
            System.out.println(member.toString("    "));
        }

        System.out.println("Removed:");

        for(final Member member : Diff.computeRemoved(devOrg, qaOrg)) {
            System.out.println(member.toString("    "));
        }
    }
}
