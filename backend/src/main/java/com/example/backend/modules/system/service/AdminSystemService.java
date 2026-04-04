package com.example.backend.modules.system.service;

import com.example.backend.common.api.PageResponse;
import java.util.List;
import java.util.Map;

public interface AdminSystemService {

    Map<String, Object> getDashboardOverview();

    PageResponse<Map<String, Object>> getUserPage(long pageNum, long pageSize, String username, Integer status);

    void createUser(Map<String, Object> request);

    void updateUser(Long id, Map<String, Object> request);

    void updateUserStatus(Long id, Integer status);

    void deleteUser(Long id);

    PageResponse<Map<String, Object>> getRolePage(long pageNum, long pageSize, String roleName, Integer status);

    List<Map<String, Object>> getRoleOptions();

    void createRole(Map<String, Object> request);

    void updateRole(Long id, Map<String, Object> request);

    void deleteRole(Long id);

    List<Map<String, Object>> getMenuTree(String menuName, Integer status);

    void createMenu(Map<String, Object> request);

    void updateMenu(Long id, Map<String, Object> request);

    void deleteMenu(Long id);

    PageResponse<Map<String, Object>> getDictTypePage(long pageNum, long pageSize, String dictName, String dictType, Integer status);

    List<Map<String, Object>> getDictItems(String dictType);

    void createDictType(Map<String, Object> request);

    void updateDictType(Long id, Map<String, Object> request);

    void deleteDictType(Long id);

    void createDictItem(Map<String, Object> request);

    void updateDictItem(Long id, Map<String, Object> request);

    void deleteDictItem(Long id);
}
