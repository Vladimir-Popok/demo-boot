package com.geekbrains.demoboot.repositories;

import com.geekbrains.demoboot.entities.Product;
import com.geekbrains.demoboot.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    Role findRoleById(Long id);
}
