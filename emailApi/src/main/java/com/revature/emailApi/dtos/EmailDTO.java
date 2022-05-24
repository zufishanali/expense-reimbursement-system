package com.revature.emailApi.dtos;

public class EmailDTO {

    private String email;
    private String subject;
    private int reimbursementId;

    public int getReimbursementId() {
        return reimbursementId;
    }

    public String getSubject() {
        return subject;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "EmailDTO{" +
                "email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", reimbursementId=" + reimbursementId +
                '}';
    }
}
