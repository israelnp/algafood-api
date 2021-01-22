package com.algaworks.algafood.di.service;

import com.algaworks.algafood.di.notificacao.NivelDeUrgencia;
import com.algaworks.algafood.di.notificacao.Notificador;
import com.algaworks.algafood.di.notificacao.TipoDoNotificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;


@Component
public class AtivacaoClienteService {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	public void ativar(Cliente cliente){
		cliente.ativar();
		eventPublisher.publishEvent(new ClienteAtivadoEvent(cliente));
	}

	
}
