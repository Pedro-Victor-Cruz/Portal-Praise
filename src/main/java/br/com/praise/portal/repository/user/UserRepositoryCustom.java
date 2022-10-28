package br.com.praise.portal.repository.user;
import java.util.List;
import br.com.praise.portal.model.User;

public interface UserRepositoryCustom {
    
    List<User> getAll();
    User login(String email, String password);
}
