package br.com.casadocodigo.loja.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.casadocodigo.loja.models.Produto;

public class RelatorioProdutosDto {

	@DateTimeFormat
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
	private Calendar dataGeracao = Calendar.getInstance();
	private int quantidade;
	private List<Produto> produtos = new ArrayList<>();
	
	
	
	public RelatorioProdutosDto(Calendar dataGeracao, int quantidade, List<Produto> produtos) {
		this.dataGeracao = dataGeracao;
		this.quantidade = quantidade;
		this.produtos = produtos;
	}
	

	public RelatorioProdutosDto() {
		
	}
	
	
	public Calendar getDataGeracao() {
		return dataGeracao;
	}
	public void setDataGeracao(Calendar dataGeracao) {
		this.dataGeracao = dataGeracao;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public List<Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
}
