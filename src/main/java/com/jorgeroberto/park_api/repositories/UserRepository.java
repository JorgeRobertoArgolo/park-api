package com.jorgeroberto.park_api.repositories;

import com.jorgeroberto.park_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
