package com.revature.reimbursementApi.dtos;


import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RequestEmailDTO {

    private String email;
    private String subject;
    private int reimbursementId;

}
