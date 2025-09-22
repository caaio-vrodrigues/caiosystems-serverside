package com.example.caiosystems.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.caiosystems.infrastructure.entity.UserClient;

@Repository
public interface UserClientRepository extends JpaRepository<UserClient, Long> {}
