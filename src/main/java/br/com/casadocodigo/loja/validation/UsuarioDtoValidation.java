package br.com.casadocodigo.loja.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.casadocodigo.loja.dto.UsuarioDto;

public class UsuarioDtoValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UsuarioDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "nome", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "email", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "senha", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "senhaRepetida", "field.required");

		UsuarioDto usuario = (UsuarioDto) target;

		if (usuario.getSenha().length() < 5) {
			errors.rejectValue("senha", "usuario.senha");
		}

		if (!usuario.getSenhaRepetida().equals(usuario.getSenha())) {
			errors.rejectValue("senhaRepetida", "usuario.senha.invalida");
		}

	}

}
