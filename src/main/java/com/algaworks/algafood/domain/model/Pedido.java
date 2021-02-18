package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import com.algaworks.algafood.domain.exception.NegocioException;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;

    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;

    @Embedded
    private Endereco enderecoEntrega;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;

    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;

    @ManyToOne
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens = new ArrayList<>();

    public void calcularValorTotal() {
        getItens().forEach(ItemPedido::calcularPrecoTotal);

        this.subtotal = getItens().stream()
                .map(item -> item.getPrecoTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }


    public void confirmar() {
        setStatus(StatusPedido.CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());
    }

    public void entregar() {
        setStatus(StatusPedido.ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }

    public void cancelar() {
        setStatus(StatusPedido.CANCELADO);
        setDataCancelamento(OffsetDateTime.now());
    }

    private void setStatus(StatusPedido novoStatus) {
        if (getStatus().naoPodeAlterarPara(novoStatus)) {
            throw new NegocioException(
                    String.format("Status do pedido %d não pode ser alterado de %s para %s",
                            getId(), getStatus().getDescricao(),
                            novoStatus.getDescricao()));
        }

        this.status = novoStatus;
    }

    @PrePersist
    private void gerarCodigo() {
        setCodigo(UUID.randomUUID().toString());
    }

}