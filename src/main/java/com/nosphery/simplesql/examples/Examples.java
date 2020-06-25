package com.nosphery.simplesql.examples;

import com.nosphery.simplesql.examples.client.Client;
import com.nosphery.simplesql.examples.client.ClientRepository;

/**
 * @author oNospher
 **/
public class Examples {

    public static void main(String[] args) {
        ClientRepository clientRepository = new ClientRepository(Client.class);

        clientRepository.createTableFunction().execute();

    }
}
