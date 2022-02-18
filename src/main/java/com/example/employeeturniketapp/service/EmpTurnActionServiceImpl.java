package com.example.employeeturniketapp.service;

import com.example.employeeturniketapp.entity.EmpTurnAction;
import com.example.employeeturniketapp.entity.Employee;
import com.example.employeeturniketapp.entity.temp.ApiResult;
import com.example.employeeturniketapp.payload.reqDto.EmpTurnActionReqDTO;
import com.example.employeeturniketapp.payload.resDto.EmpTurnActionResDTO;
import com.example.employeeturniketapp.repository.EmpTurnActionRepository;
import com.example.employeeturniketapp.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpTurnActionServiceImpl implements EmpTurnActionService {

    private final EmpTurnActionRepository empTurnActionRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public ApiResult getAllEmpTurnAction() {
        List<EmpTurnActionResDTO> empTurnActionResDTOS = empTurnActionRepository.findAll()
                .stream().map(this::castEmpTurnActionToEmpTurnActionResDto).collect(Collectors.toList());
        return new ApiResult(empTurnActionResDTOS, true);
    }

    @Override
    public ApiResult getEmpTurnActionById(Integer id) {
        Optional<EmpTurnAction> optionalEmpTurnAction = empTurnActionRepository.findById(id);
        if (optionalEmpTurnAction.isPresent()) {
            EmpTurnActionResDTO empTurnActionResDTO = castEmpTurnActionToEmpTurnActionResDto(optionalEmpTurnAction.get());
            return new ApiResult(empTurnActionResDTO, true);
        } else {
            return new ApiResult("Bunday Turniket tarihi topilmadi", false);
        }
    }

    @Override
    public ApiResult addNewEmpTurnAction(EmpTurnActionReqDTO empTurnActionReqDTO) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(empTurnActionReqDTO.getEmployeeId());
        if (optionalEmployee.isPresent()) {
            EmpTurnAction empTurnAction = castEmpTurnActionReqDtoToEmpTurnAction(empTurnActionReqDTO, optionalEmployee.get());
            empTurnActionRepository.save(empTurnAction);
            return new ApiResult("Employee Turnicet Action muvaffaqiyatli saqlandi", true);
        } else {
            return new ApiResult("Employee Turnicet Action uchun Employee topilmadi", false);
        }

    }

    /*
     UPDATE QILINGANDA CREATEDAT YA`NI YOZILGAN VAQTINI O`ZGARTIRILMADI.
     */
    @Override
    public ApiResult editEmpTurnAction(Integer id, EmpTurnActionReqDTO empTurnActionReqDTO) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(empTurnActionReqDTO.getEmployeeId());
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            Optional<EmpTurnAction> optionalEmpTurnAction = empTurnActionRepository.findById(id);
            if (optionalEmpTurnAction.isPresent()) {
                EmpTurnAction empTurnAction = optionalEmpTurnAction.get();
                empTurnAction.setEmployee(employee);
                empTurnAction.setActionIncomeYokiOutCome(empTurnActionReqDTO.getActionIncomeYokiOutCome());
                empTurnActionRepository.save(empTurnAction);
                return new ApiResult("Employee Turnicet Action muvaffaqiyatli o`zgartirildi", true);
            } else {
                return new ApiResult("Buntay Employee Turnicet Action topilmadi", false);
            }
        } else {
            return new ApiResult("Bunday Employee topilmadi", false);
        }
    }

    @Override
    public ApiResult deleteEmpTurnAction(Integer id) {
        try {
            empTurnActionRepository.deleteById(id);
            return new ApiResult("Employee Turnicet Action o`chirildi", true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult("Employee Turnicet Action o`chirilmadi", false);
        }
    }

    @Override
    public ApiResult getAllIncomeOrOutcomeEmpTurnActionByEmployee(EmpTurnActionReqDTO empTurnActionReqDTO) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(empTurnActionReqDTO.getEmployeeId());
        if (optionalEmployee.isPresent()) {
            /*
            AGAR INCOMEOROUTCOME NULL BO`LSA EMPLOYEE ID BO`YICHA KIRISH VA CHIQISHLARNING BARCHASINI CHIQARADI
             */
            if (empTurnActionReqDTO.getActionIncomeYokiOutCome() == null) {
                List<EmpTurnActionResDTO> allByEmployeeId = empTurnActionRepository.findAllByEmployeeId(empTurnActionReqDTO.getEmployeeId())
                        .stream().map(this::castEmpTurnActionToEmpTurnActionResDto).collect(Collectors.toList());
                return new ApiResult(allByEmployeeId, true);
            } else {
                /*
                ELSE GA TUSHSA EMPLOYEE ID BO`YICHA KIRISH YOKI CHIQISHLARNING BARCHASINI CHIQARADI
                 */
                List<EmpTurnActionResDTO> allByEmployeeIdAndActionIncomeYokiOutCome =
                        empTurnActionRepository.findAllByEmployeeIdAndActionIncomeYokiOutCome(empTurnActionReqDTO.getEmployeeId(), empTurnActionReqDTO.getActionIncomeYokiOutCome())
                                .stream().map(this::castEmpTurnActionToEmpTurnActionResDto).collect(Collectors.toList());
                return new ApiResult(allByEmployeeIdAndActionIncomeYokiOutCome, true);
            }
        } else {
            return new ApiResult("Employee Turnicet Actionlar uchun Employee topilmadi", false);
        }
    }
    /*
    EMPTURNACTIONREQDTO DAN EMPTURNACTION GA CAST QILIB EMPTURNACTION QAYTARUVCHI METOD
    */
    public EmpTurnAction castEmpTurnActionReqDtoToEmpTurnAction(EmpTurnActionReqDTO empTurnActionReqDTO, Employee employee) {
        EmpTurnAction empTurnAction = new EmpTurnAction();
        empTurnAction.setActionIncomeYokiOutCome(empTurnActionReqDTO.getActionIncomeYokiOutCome());
        empTurnAction.setEmployee(employee);
        return empTurnAction;
    }

    /*
    EMPTURNECTIONDAN EMPTURNACTIONRESDTO GA CAST QILIB EMPTURNACTIONRESDTO QAYTARUVCHI METOD
     */
    public EmpTurnActionResDTO castEmpTurnActionToEmpTurnActionResDto(EmpTurnAction empTurnAction) {
        EmpTurnActionResDTO empTurnActionResDTO = new EmpTurnActionResDTO();
        empTurnActionResDTO.setId(empTurnAction.getId());
        empTurnActionResDTO.setActionIncomeYokiOutCome(empTurnAction.getActionIncomeYokiOutCome());
        empTurnActionResDTO.setEmployee(empTurnAction.getEmployee());
        empTurnActionResDTO.setCreatedAt(empTurnAction.getCreatedAt());
        return empTurnActionResDTO;
    }
}
