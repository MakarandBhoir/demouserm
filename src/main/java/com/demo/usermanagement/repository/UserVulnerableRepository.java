package com.demo.usermanagement.repository;

import com.demo.usermanagement.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class UserVulnerableRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<User> searchUsersByEmailUnsafe(String emailFragment) {
        String sql = "SELECT * FROM users WHERE email LIKE '%" + emailFragment + "%'";
        return entityManager.createNativeQuery(sql, User.class).getResultList();
    }
}