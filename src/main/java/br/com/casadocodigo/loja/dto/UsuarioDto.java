package br.com.casadocodigo.loja.dto;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDto {

	private String nome;
	private String email;
	private String senha;
	private String senhaRepetida;
	private List<String> roles = new ArrayList<>();
	
	public UsuarioDto() {
		
	}

	public UsuarioDto(String nome, String email, List<String> roles) {
		this.setNome(nome);
		this.setEmail(email);
		this.setRoles(roles);
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getSenhaRepetida() {
		return senhaRepetida;
	}


	public void setSenhaRepetida(String senhaRepetida) {
		this.senhaRepetida = senhaRepetida;
	}


	public List<String> getRoles() {
		return roles;
	}


	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
	
}
