package com.spring.gof.service;

import com.spring.gof.model.Cliente;

/**
 * Interface que define o padrão Strategy no domínio de cliente.
 * Com isso, se necessário, podemos ter multipas implementações dessa mesma interface.
 *
 * @author Carlos Marinho
 */
public interface ClienteService {

    Iterable<Cliente> buscarTodos();

    Cliente buscarPorId(Long id);

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente);

    void deletar(Long id);

}
