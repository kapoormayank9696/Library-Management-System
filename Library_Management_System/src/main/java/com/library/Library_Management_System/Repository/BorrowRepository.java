package com.library.Library_Management_System.Repository;

import com.library.Library_Management_System.Entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends JpaRepository<Borrow,Integer> {}
