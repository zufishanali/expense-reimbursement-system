package com.revature.reimbursementApi.repositories;

import com.revature.reimbursementApi.entities.Reimbursement;
import com.revature.reimbursementApi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Integer> {

    @Query("FROM Reimbursement r INNER JOIN r.submittedByEmp ru WHERE ru.userId = :empId")
    Optional<Reimbursement> findAllBySubmittedEmpId(@Param("empId") int empId);

    //List<Reimbursement> findAllByRequestStatus(String requestStatus);


}
