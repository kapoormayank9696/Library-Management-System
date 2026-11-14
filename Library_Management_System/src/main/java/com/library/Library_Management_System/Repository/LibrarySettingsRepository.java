package com.library.Library_Management_System.Repository;

import com.library.Library_Management_System.Entity.LibrarySettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrarySettingsRepository extends JpaRepository<LibrarySettings, Integer> {
}