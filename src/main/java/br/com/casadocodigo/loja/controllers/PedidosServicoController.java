package br.com.casadocodigo.loja.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.dto.PedidoDto;
import br.com.casadocodigo.loja.models.Pedido;

@Controller
public class PedidosServicoController {

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/pedidos")
	public Callable<ModelAndView> listarPedidos() {
		return () -> {

			try {
				List<Pedido> pedidos = new ArrayList<>();
				try {

					Pedido[] ps = restTemplate.getForObject("https://book-payment.herokuapp.com/orders",
							Pedido[].class);

					pedidos.addAll(Arrays.stream(ps).collect(Collectors.toList()));

				} catch (HttpClientErrorException e) {
					e.printStackTrace();
				}

				ModelAndView mv = new ModelAndView("pedidos");

				List<PedidoDto> dtos = pedidos.stream()
						.map(p -> new PedidoDto(p.getId(), p.getValor(), p.getData(),
								p.getProdutos().stream().map(pr -> pr.getTitulo()).collect(Collectors.joining(", "))))
						.collect(Collectors.toList());

				mv.addObject("pedidos", dtos);

				System.out.println(pedidos.size());

				return mv;
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("pedidos");
			}
		};

	}

}
