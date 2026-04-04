package com.example.backend.modules.user.controller;

import com.example.backend.common.api.Result;
import com.example.backend.modules.user.service.UserCenterService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserCenterController {

    private final UserCenterService userCenterService;

    public UserCenterController(UserCenterService userCenterService) {
        this.userCenterService = userCenterService;
    }

    @GetMapping("/profile")
    public Result<Map<String, Object>> profile() {
        return Result.success(userCenterService.getProfile());
    }

    @PutMapping("/profile")
    public Result<Void> updateProfile(@RequestBody Map<String, Object> request) {
        userCenterService.updateProfile(request);
        return Result.success();
    }

    @GetMapping("/addresses")
    public Result<List<Map<String, Object>>> addresses() {
        return Result.success(userCenterService.listAddresses());
    }

    @PostMapping("/addresses")
    public Result<Void> createAddress(@RequestBody Map<String, Object> request) {
        userCenterService.createAddress(request);
        return Result.success();
    }

    @PutMapping("/addresses/{id}")
    public Result<Void> updateAddress(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        userCenterService.updateAddress(id, request);
        return Result.success();
    }

    @DeleteMapping("/addresses/{id}")
    public Result<Void> deleteAddress(@PathVariable Long id) {
        userCenterService.deleteAddress(id);
        return Result.success();
    }

    @PatchMapping("/addresses/{id}/default")
    public Result<Void> setDefaultAddress(@PathVariable Long id) {
        userCenterService.setDefaultAddress(id);
        return Result.success();
    }

    @GetMapping("/favorites")
    public Result<List<Map<String, Object>>> favorites() {
        return Result.success(userCenterService.listFavorites());
    }

    @PostMapping("/favorites")
    public Result<Void> addFavorite(@RequestBody Map<String, Object> request) {
        userCenterService.addFavorite(request);
        return Result.success();
    }

    @DeleteMapping("/favorites/{spuId}")
    public Result<Void> deleteFavorite(@PathVariable Long spuId) {
        userCenterService.deleteFavorite(spuId);
        return Result.success();
    }
}
