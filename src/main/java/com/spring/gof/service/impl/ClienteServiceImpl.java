package com.spring.gof.service.impl;

import com.spring.gof.model.Cliente;
import com.spring.gof.model.ClienteRepository;
import com.spring.gof.model.Endereco;
import com.spring.gof.model.EnderecoRepository;
import com.spring.gof.service.ClienteService;
import com.spring.gof.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    // TODO Singleton : injetar os componetes do Spring com @Autowired
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    // TODO Strategy : implementar os métodos definidos na interface
    // TODO Facade : Abstrair integrações com subsistemas, provendo uma interface simples.


    @Override
    public Iterable<Cliente> buscarTodos() {
        // FIXME Buscar todos os Clientes.
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        // FIXME Buscar Cliente por ID.
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        // FIXME Buscar Cliente por ID, caso exista:
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if(clienteBd.isEmpty())
            throw new RuntimeException("Cliente não encontrado");

        // FIXME Verificar se o Endereco do Cliente já existe (pelo CEP).
        // FIXME Caso não exista, integrar com o ViaCEP e persistir o retorno.
        // FIXME Alterar Cliente, vinculando o Endereco (novo ou existente).
        salvarClienteComCep(cliente);
    }

    @Override
    public void deletar(Long id) {
        // FIXME Deletar Cliente por ID.
        clienteRepository.deleteById(id);
    }

    private void salvarClienteComCep(Cliente cliente) {
        // FIXME Verificar se o Endereco do Cliente já existe (pelo CEP).
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            // FIXME Caso não exista, integrar com o ViaCEP e persistir o retorno.
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        // FIXME Inserir Cliente, vinculando o Endereco (novo ou existente).
        clienteRepository.save(cliente);
    }
}
