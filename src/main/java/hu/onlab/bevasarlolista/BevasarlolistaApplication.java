package hu.onlab.bevasarlolista;

import hu.onlab.bevasarlolista.model.User;
import hu.onlab.bevasarlolista.repository.UserRepository;
import hu.onlab.bevasarlolista.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class BevasarlolistaApplication implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(BevasarlolistaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User testUser = User.builder().userName("commanLineUser").password("commandLinePassw").build();
        userRepository.save(testUser);
        userService.createListaWithName(testUser, "commandLineListName");
    }
}
