package com.example.eventOrganizer.Controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventOrganizer.Entity.RoleEntity;
import com.example.eventOrganizer.Service.RoleService;
import com.example.eventOrganizer.Uitility.ResponseHandler;

@RestController
@CrossOrigin("*")
@ResponseBody
@RequestMapping("/api/role")
public class RoleController {
    private static Logger logger = LogManager.getLogger(RoleController.class);

    @Autowired
    RoleService roleService;

    @PostMapping("/roleCreate")
    public ResponseEntity<ResponseHandler> AddRole(@RequestBody RoleEntity roleEntity) {
        logger.info("RoleController :: START :: AddRole() ::");
        try {
            if (roleEntity != null) {
                roleService.addRoleService(roleEntity);
                return ResponseEntity.ok(new ResponseHandler("200", "Role Added Successfully"));
            }
            return null;
        } catch (Exception ex) {
            logger.error("Exception in RoleController :: FAILED :: AddRole() :: Internal Server Error ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Error Adding Role: " + ex.getMessage()));
        }
    }

    @PutMapping("/editRole")
    public ResponseEntity<ResponseHandler> EditRole(@RequestBody RoleEntity roleEntity) {
        logger.info("RoleController :: START :: EditRole() ::");
        try {
            if (roleEntity != null) {
                roleService.editRoleService(roleEntity);
                return ResponseEntity.ok(new ResponseHandler("200", "Role Updated Successfully"));
            }
            throw new NullPointerException("RoleEntity is NULL Exception");
        } catch (Exception ex) {
            logger.error("Exception in RoleController :: FAILED :: EditRole() :: Internal Server Error ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Error Updated Contact :: " + ex.getMessage()));
        }
    }

    @GetMapping("/GetAllRole")
    public ResponseEntity<ResponseHandler> GetAllRole(
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        logger.info("RoleController :: START :: GetAllRole() ::");
        try {
            logger.info("RoleController :: PROCESS :: GetAllRole() :: Success");
            List<RoleEntity> rolesList = roleService.getAllRoleService(pageNumber, pageSize);
            return ResponseEntity.ok(new ResponseHandler("200", "Success", List.of(rolesList)));
        } catch (Exception ex) {
            logger.error("Exception in RoleController :: FAILED :: GetAllRole() :: Data Not Found");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Data Not Found", List.of()));
        }
    }

    @GetMapping("/getRoleByID")
    public ResponseEntity<ResponseHandler> getRoleByID(@RequestParam Long roleID) {
        try {
            logger.info("RoleController :: START :: getRoleByID() ::" + roleID);
            RoleEntity roleObject = roleService.getRoleService(roleID);
            if (roleObject != null) {
                logger.info("RoleController:: PROCESS :: getRoleByID() :: Role SuccessFully");
                return ResponseEntity.ok(new ResponseHandler("200", "Success", List.of(roleObject)));
            } else {
                logger.error("Exception in RoleController :: FAILED :: getRoleByID() :: Data not found");
                return ResponseEntity.ok(new ResponseHandler("500", "Data not found", List.of()));
            }
        } catch (Exception e) {
            logger.error("Exception in RoleController :: FAILED :: getRoleByID() :: Internal Server Error");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Internal Server Error", List.of()));
        }
    }

    @DeleteMapping("/deleteRoleByID")
    public ResponseEntity<ResponseHandler> DeleteRoleByID(@RequestParam Long roleID) {
        logger.info("RoleController :: START :: DeleteRoleByID() ::" + roleID);
        try {
            boolean roleObject = roleService.deleteRoleService(roleID);

            if (roleObject) {
                logger.info("RoleController:: PROCESS :: DeleteRoleByID() :: Role Deleted SuccessFully");
                return ResponseEntity.ok(new ResponseHandler("200", "Role Deleted SuccessFully"));
            } else {
                logger.error("Exception in RoleController :: FAILED :: DeleteRoleByID() :: Data not found");
                return ResponseEntity.ok(new ResponseHandler("401", "Data not found", List.of()));
            }
        } catch (Exception e) {
            logger.error("Exception in RoleController :: FAILED :: DeleteRoleByID() :: Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Internal Server Error", List.of()));
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<ResponseHandler> filterRole(@RequestBody RoleEntity filterDTO) {
        try {
            logger.info("RoleController :: PROCESS :: filterRole() :: Success");
            List<RoleEntity> roleList = roleService.filterRoleByAttributesService(filterDTO);
            if (roleList.size() > 0) {
                return ResponseEntity.ok(new ResponseHandler("200", "Success", List.of(roleList)));
            } else {
                return ResponseEntity.ok(new ResponseHandler("500", "Data Not Found", List.of(roleList)));
            }
        } catch (Exception ex) {
            logger.error("Exception in RoleController :: FAILED :: filterRole() :: Data Not Found");
            return ResponseEntity.ok(new ResponseHandler("500", "Data Not Found", List.of()));
        }

    }
}
