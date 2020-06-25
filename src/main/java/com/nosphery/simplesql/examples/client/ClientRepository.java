package com.nosphery.simplesql.examples.client;

import com.nosphery.simplesql.repository.SimpleRepository;

/**
 * @author oNospher
 **/
public class ClientRepository extends SimpleRepository<Client> {
    public ClientRepository(Class<Client> clazz) {
        super(clazz);
    }
}
