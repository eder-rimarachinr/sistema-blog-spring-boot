package com.sistema.blog.Security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider tokenProvider;
    private CustomUserDetailService detailService;

    public JwtAuthenticationFilter() {
        super();
    }

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, CustomUserDetailService detailService) {
        this.tokenProvider = tokenProvider;
        this.detailService = detailService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        //obtenemos el token de la solicitud HTTP;
        String token = obtenerJWTdeLaSolicitud(request);

        //vlidamos el token
        if (StringUtils.hasText(token) && tokenProvider.validarToken(token)) {
            //obtenemos el username del token;
            String username = tokenProvider.obtenerUsernameDelJWT(token);

            //Cargamos el usuario asociado al token
            UserDetails userDetails = detailService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //estblecemos la seguridad
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        filterChain.doFilter(request, response);


    }

    private String obtenerJWTdeLaSolicitud(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
