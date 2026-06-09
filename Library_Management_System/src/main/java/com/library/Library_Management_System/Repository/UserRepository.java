package com.library.Library_Management_System.Repository;

import com.library.Library_Management_System.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {}
