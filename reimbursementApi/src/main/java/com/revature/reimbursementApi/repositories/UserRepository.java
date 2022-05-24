package com.revature.reimbursementApi.repositories;

import com.revature.reimbursementApi.entities.Reimbursement;
import com.revature.reimbursementApi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

   /*
   /**
    * This query gets all employees that a specific manager manages
    * @param managerId
    * @return list of employees managed by specific manager
    * /
   @Query("FROM User emp LEFT JOIN emp.manager man WHERE man.userId = :managerId")
   List<User> getAllEmployeesByManagerId(@Param("managerId") int managerId);
   */
}
