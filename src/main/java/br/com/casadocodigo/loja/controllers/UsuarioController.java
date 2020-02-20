package br.com.casadocodigo.loja.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.RoleDAO;
import br.com.casadocodigo.loja.dao.UsuarioDAO;
import br.com.casadocodigo.loja.dto.UsuarioDto;
import br.com.casadocodigo.loja.models.Role;
import br.com.casadocodigo.loja.models.Usuario;
import br.com.casadocodigo.loja.validation.UsuarioDtoValidation;

@Controller
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioDAO userDao;

	@Autowired
	private RoleDAO roleDao;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new UsuarioDtoValidation());
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listarUsuarios() {
		ModelAndView mv = new ModelAndView("usuarios/lista");
		try {

			List<Usuario> usuarios = userDao.listar();
			List<UsuarioDto> usuarioDto = new ArrayList<>();
			usuarioDto.addAll(usuarios.stream()
					.map(u -> new UsuarioDto(u.getNome(), u.getEmail(),
							u.getRoles().stream().map(r -> r.getNome()).collect(Collectors.toList())))
					.collect(Collectors.toList()));

			mv.addObject("usuarios", usuarioDto);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping("/form")
	public ModelAndView form(UsuarioDto usuarioDto) {
		ModelAndView modelAndView = new ModelAndView("usuarios/form");
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar(@Valid UsuarioDto usuarioDto, BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return form(usuarioDto);
		}

		try {
			Usuario loadUserByUsername = userDao.loadUserByUsername(usuarioDto.getEmail());
			if (loadUserByUsername != null) {
				result.rejectValue("email", "usuario.email");
				return form(usuarioDto);
			}
		} catch (UsernameNotFoundException e) {

		}

		Usuario usuario = new Usuario();
		usuario.setNome(usuarioDto.getNome());
		usuario.setEmail(usuarioDto.getEmail());
		usuario.setSenha(usuarioDto.getSenha());

		userDao.gravar(usuario);

		redirectAttributes.addFlashAttribute("message", "Usuário cadastrado com sucesso!");

		return new ModelAndView("redirect:/usuarios");
	}

	@RequestMapping(value = "/roles")
	public ModelAndView listarRoles(String email) {
		ModelAndView mv = new ModelAndView("usuarios/roleForm");

		try {

			List<String> roles = roleDao.listar().stream().map(r -> r.getNome()).collect(Collectors.toList());

			System.out.println(email);

			Usuario usuario = userDao.loadUserByUsername(email);

			UsuarioDto usuarioDto = new UsuarioDto(usuario.getNome(), usuario.getEmail(),
					usuario.getRoles().stream().map(r -> r.getNome()).collect(Collectors.toList()));

			mv.addObject("roles", roles);
			mv.addObject("usuarioDto", usuarioDto);

		} catch (UsernameNotFoundException e) {

		}

		return mv;
	}

	@RequestMapping("/editar")
	public ModelAndView editarPermissoes(UsuarioDto usuarioDto, BindingResult result,
			RedirectAttributes redirectAttributes) {

		List<Role> roles = usuarioDto.getRoles().stream().map(r -> new Role(r)).collect(Collectors.toList());

		Usuario user = userDao.loadUserByUsername(usuarioDto.getEmail());

		user.setRoles(roles);

		userDao.update(user);

		redirectAttributes.addFlashAttribute("message", "Permissões alteradas com sucesso!");

		return new ModelAndView("redirect:/usuarios");
	}
}
