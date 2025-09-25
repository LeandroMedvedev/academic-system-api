package br.com.infnet.academicsystemapi.controller;

import br.com.infnet.academicsystemapi.dto.AuthenticationRequestDTO;
import br.com.infnet.academicsystemapi.dto.TokenResponseDTO;
import br.com.infnet.academicsystemapi.model.User;
import br.com.infnet.academicsystemapi.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid AuthenticationRequestDTO data) {
        // 1. Cria objeto de autenticação com os dados recebidos.
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.username(), data.password());

        // 2. AuthenticationManager usa AuthenticationService para validar usuário e senha.
        // Se credenciais estiverem erradas, lança exceção que o Spring trata automaticamente.
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 3. Se autenticação for bem-sucedida, pega o objeto "User" autenticado.
        var user = (User) authentication.getPrincipal();

        // 4. Usa TokenService para gerar token JWT.
        var token = tokenService.generateToken(user);

        // 5. Retorna token em um DTO.
        return ResponseEntity.ok(new TokenResponseDTO(token));
    }
}