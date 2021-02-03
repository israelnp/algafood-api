package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public EstadoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public EstadoNaoEncontradoException(Long estadoId) {
        super(String.format("Não existe um cadastro de estado com código %d", estadoId));
    }
}
