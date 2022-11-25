package br.com.praise.portal.repository.client;

import br.com.praise.portal.model.Client;
import br.com.praise.portal.model.Device;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientsRepository extends MongoRepository<Client, String> {

    @Query("{chave :?0}")
    Optional<Client> findByKey(String chave);

}
