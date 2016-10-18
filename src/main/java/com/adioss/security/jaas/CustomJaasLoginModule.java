package com.adioss.security.jaas;

import java.io.*;
import java.util.*;
import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import javax.security.auth.spi.*;

/**
 * Declared/instanciated using jaas.config file (java.security.auth.login.config param)
 */
public class CustomJaasLoginModule implements LoginModule {
    private Subject m_subject;
    private CallbackHandler m_callbackHandler;
    private Map<String, ?> m_sharedState;
    private Map<String, ?> m_options;
    private String m_userName;

    /**
     * Get initial params on instantiation
     */
    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        m_subject = subject;
        m_callbackHandler = callbackHandler;
        m_sharedState = sharedState;
        m_options = options;
    }

    /**
     * Do the logon: send some javax.security.auth.callback.Callback to callback handler that is in charge of
     * * getting user info
     * * send them back with call back
     */
    @Override
    public boolean login() throws LoginException {
        NameCallback nameCB = new NameCallback("Username");
        PasswordCallback passwordCB = new PasswordCallback("Password", false);
        Callback[] callbacks = new Callback[]{nameCB, passwordCB};
        try {
            m_callbackHandler.handle(callbacks);
        } catch (IOException | UnsupportedCallbackException e) {
            e.printStackTrace();
        }
        NameCallback nameCallback = (NameCallback) callbacks[0];
        // here user authentication is mocked: can only retrieve "testUser"
        m_userName = nameCallback.getName();
        return true;
    }

    @Override
    public boolean commit() throws LoginException {
        // here associate a subject with a principal
        m_subject.getPrincipals().add(new TestGroupPrincipal(m_userName));
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        return false;
    }
}
