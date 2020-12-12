package br.com.cristoemvidas.repository;

import br.com.cristoemvidas.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {


    User findFirstByUsernameAndActive(String username, Boolean active);

    User findFirstByUsername(String username);

}

