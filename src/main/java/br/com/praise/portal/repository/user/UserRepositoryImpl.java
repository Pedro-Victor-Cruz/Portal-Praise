package br.com.praise.portal.repository.user;
import br.com.praise.portal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @Autowired
    MongoTemplate template;

    @Override
    public List<User> getAll() {
        // TODO Auto-generated method stub
        return template.findAll(User.class);
    }

    @Override
    public User login(String email, String password) {
        return null;
    }


}
