
# UserProvisioningFilter

## Overview

`UserProvisioningFilter` is a custom Spring Security filter designed to **automatically provision (register) users in your local database based on JWT tokens issued by an external Identity Provider (IdP), such as Keycloak**.

---

## Why is this needed?

When using an external OAuth2/OpenID Connect provider like Keycloak:

* Users authenticate via the IdP.
* The application receives a JWT token containing user info.
* However, the application’s local database might not yet have a record of this user.
* To maintain user-related data (roles, preferences, logs, etc.), you want to **create the user record on-the-fly** during the first login.

This filter handles that **automatic user creation**.

---

## How it works

1. The filter intercepts every HTTP request once (`OncePerRequestFilter`).
2. It retrieves the current `Authentication` from Spring Security’s `SecurityContextHolder`.
3. It checks if the user is authenticated and the principal is a JWT token.
4. It extracts user details (e.g., `sub`, `email`, `preferred_username`) from the JWT.
5. If the user does not exist in the database, it creates and saves a new user entity.
6. The request proceeds through the filter chain.

---

## When to use

* Your application delegates authentication to an external OAuth2 provider (Keycloak, Okta, Auth0, etc.).
* You want to synchronize users from the IdP into your local database automatically.
* You use Spring Security with JWT token validation.

---

## Example usage

```java
@Component
@RequiredArgsConstructor
public class UserProvisioningFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null
           && authentication.isAuthenticated()
           && authentication.getPrincipal() instanceof Jwt jwt) {

            UUID userId = UUID.fromString(jwt.getSubject());

            if(!userRepository.existsById(userId)) {
                User user = new User();
                user.setId(userId);
                user.setName(jwt.getClaim("preferred_username"));
                user.setEmail(jwt.getClaim("email"));
                userRepository.save(user);
            }
        }

        filterChain.doFilter(request, response);
    }
}
```

---

## Customization

* Extend the filter to add roles or assign permissions on provisioning.
* Extract additional claims as needed.
* Integrate with other user management or audit systems.

---

## Important notes

* Make sure this filter is registered **after** Spring Security’s authentication filters.
* Always call `filterChain.doFilter()` to avoid blocking requests.
* Handle potential exceptions gracefully.
* Consider caching user existence checks if needed for performance.

---

## Summary

The `UserProvisioningFilter` is a simple yet powerful way to **bridge external authentication with your internal user management**, ensuring your application’s user database stays in sync with the Identity Provider.

