package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class RestauranteRepositoryImp implements RestauranteRepository {

    @PersistenceContext
    EntityManager manager;

    @Override
    public List<Restaurante> listar() {
        return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
    }

    @Override
    public Restaurante buscar(Long id) {
        Restaurante aux = manager.find(Restaurante.class, id);
        if (aux == null){
            throw new IllegalArgumentException("Restaurante não existe!");
        }
        return aux;
    }

    @Transactional
    @Override
    public Restaurante salvar(Restaurante restaurante) {
        return manager.merge(restaurante);
    }

    @Transactional
    @Override
    public void remover(Restaurante restaurante) {
        Restaurante aux = buscar(restaurante.getId());
        manager.remove(aux);
    }
}
