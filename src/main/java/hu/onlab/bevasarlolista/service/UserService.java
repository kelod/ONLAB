package hu.onlab.bevasarlolista.service;

import hu.onlab.bevasarlolista.dto.UserCreationDto;
import hu.onlab.bevasarlolista.model.User;
import hu.onlab.bevasarlolista.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean validateAccountLogin(String userName, String password){
        User user = userRepository.findByNameIs(userName);

        if(user == null)
            return false;

        return password.equals(user.getPassword());
    }

    @Transactional(readOnly = true)
    public User findByUserName(String userName){
        return userRepository.findByNameIs(userName);
    }

    @Transactional
    public void createUser(UserCreationDto user) {
        User newUser = User.builder().userName(user.getUserName()).password(user.getPassword())
                .build();
        userRepository.save(newUser);
    }

    @Transactional(readOnly = true)
    public User findById(Integer id) {
        return userRepository.findById(id).get();
    }
}
