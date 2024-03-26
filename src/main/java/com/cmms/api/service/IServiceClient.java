package com.cmms.api.service;

import java.util.List;

import com.cmms.api.entity.Client;

public interface IServiceClient {

	public Client createClient(Client client);

    public Client findClientById(int id);

    public Client updateClient(Client client);

    public List<Client> findAllClients();

    public void deleteClient(Client client);

}
