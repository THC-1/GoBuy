package com.example.backend.modules.product.service;

import com.example.backend.common.api.PageResponse;
import java.util.List;
import java.util.Map;

public interface ProductService {

    List<Map<String, Object>> getCategoryTree();

    void createCategory(Map<String, Object> request);

    void updateCategory(Long id, Map<String, Object> request);

    void deleteCategory(Long id);

    PageResponse<Map<String, Object>> getTemplatePage(long pageNum, long pageSize, String keyword);

    Map<String, Object> getTemplateDetail(Long id);

    void createTemplate(Map<String, Object> request);

    void updateTemplate(Long id, Map<String, Object> request);

    void deleteTemplate(Long id);

    PageResponse<Map<String, Object>> getSpuPage(long pageNum, long pageSize, String keyword, Integer status);

    Map<String, Object> getSpuDetail(Long id);

    void createSpu(Map<String, Object> request);

    void updateSpu(Long id, Map<String, Object> request);

    void updateSpuStatus(Long id, Integer status);

    void deleteSpu(Long id);

    List<Map<String, Object>> getRecommendProducts();

    Map<String, Object> getProductDetail(Long id);

    Map<String, Object> getSpuSnapshot(Long id);

    Map<String, Object> getSkuSnapshot(Long skuId);
}
