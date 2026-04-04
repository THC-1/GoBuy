package com.example.backend.modules.user.service;

import java.util.List;
import java.util.Map;

public interface UserCenterService {

    Map<String, Object> getProfile();

    void updateProfile(Map<String, Object> request);

    List<Map<String, Object>> listAddresses();

    void createAddress(Map<String, Object> request);

    void updateAddress(Long id, Map<String, Object> request);

    void deleteAddress(Long id);

    void setDefaultAddress(Long id);

    List<Map<String, Object>> listFavorites();

    void addFavorite(Map<String, Object> request);

    void deleteFavorite(Long spuId);
}
