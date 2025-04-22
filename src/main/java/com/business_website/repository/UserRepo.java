package com.business_website.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.business_website.models.User;


@Repository
public interface UserRepo extends JpaRepository<User, Long>{
    
    User findByEmail(String email);

    @Query(value = "SELECT CASE WHEN count(*) > 0 THEN true ELSE false END FROM users where email = :email",  nativeQuery = true)
    boolean checkUsername(String email);
}
