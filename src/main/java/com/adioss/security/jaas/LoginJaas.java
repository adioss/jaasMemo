package com.adioss.security.jaas;

import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.*;
import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;

class LoginJaas {

    private final LoginContext m_loginContext;
    private Subject m_subject;

    LoginJaas(String loginContextName, CallbackHandler callbackHandler) {
        try {
            m_loginContext = new LoginContext(loginContextName, callbackHandler);
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }

    void login() {
        m_subject = null;
        try {
            m_loginContext.login();
            m_subject = m_loginContext.getSubject();
        } catch (LoginException e) {
            System.out.println("authentication failed");
            e.printStackTrace();
        }
        System.out.printf(m_subject.toString());
        Set<Principal> principals = m_subject.getPrincipals();
        System.out.println(principals);
    }

    void doAsPrivileged(PrivilegedAction<?> privilegedAction) {
        Subject.doAsPrivileged(m_subject, privilegedAction, null);
    }
}
