package com.fund.fund_transfer.model;

import com.fund.fund_transfer.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "currency")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", unique = true, nullable = false, length = 3)
    private String code;

    @Column(name = "description", nullable = false)
    private String description;

    public Currency(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
