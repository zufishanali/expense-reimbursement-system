package com.revature.reimbursementApi.dtos;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ReimbursementUpdateDTO {
    private String updateType;
    private int reimbursementId;
    private int managerId;
    private int reassignId;
}

