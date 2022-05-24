package com.revature.reimbursementApi.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="Reimbursements")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Reimbursement {

    @Id
    @Column(name="reimbursement_id", columnDefinition = "AUTO_INCREMENT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reimbursementId;

    @Column(name="item_name")
    private String itemName;

    @Column(name="item_cost")
    private double itemCost;

    @Column(name="request_status")
    private String requestStatus;

    @Column(name="request_date")
    private Timestamp requestDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="submitted_emp_id")
    private User submittedByEmp;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="updated_man_id")
    private User updatedByMan;


}
