package br.com.infnet.academicsystemapi.config;

import br.com.infnet.academicsystemapi.AcademicSystemApiApplication;
import br.com.infnet.academicsystemapi.exception.BusinessException;
import br.com.infnet.academicsystemapi.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ConfigTest {

    @Test
    @DisplayName("Deve cobrir as classes de exceção")
    void testExceptionClasses() {
        String message = "Test message";
        BusinessException be = new BusinessException(message);
        ResourceNotFoundException re = new ResourceNotFoundException(message);

        assertNotNull(be);
        assertEquals(message, be.getMessage());
        assertNotNull(re);
        assertEquals(message, re.getMessage());
    }

    @Test
    @DisplayName("Deve executar o método main da aplicação sem lançar exceções")
    void main() {
        String[] args = {};

        assertDoesNotThrow(() -> {
            AcademicSystemApiApplication.main(args);
        });
    }
}
