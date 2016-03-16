package eripay.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import eripay.domain.User;

@Repository
public interface ItemRepository extends MongoRepository<User, String> {

}
