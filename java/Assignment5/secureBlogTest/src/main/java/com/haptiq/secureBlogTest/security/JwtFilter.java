package com.haptiq.secureBlogTest.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final ApplicationContext applicationContext;

    public JwtFilter(JwtUtils jwtUtils, ApplicationContext applicationContext) {
        this.jwtUtils = jwtUtils;
        this.applicationContext = applicationContext;
    }

    //main kaam: "If the token has a username, and no one is authenticated yet for this request, then we should authenticate the user using the token."
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader  = request.getHeader("Authorization");
        final String token;
        String jwt = null;
        final String userName;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);

        try {
            userName = jwtUtils.extractUsername(token);
            System.out.println("-------------username"+userName);
// What is SecurityContextHolder? : It holds the security information (like who is logged in) for the current request thread.It's like saying: "Hey, for this HTTP request thread, is someone already authenticated?"
// What is .getAuthentication()?: Returns the Authentication object (like UsernamePasswordAuthenticationToken) that represents the current logged-in user.
            if (userName != null && SecurityContextHolder.getContext().getAuthentication()==null){
                //  Lazy fetch of userDetailsService to avoid circular dependency
                UserDetailsService userDetailsService = applicationContext.getBean("userDetailsServiceImpl",UserDetailsService.class);

                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
//we will validate this now and tell ki user validate zhaly
                if (jwtUtils.validateToken(token, userDetails)){

// UsernamePasswordAuthenticationToken:This is a class that implements Authentication, used to represent the identity of a user in the system.
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); //and then we will use this object in security config for giving roles and permisiion according to role and info

                    // here details means Hey, besides the username and roles, here’s some context about how the user made this request
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    //Securitycontext class save krto current authenticated users info ani
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    securityContext.setAuthentication(authenticationToken);
                    SecurityContextHolder.setContext(securityContext);
                    //atta after this we got the authenticated user in securityContext object


                }

            }
        }
        catch (Exception e){

            System.out.println("JWT Filter Error: " + e.getMessage());

        }
        filterChain.doFilter(request,response);

    }
}
