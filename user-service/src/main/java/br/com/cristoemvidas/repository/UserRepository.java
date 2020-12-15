package br.com.cristoemvidas.repository;

import br.com.cristoemvidas.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {


    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}

