package br.com.infnet.academicsystemapi.config;

import br.com.infnet.academicsystemapi.model.User;
import br.com.infnet.academicsystemapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existe algum usuário no banco para não inserir duplicado
        if (userRepository.count() == 0) {
            User professor = new User();
            professor.setUsername("professor");
            // CRIPTOGRAFA a senha antes de salvar
            professor.setPassword(passwordEncoder.encode("123456"));

            userRepository.save(professor);
            System.out.println("Usuário 'professor' criado com senha '123456'");
        }
    }
}