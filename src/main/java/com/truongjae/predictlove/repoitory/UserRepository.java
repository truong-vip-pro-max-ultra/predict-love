package com.truongjae.predictlove.repoitory;

import com.truongjae.predictlove.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findOneByUsername(String username);
    User findOneByUsernameAndPassword(String username,String password);
}
