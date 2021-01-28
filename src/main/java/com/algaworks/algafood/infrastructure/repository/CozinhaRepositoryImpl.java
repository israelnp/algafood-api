package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository {

    @PersistenceContext
    EntityManager manager;
    @Override
    public List<Cozinha> listar() {
        return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
    }

    @Override
    public List<Cozinha> consultarPorNome(String nome) {
        return manager.createQuery("from Cozinha where nome like :nome", Cozinha.class)
                .setParameter("nome", nome)
                .getResultList();

    }

    @Override
    public Cozinha buscar(Long id) {

         Cozinha cozinha =manager.find(Cozinha.class, id);
        return cozinha;
    }
    @Transactional
    @Override
    public Cozinha salvar(Cozinha cozinha) {
        return manager.merge(cozinha);
    }
    @Transactional
    @Override
    public void remover(Long cozinhaId) {
        Cozinha aux =buscar(cozinhaId);
        manager.remove(aux);
    }


}
