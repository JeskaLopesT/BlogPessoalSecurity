package com.generation.blogpessoal.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@Test
	@Order(1)
	public void deveCadastrarUmUsuario() {
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, "Zé Mario", "zemario@ovo.com","ovofrito123","https://i.imgur.com/JR7kUFU.jpg"));
		
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
	}
	
	@Test
	@Order(2)
	private void deveAtualizarUmUsuario() {
		Optional<Usuario> usuarioCreate = usuarioService.cadastrarUsuario(new Usuario(0L, 
				"TinkWink", "tinkwink@telletubies.com.br", 
				"bolsavermelha", "https://pbs.twimg.com/profile_images/3457438261/e839142b1e74a6c69ce06189edf5a4e7_400x400.jpeg\r\n"));
		
		
		
		Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(), 
				"TinkWink da Silva", "tinkwink@telletubies.com.br", 
				"bolsavermelha", "https://pbs.twimg.com/profile_images/3457438261/e839142b1e74a6c69ce06189edf5a4e7_400x400.jpeg\r\n");
	

		HttpEntity<Usuario> requisicaoAtualizacao = new HttpEntity<Usuario>(usuarioUpdate);
		
		ResponseEntity<Usuario> respostaAtualizacao = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicaoAtualizacao, Usuario.class);

		assertEquals(HttpStatus.OK, respostaAtualizacao.getStatusCode());
	}
	
}
