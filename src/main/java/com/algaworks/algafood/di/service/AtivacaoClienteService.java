package com.algaworks.algafood.di.service;

import com.algaworks.algafood.di.notificacao.Notificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

@Component
public class AtivacaoClienteService {

	@Autowired
	private Notificador notificador;

//	public AtivacaoClienteService(Notificador notificador) {
//		this.notificador = notificador;
//
//		System.out.println("AtivacaoClienteService: " + notificador);
//	}
	
	public void ativar(Cliente cliente) {
		cliente.ativar();
		notificador.notificar(cliente, "seu cadastro no sistema está ativo!");
	}
	
}
