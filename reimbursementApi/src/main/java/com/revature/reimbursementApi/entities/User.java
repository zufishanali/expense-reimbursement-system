package com.revature.reimbursementApi.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;


@Entity
@Table(name="Users")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name="user_id", columnDefinition = "AUTO_INCREMENT")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    private String email;
    private String username;

    @Column(name="passwrd")
    private String password;

    @Column(name="user_role")
    private String role;

    @Column(name="manager_id", nullable = true)
    private int managerId;

    /*
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="manager_id", referencedColumnName = "user_id")
    private User manager;
     */


}
