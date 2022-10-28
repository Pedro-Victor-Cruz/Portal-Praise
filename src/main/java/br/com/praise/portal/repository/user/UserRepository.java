package br.com.praise.portal.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.praise.portal.model.User;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    
    User findFirstByID(String _id);
    @Query("{email :?0}")
    List<User> login(String email);
}
