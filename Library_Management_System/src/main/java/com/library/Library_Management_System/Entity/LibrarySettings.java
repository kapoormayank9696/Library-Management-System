package com.library.Library_Management_System.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "library_settings")
@Getter @Setter @NoArgsConstructor
public class LibrarySettings {

    @Id
    private Integer id = 1;

    @Column(name = "loan_days", nullable = false)
    private Integer loanDays = 14;

    @Column(name = "fine_per_day", nullable = false)
    private BigDecimal finePerDay = BigDecimal.valueOf(5);
}