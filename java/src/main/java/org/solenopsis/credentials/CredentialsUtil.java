package org.solenopsis.credentials;

/**
 * Credential related utilities.
 *
 * @author sfloess
 *
 */
public class CredentialsUtil {
    /**
     * Compute the web service password - this is a combination of <code>password</code>
     * plus <code>token</code>.
     *
     * @param password the password.
     * @param token the security token.
     *
     * @return the web service password (combination of password plus token).
     */
    public static String computeSecurityPassword(final String password, final String token) {
        return password + token;
    }

    /**
     * Compute the web service password - this is a combination of credential's <code>password</code>
     * plus <code>token</code>.
     *
     * @param credential contains the password and token for computation..
     *
     * @return the web service password (combination of credential's password plus token).
     */
    public static String computeSecurityPassword(final Credentials credentials) {
        return computeSecurityPassword(credentials.getPassword(), credentials.getToken());
    }
}
