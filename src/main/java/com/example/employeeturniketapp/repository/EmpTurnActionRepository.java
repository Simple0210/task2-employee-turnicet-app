package com.example.employeeturniketapp.repository;

import com.example.employeeturniketapp.entity.EmpTurnAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmpTurnActionRepository extends JpaRepository<EmpTurnAction, Integer> {

    //EMPLOYEE ID BO`YICHA BARCHA EMPTURNACTIONLARNI LISTINI CHIQARIB BERADI.
    List<EmpTurnAction> findAllByEmployeeId(Integer employee_id);

    //EMPLOYEE ID VA KIRGAN YOKI CHIQQANLIGINI BERILSA BOOLEANDA BERILSA O`SHA EMPTURNACTIONLARNI LISTINI CHIQARIB BERADI
    @Query(name = "select * from emp_turn_action where employee_id = ?1 and action_income_or_out_come = ?2;", nativeQuery = true)
    List<EmpTurnAction> findAllByEmployeeIdAndActionIncomeYokiOutCome(Integer employee_id, Boolean actionIncomeYokiOutCome);

}
