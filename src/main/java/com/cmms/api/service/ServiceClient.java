package com.cmms.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmms.api.entity.Client;
import com.cmms.api.repository.ClientRepository;

@Service
public class ServiceClient implements IServiceClient {

	@Autowired
	ClientRepository clientRepository;

	@Override
	public Client createClient(Client client) {
		return clientRepository.save(client);
	}

	@Override
	public Client findClientById(int id) {
		return clientRepository.findById(id).get();
	}

	@Override
	public Client updateClient(Client client) {
		return clientRepository.save(client);
	}

	@Override
	public List<Client> findAllClients() {
		return clientRepository.findAll();
	}

	@Override
	public void deleteClient(Client client) {
		clientRepository.delete(client);

	}

}
