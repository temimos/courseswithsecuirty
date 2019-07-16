package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository <Role, Long> {
    Role findByRole(String role);
}

