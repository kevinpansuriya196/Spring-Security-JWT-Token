package com.example.SecurityDemo.repository;

import com.example.SecurityDemo.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String username);

    //    SELECT * FROM security.users where roles="USER";
    @Query(nativeQuery = true, value = "SELECT * FROM security.users where roles='USER' ")
    List<UserInfo> findUsers();

    //    SELECT * FROM security.users where roles="ADMIN";
    @Query(nativeQuery = true, value = "SELECT * FROM security.users where roles='ADMIN' ")
    List<UserInfo> findAdmins();
}
