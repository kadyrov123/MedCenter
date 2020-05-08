package com.example.medcenter.repoitory;

import com.example.medcenter.entity.RoleEntity;
import com.example.medcenter.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,Long> {
    List<UsersEntity> findAll();

    @Query("select e from UsersEntity e where lower(concat(trim(e.name), ' ' , trim(e.surname))) like %:searchString%")
    List<UsersEntity> search(@Param("searchString") String searchString);

    List<UsersEntity> findUsersEntitiesByRolesContaining(RoleEntity role);

    UsersEntity findUsersEntityByUsername(String username);
    UsersEntity findUsersEntityById(long id);


}
