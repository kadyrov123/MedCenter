package com.example.medcenter.repoitory;

import com.example.medcenter.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity , Integer> {
    RoleEntity getRoleEntityByRole(String roleName);
    RoleEntity getRoleEntityById(int id);



//    List<RoleEntity> findRoleEntitiesBy
}

