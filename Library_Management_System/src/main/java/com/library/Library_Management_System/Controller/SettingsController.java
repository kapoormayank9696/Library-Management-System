package com.library.Library_Management_System.Controller;

import com.library.Library_Management_System.Entity.LibrarySettings;
import com.library.Library_Management_System.Services.SettingsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping
    public LibrarySettings get() {
        return settingsService.get();
    }

    @PutMapping
    public LibrarySettings update(@RequestBody LibrarySettings settings) {
        return settingsService.update(settings);
    }
}