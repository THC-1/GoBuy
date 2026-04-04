package com.example.backend.modules.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.common.api.PageResponse;
import com.example.backend.common.api.ResultCode;
import com.example.backend.common.exception.BusinessException;
import com.example.backend.modules.product.entity.ProductAttributeTemplate;
import com.example.backend.modules.product.entity.ProductAttributeTemplateItem;
import com.example.backend.modules.product.entity.ProductCategory;
import com.example.backend.modules.product.entity.ProductReview;
import com.example.backend.modules.product.entity.ProductSku;
import com.example.backend.modules.product.entity.ProductSpu;
import com.example.backend.modules.product.mapper.ProductAttributeTemplateItemMapper;
import com.example.backend.modules.product.mapper.ProductAttributeTemplateMapper;
import com.example.backend.modules.product.mapper.ProductCategoryMapper;
import com.example.backend.modules.product.mapper.ProductReviewMapper;
import com.example.backend.modules.product.mapper.ProductSkuMapper;
import com.example.backend.modules.product.mapper.ProductSpuMapper;
import com.example.backend.modules.product.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductCategoryMapper productCategoryMapper;
    private final ProductAttributeTemplateMapper productAttributeTemplateMapper;
    private final ProductAttributeTemplateItemMapper productAttributeTemplateItemMapper;
    private final ProductSpuMapper productSpuMapper;
    private final ProductSkuMapper productSkuMapper;
    private final ProductReviewMapper productReviewMapper;
    private final ObjectMapper objectMapper;

    public ProductServiceImpl(
            ProductCategoryMapper productCategoryMapper,
            ProductAttributeTemplateMapper productAttributeTemplateMapper,
            ProductAttributeTemplateItemMapper productAttributeTemplateItemMapper,
            ProductSpuMapper productSpuMapper,
            ProductSkuMapper productSkuMapper,
            ProductReviewMapper productReviewMapper,
            ObjectMapper objectMapper
    ) {
        this.productCategoryMapper = productCategoryMapper;
        this.productAttributeTemplateMapper = productAttributeTemplateMapper;
        this.productAttributeTemplateItemMapper = productAttributeTemplateItemMapper;
        this.productSpuMapper = productSpuMapper;
        this.productSkuMapper = productSkuMapper;
        this.productReviewMapper = productReviewMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Map<String, Object>> getCategoryTree() {
        List<ProductCategory> categories = productCategoryMapper.selectList(
                Wrappers.<ProductCategory>lambdaQuery().orderByAsc(ProductCategory::getSortNo).orderByAsc(ProductCategory::getId)
        );
        return buildCategoryNodes(categories, 0L);
    }

    @Override
    public void createCategory(Map<String, Object> request) {
        ProductCategory category = new ProductCategory();
        fillCategory(category, request);
        category.setLevelNo(resolveCategoryLevel(category.getParentId()));
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        productCategoryMapper.insert(category);
    }

    @Override
    public void updateCategory(Long id, Map<String, Object> request) {
        ProductCategory category = requireCategory(id);
        fillCategory(category, request);
        category.setLevelNo(resolveCategoryLevel(category.getParentId()));
        category.setUpdatedAt(LocalDateTime.now());
        productCategoryMapper.updateById(category);
    }

    @Override
    public void deleteCategory(Long id) {
        productCategoryMapper.delete(Wrappers.<ProductCategory>lambdaQuery().eq(ProductCategory::getId, id).or().eq(ProductCategory::getParentId, id));
    }

    @Override
    public PageResponse<Map<String, Object>> getTemplatePage(long pageNum, long pageSize, String keyword) {
        Page<ProductAttributeTemplate> page = productAttributeTemplateMapper.selectPage(
                Page.of(pageNum, pageSize),
                Wrappers.<ProductAttributeTemplate>lambdaQuery()
                        .like(StringUtils.hasText(keyword), ProductAttributeTemplate::getTemplateName, keyword)
                        .orderByDesc(ProductAttributeTemplate::getId)
        );
        List<Map<String, Object>> records = page.getRecords().stream().map(template -> {
            List<ProductAttributeTemplateItem> items = listTemplateItems(template.getId());
            long attrCount = items.stream().filter(item -> "ATTR".equalsIgnoreCase(item.getItemType())).count();
            long specCount = items.stream().filter(item -> "SPEC".equalsIgnoreCase(item.getItemType())).count();
            return Map.<String, Object>of(
                    "id", template.getId(),
                    "name", template.getTemplateName(),
                    "attrCount", attrCount,
                    "specCount", specCount,
                    "remark", stringValue(template.getRemark()),
                    "createTime", template.getCreatedAt()
            );
        }).toList();
        return new PageResponse<>(page.getTotal(), page.getCurrent(), page.getSize(), records);
    }

    @Override
    public Map<String, Object> getTemplateDetail(Long id) {
        ProductAttributeTemplate template = requireTemplate(id);
        List<Map<String, Object>> attrs = new ArrayList<>();
        List<Map<String, Object>> specs = new ArrayList<>();
        for (ProductAttributeTemplateItem item : listTemplateItems(id)) {
            Map<String, Object> node = Map.of(
                    "id", item.getId(),
                    "name", item.getItemName(),
                    "values", splitValues(item.getItemValues()),
                    "sortNo", item.getSortNo(),
                    "status", item.getStatus()
            );
            if ("SPEC".equalsIgnoreCase(item.getItemType())) {
                specs.add(node);
            } else {
                attrs.add(node);
            }
        }
        return Map.of(
                "id", template.getId(),
                "name", template.getTemplateName(),
                "remark", stringValue(template.getRemark()),
                "attrs", attrs,
                "specs", specs
        );
    }

    @Override
    @Transactional
    public void createTemplate(Map<String, Object> request) {
        ProductAttributeTemplate template = new ProductAttributeTemplate();
        fillTemplate(template, request);
        template.setCreatedAt(LocalDateTime.now());
        template.setUpdatedAt(LocalDateTime.now());
        productAttributeTemplateMapper.insert(template);
        saveTemplateItems(template.getId(), request.get("attrs"), "ATTR");
        saveTemplateItems(template.getId(), request.get("specs"), "SPEC");
    }

    @Override
    @Transactional
    public void updateTemplate(Long id, Map<String, Object> request) {
        ProductAttributeTemplate template = requireTemplate(id);
        fillTemplate(template, request);
        template.setUpdatedAt(LocalDateTime.now());
        productAttributeTemplateMapper.updateById(template);
        productAttributeTemplateItemMapper.delete(Wrappers.<ProductAttributeTemplateItem>lambdaQuery().eq(ProductAttributeTemplateItem::getTemplateId, id));
        saveTemplateItems(id, request.get("attrs"), "ATTR");
        saveTemplateItems(id, request.get("specs"), "SPEC");
    }

    @Override
    @Transactional
    public void deleteTemplate(Long id) {
        productAttributeTemplateItemMapper.delete(Wrappers.<ProductAttributeTemplateItem>lambdaQuery().eq(ProductAttributeTemplateItem::getTemplateId, id));
        productAttributeTemplateMapper.deleteById(id);
    }

    @Override
    public PageResponse<Map<String, Object>> getSpuPage(long pageNum, long pageSize, String keyword, Integer status) {
        Page<ProductSpu> page = productSpuMapper.selectPage(
                Page.of(pageNum, pageSize),
                Wrappers.<ProductSpu>lambdaQuery()
                        .like(StringUtils.hasText(keyword), ProductSpu::getSpuName, keyword)
                        .eq(status != null, ProductSpu::getSaleStatus, status)
                        .orderByDesc(ProductSpu::getId)
        );
        List<Map<String, Object>> records = page.getRecords().stream().map(this::toSpuTableRow).toList();
        return new PageResponse<>(page.getTotal(), page.getCurrent(), page.getSize(), records);
    }

    @Override
    public Map<String, Object> getSpuDetail(Long id) {
        ProductSpu spu = requireSpu(id);
        Map<String, Object> detail = new LinkedHashMap<>(toSpuTableRow(spu));
        detail.put("subTitle", stringValue(spu.getSubtitle()));
        detail.put("description", stringValue(spu.getDetailDesc()));
        detail.put("templateId", spu.getTemplateId());
        detail.put("categoryId", spu.getCategoryId());
        detail.put("skus", listSkuMaps(spu.getId()));
        return detail;
    }

    @Override
    @Transactional
    public void createSpu(Map<String, Object> request) {
        ProductSpu spu = new ProductSpu();
        fillSpu(spu, request);
        spu.setCreatedAt(LocalDateTime.now());
        spu.setUpdatedAt(LocalDateTime.now());
        productSpuMapper.insert(spu);
        saveSkuList(spu.getId(), request.get("skus"), spu.getCoverImage(), spu.getSpuName());
    }

    @Override
    @Transactional
    public void updateSpu(Long id, Map<String, Object> request) {
        ProductSpu spu = requireSpu(id);
        fillSpu(spu, request);
        spu.setUpdatedAt(LocalDateTime.now());
        productSpuMapper.updateById(spu);
        productSkuMapper.delete(Wrappers.<ProductSku>lambdaQuery().eq(ProductSku::getSpuId, id));
        saveSkuList(id, request.get("skus"), spu.getCoverImage(), spu.getSpuName());
    }

    @Override
    public void updateSpuStatus(Long id, Integer status) {
        ProductSpu spu = requireSpu(id);
        spu.setSaleStatus(status);
        spu.setUpdatedAt(LocalDateTime.now());
        productSpuMapper.updateById(spu);
    }

    @Override
    @Transactional
    public void deleteSpu(Long id) {
        productReviewMapper.delete(Wrappers.<ProductReview>lambdaQuery().eq(ProductReview::getSpuId, id));
        productSkuMapper.delete(Wrappers.<ProductSku>lambdaQuery().eq(ProductSku::getSpuId, id));
        productSpuMapper.deleteById(id);
    }

    @Override
    public List<Map<String, Object>> getRecommendProducts() {
        return productSpuMapper.selectList(Wrappers.<ProductSpu>lambdaQuery()
                        .eq(ProductSpu::getSaleStatus, 1)
                        .orderByDesc(ProductSpu::getSalesCount)
                        .last("limit 6"))
                .stream()
                .map(spu -> Map.<String, Object>of(
                        "id", spu.getId(),
                        "name", spu.getSpuName(),
                        "price", firstSalePrice(spu.getId()),
                        "image", stringValue(spu.getCoverImage()),
                        "categoryLabel", resolveCategoryName(spu.getCategoryId())
                ))
                .toList();
    }

    @Override
    public Map<String, Object> getProductDetail(Long id) {
        ProductSpu spu = requireSpu(id);
        List<ProductSku> skuList = productSkuMapper.selectList(
                Wrappers.<ProductSku>lambdaQuery().eq(ProductSku::getSpuId, id).eq(ProductSku::getStatus, 1).orderByAsc(ProductSku::getId)
        );
        List<Map<String, Object>> reviews = productReviewMapper.selectList(
                Wrappers.<ProductReview>lambdaQuery().eq(ProductReview::getSpuId, id).orderByDesc(ProductReview::getId)
        ).stream().map(review -> Map.<String, Object>of(
                "id", review.getId(),
                "user", review.getUsername(),
                "avatar", stringValue(review.getAvatarUrl()),
                "rating", review.getRating(),
                "date", review.getCreatedAt(),
                "content", review.getContent(),
                "images", parseStringList(review.getImagesJson())
        )).toList();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("id", spu.getId());
        result.put("name", spu.getSpuName());
        result.put("price", firstSalePrice(spu.getId()));
        result.put("originalPrice", defaultDecimal(spu.getOriginalPrice()));
        result.put("description", stringValue(spu.getDetailDesc()));
        result.put("images", buildImageList(spu.getCoverImage()));
        result.put("skus", buildSpecGroups(skuList));
        result.put("skuList", skuList.stream().map(this::toSkuSelectionMap).toList());
        result.put("sales", defaultInt(spu.getSalesCount()));
        result.put("stock", skuList.stream().map(ProductSku::getStock).reduce(0, Integer::sum));
        result.put("rating", defaultDecimal(spu.getRating()));
        result.put("reviews", reviews);
        return result;
    }

    @Override
    public Map<String, Object> getSpuSnapshot(Long id) {
        ProductSpu spu = requireSpu(id);
        return Map.of(
                "id", spu.getId(),
                "name", spu.getSpuName(),
                "image", stringValue(spu.getCoverImage()),
                "brandName", stringValue(spu.getBrandName()),
                "categoryName", resolveCategoryName(spu.getCategoryId())
        );
    }

    @Override
    public Map<String, Object> getSkuSnapshot(Long skuId) {
        ProductSku sku = requireSku(skuId);
        return Map.of(
                "id", sku.getId(),
                "spuId", sku.getSpuId(),
                "skuName", sku.getSkuName(),
                "skuCode", sku.getSkuCode(),
                "price", sku.getSalePrice(),
                "stock", sku.getStock(),
                "image", stringValue(sku.getImageUrl()),
                "specs", parseSpecMap(sku.getSpecsJson()),
                "skuText", toSkuText(parseSpecMap(sku.getSpecsJson()))
        );
    }

    private void fillCategory(ProductCategory category, Map<String, Object> request) {
        category.setParentId(longValue(request.getOrDefault("parentId", 0)));
        category.setCategoryName(requiredString(request, "name"));
        category.setSortNo(intValue(request.get("sort"), 0));
        category.setStatus(intValue(request.get("status"), 1));
    }

    private int resolveCategoryLevel(Long parentId) {
        if (parentId == null || parentId == 0L) {
            return 1;
        }
        ProductCategory parent = productCategoryMapper.selectById(parentId);
        return parent == null ? 1 : defaultInt(parent.getLevelNo()) + 1;
    }

    private void fillTemplate(ProductAttributeTemplate template, Map<String, Object> request) {
        template.setTemplateName(requiredString(request, "name"));
        template.setRemark(stringValue(request.get("remark")));
        template.setStatus(intValue(request.get("status"), 1));
    }

    private List<ProductAttributeTemplateItem> listTemplateItems(Long templateId) {
        return productAttributeTemplateItemMapper.selectList(
                Wrappers.<ProductAttributeTemplateItem>lambdaQuery()
                        .eq(ProductAttributeTemplateItem::getTemplateId, templateId)
                        .orderByAsc(ProductAttributeTemplateItem::getSortNo)
                        .orderByAsc(ProductAttributeTemplateItem::getId)
        );
    }

    @SuppressWarnings("unchecked")
    private void saveTemplateItems(Long templateId, Object source, String itemType) {
        if (!(source instanceof Collection<?> collection)) {
            return;
        }
        int sort = 1;
        for (Object entry : collection) {
            if (!(entry instanceof Map<?, ?> raw)) {
                continue;
            }
            Map<String, Object> itemMap = (Map<String, Object>) raw;
            ProductAttributeTemplateItem item = new ProductAttributeTemplateItem();
            item.setTemplateId(templateId);
            item.setItemType(itemType);
            item.setItemName(requiredString(itemMap, "name"));
            item.setItemValues(String.join(",", stringList(itemMap.get("values"))));
            item.setSortNo(intValue(itemMap.get("sortNo"), sort++));
            item.setStatus(intValue(itemMap.get("status"), 1));
            item.setCreatedAt(LocalDateTime.now());
            item.setUpdatedAt(LocalDateTime.now());
            productAttributeTemplateItemMapper.insert(item);
        }
    }

    private void fillSpu(ProductSpu spu, Map<String, Object> request) {
        spu.setSpuName(requiredString(request, "name"));
        spu.setSubtitle(stringValue(request.get("subTitle")));
        spu.setCategoryId(resolveCategoryId(request.get("categoryId")));
        spu.setTemplateId(longOrNull(request.get("templateId")));
        spu.setBrandName(resolveBrandName(request.get("brandId"), request.get("brandName")));
        spu.setCoverImage(stringValue(request.get("pic")));
        spu.setDetailDesc(stringValue(request.get("description")));
        spu.setOriginalPrice(decimalValue(request.get("originalPrice"), BigDecimal.ZERO));
        spu.setSalesCount(intValue(request.get("sales"), 0));
        spu.setRating(decimalValue(request.get("rating"), BigDecimal.valueOf(4.8)));
        spu.setSaleStatus(intValue(request.get("status"), 1));
    }

    @SuppressWarnings("unchecked")
    private void saveSkuList(Long spuId, Object skuSource, String defaultImage, String spuName) {
        if (!(skuSource instanceof Collection<?> collection) || collection.isEmpty()) {
            ProductSku sku = new ProductSku();
            sku.setSpuId(spuId);
            sku.setSkuName(spuName);
            sku.setSkuCode("SKU-" + spuId + "-1");
            sku.setSpecsJson("{}");
            sku.setImageUrl(defaultImage);
            sku.setSalePrice(BigDecimal.ZERO);
            sku.setStock(0);
            sku.setLockStock(0);
            sku.setStatus(1);
            sku.setCreatedAt(LocalDateTime.now());
            sku.setUpdatedAt(LocalDateTime.now());
            productSkuMapper.insert(sku);
            return;
        }
        int index = 1;
        for (Object entry : collection) {
            if (!(entry instanceof Map<?, ?> raw)) {
                continue;
            }
            Map<String, Object> item = (Map<String, Object>) raw;
            ProductSku sku = new ProductSku();
            sku.setSpuId(spuId);
            sku.setSkuName(StringUtils.hasText(stringValue(item.get("skuName"))) ? stringValue(item.get("skuName")) : spuName);
            sku.setSkuCode(StringUtils.hasText(stringValue(item.get("skuCode"))) ? stringValue(item.get("skuCode")) : "SKU-" + spuId + "-" + index++);
            sku.setSpecsJson(writeJson(item.get("specs")));
            sku.setImageUrl(StringUtils.hasText(stringValue(item.get("image"))) ? stringValue(item.get("image")) : defaultImage);
            sku.setSalePrice(decimalValue(item.get("price"), BigDecimal.ZERO));
            sku.setStock(intValue(item.get("stock"), 0));
            sku.setLockStock(0);
            sku.setStatus(intValue(item.get("status"), 1));
            sku.setCreatedAt(LocalDateTime.now());
            sku.setUpdatedAt(LocalDateTime.now());
            productSkuMapper.insert(sku);
        }
    }

    private Map<String, Object> toSpuTableRow(ProductSpu spu) {
        return Map.of(
                "id", spu.getId(),
                "name", spu.getSpuName(),
                "pic", stringValue(spu.getCoverImage()),
                "categoryName", resolveCategoryName(spu.getCategoryId()),
                "brandName", stringValue(spu.getBrandName()),
                "status", spu.getSaleStatus(),
                "sort", 0,
                "skus", listSkuMaps(spu.getId())
        );
    }

    private List<Map<String, Object>> listSkuMaps(Long spuId) {
        return productSkuMapper.selectList(
                Wrappers.<ProductSku>lambdaQuery().eq(ProductSku::getSpuId, spuId).orderByAsc(ProductSku::getId)
        ).stream().map(sku -> Map.<String, Object>of(
                "id", sku.getId(),
                "skuCode", sku.getSkuCode(),
                "skuName", sku.getSkuName(),
                "specs", parseSpecMap(sku.getSpecsJson()),
                "price", sku.getSalePrice(),
                "stock", sku.getStock(),
                "sales", 0,
                "image", stringValue(sku.getImageUrl()),
                "status", sku.getStatus()
        )).toList();
    }

    private ProductCategory requireCategory(Long id) {
        ProductCategory category = productCategoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "分类不存在");
        }
        return category;
    }

    private ProductAttributeTemplate requireTemplate(Long id) {
        ProductAttributeTemplate template = productAttributeTemplateMapper.selectById(id);
        if (template == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "模板不存在");
        }
        return template;
    }

    private ProductSpu requireSpu(Long id) {
        ProductSpu spu = productSpuMapper.selectById(id);
        if (spu == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "商品不存在");
        }
        return spu;
    }

    private ProductSku requireSku(Long skuId) {
        ProductSku sku = productSkuMapper.selectById(skuId);
        if (sku == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "SKU不存在");
        }
        return sku;
    }

    private String resolveCategoryName(Long categoryId) {
        if (categoryId == null) {
            return "";
        }
        List<String> names = new ArrayList<>();
        ProductCategory current = productCategoryMapper.selectById(categoryId);
        while (current != null && current.getId() != null && current.getId() > 0) {
            names.add(current.getCategoryName());
            if (current.getParentId() == null || current.getParentId() == 0L) {
                break;
            }
            current = productCategoryMapper.selectById(current.getParentId());
        }
        java.util.Collections.reverse(names);
        return String.join(" > ", names);
    }

    private BigDecimal firstSalePrice(Long spuId) {
        ProductSku sku = productSkuMapper.selectOne(
                Wrappers.<ProductSku>lambdaQuery().eq(ProductSku::getSpuId, spuId).eq(ProductSku::getStatus, 1).orderByAsc(ProductSku::getId).last("limit 1")
        );
        return sku == null ? BigDecimal.ZERO : defaultDecimal(sku.getSalePrice());
    }

    private List<String> buildImageList(String coverImage) {
        if (!StringUtils.hasText(coverImage)) {
            return List.of();
        }
        return List.of(coverImage, coverImage, coverImage);
    }

    private List<Map<String, Object>> buildSpecGroups(List<ProductSku> skuList) {
        Map<String, Set<String>> groups = new LinkedHashMap<>();
        for (ProductSku sku : skuList) {
            parseSpecMap(sku.getSpecsJson()).forEach((key, value) ->
                    groups.computeIfAbsent(key, item -> new LinkedHashSet<>()).add(String.valueOf(value)));
        }
        return groups.entrySet().stream()
                .map(entry -> Map.<String, Object>of("name", entry.getKey(), "options", new ArrayList<>(entry.getValue())))
                .toList();
    }

    private Map<String, Object> toSkuSelectionMap(ProductSku sku) {
        return Map.of(
                "skuId", sku.getId(),
                "specs", parseSpecMap(sku.getSpecsJson()),
                "price", sku.getSalePrice(),
                "stock", sku.getStock(),
                "image", stringValue(sku.getImageUrl())
        );
    }

    private Map<String, Object> parseSpecMap(String specsJson) {
        if (!StringUtils.hasText(specsJson) || "{}".equals(specsJson)) {
            return Map.of();
        }
        try {
            return objectMapper.readValue(specsJson, new TypeReference<LinkedHashMap<String, Object>>() {
            });
        } catch (Exception exception) {
            return Map.of();
        }
    }

    private List<String> parseStringList(String json) {
        if (!StringUtils.hasText(json)) {
            return List.of();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {
            });
        } catch (Exception exception) {
            return List.of();
        }
    }

    private String toSkuText(Map<String, Object> specMap) {
        return specMap.values().stream().map(String::valueOf).collect(Collectors.joining(", "));
    }

    private List<String> splitValues(String raw) {
        if (!StringUtils.hasText(raw)) {
            return List.of();
        }
        return java.util.Arrays.stream(raw.split(","))
                .map(String::trim)
                .filter(StringUtils::hasText)
                .toList();
    }

    @SuppressWarnings("unchecked")
    private List<String> stringList(Object source) {
        if (!(source instanceof Collection<?> collection)) {
            return List.of();
        }
        return collection.stream().map(String::valueOf).toList();
    }

    @SuppressWarnings("unchecked")
    private String writeJson(Object source) {
        try {
            if (source instanceof Map<?, ?> || source instanceof Collection<?>) {
                return objectMapper.writeValueAsString(source);
            }
            return source == null ? "{}" : objectMapper.writeValueAsString(Map.of("value", source));
        } catch (Exception exception) {
            return "{}";
        }
    }

    @SuppressWarnings("unchecked")
    private Long resolveCategoryId(Object value) {
        if (value instanceof Collection<?> collection && !collection.isEmpty()) {
            Object last = null;
            for (Object item : collection) {
                last = item;
            }
            return longValue(last);
        }
        return longValue(value);
    }

    private String resolveBrandName(Object brandId, Object brandName) {
        if (StringUtils.hasText(stringValue(brandName))) {
            return stringValue(brandName);
        }
        return switch (intValue(brandId, 0)) {
            case 1 -> "苹果";
            case 2 -> "华为";
            default -> "";
        };
    }

    private List<Map<String, Object>> buildCategoryNodes(List<ProductCategory> categories, Long parentId) {
        return categories.stream()
                .filter(category -> Objects.equals(category.getParentId(), parentId))
                .map(category -> {
                    Map<String, Object> node = new LinkedHashMap<>();
                    node.put("id", category.getId());
                    node.put("name", category.getCategoryName());
                    node.put("sort", category.getSortNo());
                    node.put("status", category.getStatus());
                    List<Map<String, Object>> children = buildCategoryNodes(categories, category.getId());
                    if (!children.isEmpty()) {
                        node.put("children", children);
                    }
                    return node;
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private String requiredString(Map<String, Object> request, String key) {
        String value = stringValue(request.get(key));
        if (!StringUtils.hasText(value)) {
            throw new BusinessException(ResultCode.BAD_REQUEST, key + "不能为空");
        }
        return value;
    }

    private String stringValue(Object value) {
        return value == null ? "" : String.valueOf(value).trim();
    }

    private Integer intValue(Object value, Integer defaultValue) {
        if (value == null || !StringUtils.hasText(String.valueOf(value))) {
            return defaultValue;
        }
        return Integer.parseInt(String.valueOf(value));
    }

    private Long longValue(Object value) {
        if (value == null || !StringUtils.hasText(String.valueOf(value))) {
            return 0L;
        }
        return Long.parseLong(String.valueOf(value));
    }

    private Long longOrNull(Object value) {
        Long parsed = longValue(value);
        return parsed == 0L ? null : parsed;
    }

    private BigDecimal decimalValue(Object value, BigDecimal defaultValue) {
        if (value == null || !StringUtils.hasText(String.valueOf(value))) {
            return defaultValue;
        }
        return new BigDecimal(String.valueOf(value));
    }

    private BigDecimal defaultDecimal(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    private Integer defaultInt(Integer value) {
        return value == null ? 0 : value;
    }
}
