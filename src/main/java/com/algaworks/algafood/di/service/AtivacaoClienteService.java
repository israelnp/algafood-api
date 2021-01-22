package com.algaworks.algafood.di.service;

import com.algaworks.algafood.di.notificacao.Notificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.notificacao.NotificadorEmail;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class AtivacaoClienteService {

	@Autowired
	private List<Notificador> notificadores;

//	public AtivacaoClienteService(Notificador notificador) {
//		this.notificador = notificador;
//
//		System.out.println("AtivacaoClienteService: " + notificador);
//	}
	
	public void ativar(Cliente cliente) {
		cliente.ativar();
		for (Notificador notificador : notificadores
			 ) {
			notificador.notificar(cliente, "seu cadastro no sistema está ativo!");

		}
	}
	
}
