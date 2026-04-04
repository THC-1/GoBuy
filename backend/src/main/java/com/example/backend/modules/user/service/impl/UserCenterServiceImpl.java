package com.example.backend.modules.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.backend.common.api.ResultCode;
import com.example.backend.common.exception.BusinessException;
import com.example.backend.common.util.SecurityUtils;
import com.example.backend.modules.product.service.ProductService;
import com.example.backend.modules.user.entity.MallUser;
import com.example.backend.modules.user.entity.MallUserAddress;
import com.example.backend.modules.user.entity.MallUserFavorite;
import com.example.backend.modules.user.mapper.MallUserAddressMapper;
import com.example.backend.modules.user.mapper.MallUserFavoriteMapper;
import com.example.backend.modules.user.mapper.MallUserMapper;
import com.example.backend.modules.user.service.UserCenterService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UserCenterServiceImpl implements UserCenterService {

    private final MallUserMapper mallUserMapper;
    private final MallUserAddressMapper mallUserAddressMapper;
    private final MallUserFavoriteMapper mallUserFavoriteMapper;
    private final ProductService productService;

    public UserCenterServiceImpl(
            MallUserMapper mallUserMapper,
            MallUserAddressMapper mallUserAddressMapper,
            MallUserFavoriteMapper mallUserFavoriteMapper,
            ProductService productService
    ) {
        this.mallUserMapper = mallUserMapper;
        this.mallUserAddressMapper = mallUserAddressMapper;
        this.mallUserFavoriteMapper = mallUserFavoriteMapper;
        this.productService = productService;
    }

    @Override
    public Map<String, Object> getProfile() {
        MallUser user = currentUser();
        return Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "nickname", user.getNickname(),
                "phone", stringValue(user.getPhone()),
                "email", stringValue(user.getEmail()),
                "gender", stringValue(user.getGender()),
                "bio", stringValue(user.getBio())
        );
    }

    @Override
    public void updateProfile(Map<String, Object> request) {
        MallUser user = currentUser();
        user.setNickname(requiredString(request, "nickname"));
        user.setPhone(stringValue(request.get("phone")));
        user.setEmail(stringValue(request.get("email")));
        user.setGender(StringUtils.hasText(stringValue(request.get("gender"))) ? stringValue(request.get("gender")) : "secret");
        user.setBio(stringValue(request.get("bio")));
        user.setUpdatedAt(LocalDateTime.now());
        mallUserMapper.updateById(user);
    }

    @Override
    public List<Map<String, Object>> listAddresses() {
        return mallUserAddressMapper.selectList(
                Wrappers.<MallUserAddress>lambdaQuery()
                        .eq(MallUserAddress::getUserId, currentUserId())
                        .eq(MallUserAddress::getStatus, 1)
                        .orderByDesc(MallUserAddress::getIsDefault)
                        .orderByDesc(MallUserAddress::getId)
        ).stream().map(address -> Map.<String, Object>of(
                "id", address.getId(),
                "name", address.getReceiverName(),
                "phone", address.getReceiverPhone(),
                "province", address.getProvince(),
                "city", address.getCity(),
                "district", address.getDistrict(),
                "detail", address.getDetailAddress(),
                "isDefault", address.getIsDefault() == 1
        )).toList();
    }

    @Override
    @Transactional
    public void createAddress(Map<String, Object> request) {
        if (booleanValue(request.get("isDefault"))) {
            clearDefaultAddress();
        }
        MallUserAddress address = new MallUserAddress();
        fillAddress(address, request);
        address.setUserId(currentUserId());
        address.setCreatedAt(LocalDateTime.now());
        address.setUpdatedAt(LocalDateTime.now());
        mallUserAddressMapper.insert(address);
    }

    @Override
    @Transactional
    public void updateAddress(Long id, Map<String, Object> request) {
        MallUserAddress address = requireAddress(id);
        if (booleanValue(request.get("isDefault"))) {
            clearDefaultAddress();
        }
        fillAddress(address, request);
        address.setUpdatedAt(LocalDateTime.now());
        mallUserAddressMapper.updateById(address);
    }

    @Override
    public void deleteAddress(Long id) {
        mallUserAddressMapper.delete(Wrappers.<MallUserAddress>lambdaQuery().eq(MallUserAddress::getId, id).eq(MallUserAddress::getUserId, currentUserId()));
    }

    @Override
    @Transactional
    public void setDefaultAddress(Long id) {
        MallUserAddress address = requireAddress(id);
        clearDefaultAddress();
        address.setIsDefault(1);
        address.setUpdatedAt(LocalDateTime.now());
        mallUserAddressMapper.updateById(address);
    }

    @Override
    public List<Map<String, Object>> listFavorites() {
        return mallUserFavoriteMapper.selectList(
                Wrappers.<MallUserFavorite>lambdaQuery()
                        .eq(MallUserFavorite::getUserId, currentUserId())
                        .orderByDesc(MallUserFavorite::getId)
        ).stream().map(favorite -> {
            Map<String, Object> product = productService.getSpuSnapshot(favorite.getSpuId());
            return Map.<String, Object>of(
                    "id", favorite.getId(),
                    "productId", favorite.getSpuId(),
                    "name", product.get("name"),
                    "price", "¥ " + productService.getProductDetail(favorite.getSpuId()).get("price"),
                    "image", product.get("image"),
                    "status", "在售"
            );
        }).toList();
    }

    @Override
    public void addFavorite(Map<String, Object> request) {
        Long spuId = longValue(request.get("productId"));
        if (spuId == 0L) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "productId不能为空");
        }
        long count = mallUserFavoriteMapper.selectCount(
                Wrappers.<MallUserFavorite>lambdaQuery()
                        .eq(MallUserFavorite::getUserId, currentUserId())
                        .eq(MallUserFavorite::getSpuId, spuId)
        );
        if (count > 0) {
            return;
        }
        MallUserFavorite favorite = new MallUserFavorite();
        favorite.setUserId(currentUserId());
        favorite.setSpuId(spuId);
        favorite.setCreatedAt(LocalDateTime.now());
        mallUserFavoriteMapper.insert(favorite);
    }

    @Override
    public void deleteFavorite(Long spuId) {
        mallUserFavoriteMapper.delete(
                Wrappers.<MallUserFavorite>lambdaQuery()
                        .eq(MallUserFavorite::getUserId, currentUserId())
                        .eq(MallUserFavorite::getSpuId, spuId)
        );
    }

    private void fillAddress(MallUserAddress address, Map<String, Object> request) {
        address.setReceiverName(requiredString(request, "name"));
        address.setReceiverPhone(requiredString(request, "phone"));
        address.setProvince(requiredString(request, "province"));
        address.setCity(requiredString(request, "city"));
        address.setDistrict(requiredString(request, "district"));
        address.setDetailAddress(requiredString(request, "detail"));
        address.setIsDefault(booleanValue(request.get("isDefault")) ? 1 : 0);
        address.setStatus(1);
    }

    private void clearDefaultAddress() {
        List<MallUserAddress> addresses = mallUserAddressMapper.selectList(
                Wrappers.<MallUserAddress>lambdaQuery().eq(MallUserAddress::getUserId, currentUserId()).eq(MallUserAddress::getIsDefault, 1)
        );
        for (MallUserAddress address : addresses) {
            address.setIsDefault(0);
            address.setUpdatedAt(LocalDateTime.now());
            mallUserAddressMapper.updateById(address);
        }
    }

    private MallUser currentUser() {
        MallUser user = mallUserMapper.selectById(currentUserId());
        if (user == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, ResultCode.UNAUTHORIZED.getMessage());
        }
        return user;
    }

    private Long currentUserId() {
        return SecurityUtils.getLoginUser().getUserId();
    }

    private MallUserAddress requireAddress(Long id) {
        MallUserAddress address = mallUserAddressMapper.selectOne(
                Wrappers.<MallUserAddress>lambdaQuery().eq(MallUserAddress::getId, id).eq(MallUserAddress::getUserId, currentUserId()).last("limit 1")
        );
        if (address == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "地址不存在");
        }
        return address;
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

    private Long longValue(Object value) {
        if (value == null || !StringUtils.hasText(String.valueOf(value))) {
            return 0L;
        }
        return Long.parseLong(String.valueOf(value));
    }

    private boolean booleanValue(Object value) {
        return value != null && ("true".equalsIgnoreCase(String.valueOf(value)) || "1".equals(String.valueOf(value)));
    }
}
