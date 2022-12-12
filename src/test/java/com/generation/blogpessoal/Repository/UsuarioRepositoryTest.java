package com.generation.blogpessoal.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.generation.blogpessoal.repository.UsuarioRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.blogpessoal.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeAll
    void start(){

        usuarioRepository.deleteAll();

        usuarioRepository.save(new Usuario(0L, "João da Silva", "joao@email.com.br", "13465278", "https://i.imgur.com/FETvs2O.jpg"));

        usuarioRepository.save(new Usuario(0L, "Manuela da Silva", "manuela@email.com.br", "13465278", "https://i.imgur.com/NtyGneo.jpg"));

        usuarioRepository.save(new Usuario(0L, "Adriana da Silva", "adriana@email.com.br", "13465278", "https://i.imgur.com/mB3VM2N.jpg"));

        usuarioRepository.save(new Usuario(0L, "Paulo Antunes", "paulo@email.com.br", "13465278", "https://i.imgur.com/JR7kUFU.jpg"));

    }

    @Test
    @DisplayName("Retorna 1 usuario")
    public void deveRetornarUmUsuario() {

        Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com.br");

        assertTrue(usuario.get().getUsuario().equals("joao@email.com.br"));
    }

    @Test
    @DisplayName("Retorna 3 usuarios")
    public void deveRetornarTresUsuarios() {

       Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com.br");
       Optional<Usuario> usuario1 = usuarioRepository.findByUsuario("manuela@email.com.br");
       Optional<Usuario> usuario2 = usuarioRepository.findByUsuario("adriana@email.com.br");

        assertTrue(usuario.get().getUsuario().equalsIgnoreCase("joao@email.com.br"));
        assertTrue(usuario1.get().getUsuario().equalsIgnoreCase("manuela@email.com.br"));
        assertTrue(usuario2.get().getUsuario().equalsIgnoreCase("adriana@email.com.br"));
    }
    @Test
    @AfterAll
    public void end() {
        usuarioRepository.deleteAll();
    }

}