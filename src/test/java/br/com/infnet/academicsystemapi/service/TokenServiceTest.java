package br.com.infnet.academicsystemapi.service;

import br.com.infnet.academicsystemapi.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Test
    @DisplayName("Deve retornar subject ao validar token válido")
    void validateToken_WithValidToken_ShouldReturnSubject() {
        ReflectionTestUtils.setField(tokenService, "secret", "meu_secret_super_secreto");
        User user = new User();
        user.setUsername("professor");
        String validToken = tokenService.generateToken(user);

        String subject = tokenService.validateToken(validToken);

        assertThat(subject).isEqualTo("professor");
    }

    @Test
    @DisplayName("Deve retornar string vazia ao validar token inválido")
    void validateToken_WithInvalidToken_ShouldReturnEmptyString() {
        ReflectionTestUtils.setField(tokenService, "secret", "meu_secret_super_secreto");
        String invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        String subject = tokenService.validateToken(invalidToken);

        assertThat(subject).isEmpty();
    }
}
