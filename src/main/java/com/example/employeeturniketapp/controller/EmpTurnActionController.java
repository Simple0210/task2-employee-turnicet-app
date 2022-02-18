package com.example.employeeturniketapp.controller;

import com.example.employeeturniketapp.entity.temp.ApiResult;
import com.example.employeeturniketapp.payload.reqDto.EmpTurnActionReqDTO;
import com.example.employeeturniketapp.service.EmpTurnActionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/action")
@Validated
public class EmpTurnActionController {

    private final EmpTurnActionServiceImpl empTurnActionService;

    @GetMapping("/getAllEmpTurnAction")
    public ApiResult getAllEmpTurnAction() {
        return empTurnActionService.getAllEmpTurnAction();
    }

    @GetMapping("/getEmpTurnActionById/{id}")
    public ApiResult getEmpTurnActionById(@PathVariable Integer id) {
        return empTurnActionService.getEmpTurnActionById(id);
    }

    @PostMapping("/addNewEmpTurnAction")
    public ApiResult addNewEmpTurnAction(@Valid @RequestBody EmpTurnActionReqDTO empTurnActionReqDTO) {
        return empTurnActionService.addNewEmpTurnAction(empTurnActionReqDTO);
    }

    @PutMapping("/editEmpTurnAction/{id}")
    public ApiResult editEmpTurnAction(@PathVariable Integer id, @Valid @RequestBody EmpTurnActionReqDTO empTurnActionReqDTO) {
        return empTurnActionService.editEmpTurnAction(id, empTurnActionReqDTO);
    }

    @DeleteMapping("/deleteEmpTurnAction/{id}")
    public ApiResult deleteEmpTurnAction(@PathVariable Integer id) {
        return empTurnActionService.deleteEmpTurnAction(id);
    }

    @GetMapping("/getAllEmpTurnActionByEmployee")
    public ApiResult getAllIncomeOrOutcomeEmpTurnActionByEmployeeId(@Valid @RequestBody EmpTurnActionReqDTO empTurnActionReqDTO) {
        return empTurnActionService.getAllIncomeOrOutcomeEmpTurnActionByEmployee(empTurnActionReqDTO);
    }

}
