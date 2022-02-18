package com.example.employeeturniketapp.service;

import com.example.employeeturniketapp.entity.temp.ApiResult;
import com.example.employeeturniketapp.payload.reqDto.EmpTurnActionReqDTO;

public interface EmpTurnActionService {

    ApiResult getAllEmpTurnAction();

    ApiResult getEmpTurnActionById(Integer id);

    ApiResult addNewEmpTurnAction(EmpTurnActionReqDTO empTurnActionReqDTO);

    ApiResult editEmpTurnAction(Integer id, EmpTurnActionReqDTO empTurnActionReqDTO);

    ApiResult deleteEmpTurnAction(Integer id);

    ApiResult getAllIncomeOrOutcomeEmpTurnActionByEmployee(EmpTurnActionReqDTO empTurnActionReqDTO);


}
