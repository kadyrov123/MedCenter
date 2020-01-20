package com.example.medcenter.repoitory;

import com.example.medcenter.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,Long> {
    List<UsersEntity> findAll();
    UsersEntity findUsersEntityByUsername(String username);
}
