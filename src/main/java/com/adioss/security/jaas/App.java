package com.adioss.security.jaas;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivilegedAction;

/**
 * Params:
 * -Djava.security.manager
 * -Djava.security.auth.login.config="path_to/jaas.config"
 * -Djava.security.policy="path_to/policy.config"
 * Rmk: -Djava.security.policy==(...) will OVERRIDE completely the default policy instead of enhance it.
 */
public class App {
    public static void main(String[] args) {
        // create a new javax.security.auth.login.LoginContext that will use LoginCallbackHandler to retrive user (here testUser all the time)
        LoginJaas loginJaas = new LoginJaas("CustomJaasLoginModule", new LoginCallbackHandler());
        // perform login and retrieve subject
        loginJaas.login();
        PrivilegedAction<Object> action = () -> {
            Files.exists(Paths.get("config.txt"));
            return "";  // TODO implement method
        };
        // submit action on subject with privilege
        loginJaas.doAsPrivileged(action);
    }

    private App() {
    }
}
