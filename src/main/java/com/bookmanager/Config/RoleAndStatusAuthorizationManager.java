package com.bookmanager.Config;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

public class RoleAndStatusAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final String requiredRole;
    private final String requiredStatus;

    public RoleAndStatusAuthorizationManager(String requiredRole, String requiredStatus) {
        this.requiredRole = requiredRole;
        this.requiredStatus = requiredStatus;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        var auth = authentication.get();
        if (auth == null || auth.getAuthorities() == null) {
            return new AuthorizationDecision(false);
        }
        boolean hasRole = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + requiredRole));

        boolean hasStatus = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("STATUS_" + requiredStatus));

        boolean granted = hasRole && hasStatus;
        return new AuthorizationDecision(granted);
    }
}
