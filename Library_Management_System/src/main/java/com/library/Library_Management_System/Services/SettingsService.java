package com.library.Library_Management_System.Services;

import com.library.Library_Management_System.Entity.LibrarySettings;
import com.library.Library_Management_System.Repository.LibrarySettingsRepository;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    private final LibrarySettingsRepository repo;

    public SettingsService(LibrarySettingsRepository repo) {
        this.repo = repo;
    }

    public LibrarySettings get() {
        return repo.findById(1).orElseGet(() -> repo.save(new LibrarySettings()));
    }

    public LibrarySettings update(LibrarySettings in) {
        if (in.getLoanDays() == null || in.getLoanDays() < 1 || in.getLoanDays() > 365)
            throw new RuntimeException("Loan period must be between 1 and 365 days.");
        if (in.getFinePerDay() == null || in.getFinePerDay().signum() < 0)
            throw new RuntimeException("Fine per day cannot be negative.");

        LibrarySettings s = get();
        s.setLoanDays(in.getLoanDays());
        s.setFinePerDay(in.getFinePerDay());
        return repo.save(s);
    }
}