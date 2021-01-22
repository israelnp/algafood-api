package com.algaworks.algafood.di.notificacao;

import com.algaworks.algafood.di.modelo.Cliente;
import org.springframework.stereotype.Component;

@TipoDoNotificador(NivelDeUrgencia.URGENTE)
@Component
public class NotificadoSMS implements Notificador {
    @Override
    public void notificar(Cliente cliente, String mensagem) {
        System.out.printf("Notificando %s através do SMS %s: %s\n",
                cliente.getNome(), cliente.getEmail(), mensagem);
    }
}
