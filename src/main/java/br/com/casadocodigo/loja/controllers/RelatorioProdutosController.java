package br.com.casadocodigo.loja.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.dto.RelatorioProdutosDto;
import br.com.casadocodigo.loja.models.Produto;

@RestController
public class RelatorioProdutosController {

	@Autowired
	private ProdutoDAO dao;

	@RequestMapping(value = "/relatorio-produtos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> relatorioDeProdutos(@RequestParam(value = "data", required = false) String data) {
		try {

			Calendar dataGeracao = Calendar.getInstance();

			List<Produto> produtos = new ArrayList<>();

			if (data == null) {

				produtos.addAll(dao.listar());

			} else {

				if (data.isEmpty() || !data.matches("\\w{4}(\\-\\w{2}){2}")) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
							"Erro ao procurar por data de lançamento, data informada deve ser no pattern yyyy-MM-dd");
				}

				Calendar dataLancamento = Calendar.getInstance();
				dataLancamento.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(data));

				produtos.addAll(dao.listaPorDataLancamento(dataLancamento));

			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(new RelatorioProdutosDto(dataGeracao, produtos.size(), produtos));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("erro no servidor");
		}
	}

}
