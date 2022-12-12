package com.generation.blogpessoal.controller;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate; // permite simular comunicação com API

    @Autowired // injeção de dependencia
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @BeforeAll
    void start(){
        usuarioRepository.deleteAll();
        usuarioService.cadastrarUsuario(new Usuario(0L,"root","root@gmail.com", "rootroot", " ")); //O 0L a letra L é de Long

    }
    @Test
    @DisplayName("Cadastrar um Usuário")
    public void deveCriarUmUsuario(){
        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L,"Larissa", "larissa@mercadolivre.com","1234652345678"," "));
        ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);
        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
         assertEquals(corpoRequisicao.getBody().getNome(),corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getUsuario(),corpoResposta.getBody().getUsuario());
    }

    @Test
    @DisplayName("Listar todos os Usuários")
    public void deveMostrarTodosUsuarios(){
        usuarioService.cadastrarUsuario(new Usuario(0L,"Sabrina", "sabrina@mercadolivre.com","12345643335789", " "));
        usuarioService.cadastrarUsuario(new Usuario(0L,"Ricardo", "Ricardo@mercadolivre.com","1234565789", " "));
        ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root@gmail.com", "rootroot").exchange("/usuarios/listartodos", HttpMethod.GET, null,String.class);
       assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    @DisplayName("Listar por ID")
    public void deveMostrarUsuariosPorId(){
        usuarioService.cadastrarUsuario(new Usuario(0L,"Sabrina", "sabrina@mercadolivre.com","12345643335789", " "));
        ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root@gmail.com", "rootroot").exchange("/usuarios/buscarporid/1", HttpMethod.GET, null,String.class);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    @DisplayName("Fazer login")
    public void fazerLogin() {
        Optional<UsuarioLogin> usuarioLogin = Optional.of(new UsuarioLogin("root@gmail.com", "rootroot"));
        usuarioService.autenticarUsuario(usuarioLogin);

        HttpEntity<UsuarioLogin> corpoRequisicao = new HttpEntity<UsuarioLogin>(new UsuarioLogin("root@gmail.com",
                "rootroot"));

        ResponseEntity<UsuarioLogin> resposta = testRestTemplate.withBasicAuth("root@gmail.com", "rootroot")
                .exchange("/usuarios/logar", HttpMethod.POST, corpoRequisicao, UsuarioLogin.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        //usuarioCadastrado.get().getUsuario(), usuarioCadastrado.get().getSenha();
    }



}
