package com.adioss.security.jaas;

import java.security.Principal;
import javax.security.auth.*;

/**
 * Custom {@link Principal} that will be used to discriminate a user on policy.config (java.security.policy)
 */
public class TestGroupPrincipal implements Principal {

    private final String m_name;

    public TestGroupPrincipal(String name) {
        m_name = name;
    }

    @Override
    public String getName() {
        return m_name;
    }

    @Override
    public boolean implies(Subject subject) {
        return true;
    }

    @Override
    public String toString() {
        return "TestGroupPrincipal{" +
                "m_name='" + m_name + '\'' +
                '}';
    }
}
