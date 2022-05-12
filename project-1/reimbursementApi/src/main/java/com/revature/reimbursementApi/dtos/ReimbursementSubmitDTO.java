package com.revature.reimbursementApi.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ReimbursementSubmitDTO {

    private String itemName;
    private double itemCost;
    private int empId;


}
