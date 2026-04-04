package com.example.backend.modules.system.controller;

import com.example.backend.common.api.Result;
import com.example.backend.modules.system.service.AdminSystemService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminSystemController {

    private final AdminSystemService adminSystemService;

    public AdminSystemController(AdminSystemService adminSystemService) {
        this.adminSystemService = adminSystemService;
    }

    @GetMapping("/dashboard/overview")
    public Result<Map<String, Object>> dashboardOverview() {
        return Result.success(adminSystemService.getDashboardOverview());
    }

    @GetMapping("/system/users")
    public Result<?> userPage(
            @RequestParam(defaultValue = "1") long pageNum,
            @RequestParam(defaultValue = "10") long pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer status
    ) {
        return Result.success(adminSystemService.getUserPage(pageNum, pageSize, username, status));
    }

    @PostMapping("/system/users")
    public Result<Void> createUser(@RequestBody Map<String, Object> request) {
        adminSystemService.createUser(request);
        return Result.success();
    }

    @PutMapping("/system/users/{id}")
    public Result<Void> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        adminSystemService.updateUser(id, request);
        return Result.success();
    }

    @PatchMapping("/system/users/{id}/status")
    public Result<Void> updateUserStatus(@PathVariable Long id, @RequestParam Integer status) {
        adminSystemService.updateUserStatus(id, status);
        return Result.success();
    }

    @DeleteMapping("/system/users/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        adminSystemService.deleteUser(id);
        return Result.success();
    }

    @GetMapping("/system/roles")
    public Result<?> rolePage(
            @RequestParam(defaultValue = "1") long pageNum,
            @RequestParam(defaultValue = "10") long pageSize,
            @RequestParam(required = false) String roleName,
            @RequestParam(required = false) Integer status
    ) {
        return Result.success(adminSystemService.getRolePage(pageNum, pageSize, roleName, status));
    }

    @GetMapping("/system/roles/options")
    public Result<List<Map<String, Object>>> roleOptions() {
        return Result.success(adminSystemService.getRoleOptions());
    }

    @PostMapping("/system/roles")
    public Result<Void> createRole(@RequestBody Map<String, Object> request) {
        adminSystemService.createRole(request);
        return Result.success();
    }

    @PutMapping("/system/roles/{id}")
    public Result<Void> updateRole(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        adminSystemService.updateRole(id, request);
        return Result.success();
    }

    @DeleteMapping("/system/roles/{id}")
    public Result<Void> deleteRole(@PathVariable Long id) {
        adminSystemService.deleteRole(id);
        return Result.success();
    }

    @GetMapping("/system/menus/tree")
    public Result<List<Map<String, Object>>> menuTree(
            @RequestParam(required = false) String menuName,
            @RequestParam(required = false) Integer status
    ) {
        return Result.success(adminSystemService.getMenuTree(menuName, status));
    }

    @PostMapping("/system/menus")
    public Result<Void> createMenu(@RequestBody Map<String, Object> request) {
        adminSystemService.createMenu(request);
        return Result.success();
    }

    @PutMapping("/system/menus/{id}")
    public Result<Void> updateMenu(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        adminSystemService.updateMenu(id, request);
        return Result.success();
    }

    @DeleteMapping("/system/menus/{id}")
    public Result<Void> deleteMenu(@PathVariable Long id) {
        adminSystemService.deleteMenu(id);
        return Result.success();
    }

    @GetMapping("/system/dicts")
    public Result<?> dictTypePage(
            @RequestParam(defaultValue = "1") long pageNum,
            @RequestParam(defaultValue = "10") long pageSize,
            @RequestParam(required = false) String dictName,
            @RequestParam(required = false) String dictType,
            @RequestParam(required = false) Integer status
    ) {
        return Result.success(adminSystemService.getDictTypePage(pageNum, pageSize, dictName, dictType, status));
    }

    @GetMapping("/system/dicts/{dictType}/items")
    public Result<List<Map<String, Object>>> dictItems(@PathVariable String dictType) {
        return Result.success(adminSystemService.getDictItems(dictType));
    }

    @PostMapping("/system/dicts")
    public Result<Void> createDictType(@RequestBody Map<String, Object> request) {
        adminSystemService.createDictType(request);
        return Result.success();
    }

    @PutMapping("/system/dicts/{id}")
    public Result<Void> updateDictType(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        adminSystemService.updateDictType(id, request);
        return Result.success();
    }

    @DeleteMapping("/system/dicts/{id}")
    public Result<Void> deleteDictType(@PathVariable Long id) {
        adminSystemService.deleteDictType(id);
        return Result.success();
    }

    @PostMapping("/system/dict-items")
    public Result<Void> createDictItem(@RequestBody Map<String, Object> request) {
        adminSystemService.createDictItem(request);
        return Result.success();
    }

    @PutMapping("/system/dict-items/{id}")
    public Result<Void> updateDictItem(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        adminSystemService.updateDictItem(id, request);
        return Result.success();
    }

    @DeleteMapping("/system/dict-items/{id}")
    public Result<Void> deleteDictItem(@PathVariable Long id) {
        adminSystemService.deleteDictItem(id);
        return Result.success();
    }
}
