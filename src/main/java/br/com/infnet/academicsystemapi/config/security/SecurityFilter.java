package br.com.infnet.academicsystemapi.config.security;

import br.com.infnet.academicsystemapi.repository.UserRepository;
import br.com.infnet.academicsystemapi.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Recupera "token" do cabeçalho da requisição
        var token = this.recoverToken(request);

        // 2. Se houver um token, valida-o
        if (token != null) {
            var subject = tokenService.validateToken(token);

            // 3. Se token for válido, busca o usuário no banco de dados
            if (!subject.isEmpty()) {
                UserDetails user = userRepository.findByUsername(subject)
                        .orElseThrow(() -> new RuntimeException("User Not Found"));

                // 4. Cria um objeto de autenticação para o Spring Security
                var authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());

                // 5. Define usuário como autenticado no contexto de segurança do Spring
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 6. Continua a cadeia de filtros
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        // Remove prefixo "Bearer " para obter apenas o token
        return authHeader.replace("Bearer ", "");
    }
}
