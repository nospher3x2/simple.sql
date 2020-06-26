package com.nosphery.simplesql.examples;

import com.nosphery.simplesql.examples.client.Client;
import com.nosphery.simplesql.examples.product.Product;
import com.nosphery.simplesql.repository.SimpleRepository;

/**
 * @author oNospher
 **/
public class Examples {

    public static void main(String[] args) {
        SimpleRepository<Client> clientRepository = new SimpleRepository<>();
        clientRepository.createTable().execute();

        Client clientInserted = clientRepository.insert().execute(
                new Client(
                        0,
                        "Ryan",
                        "Nosphery",
                        "nosphery@gmail.com",
                        null
                )
        );

        Client clientFounded = clientRepository.findOne().execute("id", clientInserted.getId());


        System.out.println(clientInserted.getId());
        System.out.println(clientFounded.toString());
    }
}
