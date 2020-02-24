package hu.onlab.bevasarlolista.model;

import lombok.Getter;

import javax.security.auth.Subject;
import java.security.Principal;

public class UserPrincipal implements Principal {

    @Getter
    final private Integer id;

    public UserPrincipal(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return id.toString();
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }


}
