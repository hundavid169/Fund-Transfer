package com.fund.fund_transfer.model;

import com.fund.fund_transfer.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    public User (String userUuid) {
        this.userUuid = userUuid;
    }

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "user_id")
//    private Long userId;
    @Id
    @Column(name = "user_uuid", nullable = false, length = 36)
    private String userUuid;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 255)
    private String address;

    @Column(length = 20)
    private String tel;

    @Column(name = "id_card", length = 50)
    private String idCard;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

}
