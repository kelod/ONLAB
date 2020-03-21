package hu.onlab.bevasarlolista.repository;

import hu.onlab.bevasarlolista.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("select u from User u where u.userName = ?1")
    User findByNameIs(String name);

}
