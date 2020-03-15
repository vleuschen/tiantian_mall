package com.imooc.service;

import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {

    /**
     * 根据用户id查询收货地址列表
     * @param userId
     * @return
     */
    List<UserAddress> queryAll(String userId);

    /**
     * 用户新增地址
     * @param addressBO
     */
    void addNewAddress(AddressBO addressBO);

    /**
     * 用户修改地址
     * @param addressBO
     */
    void updateUserAddress(AddressBO addressBO);

    /**
     * 根据用户id和地址id，删除对应的用户地址信息
     * @param userId
     * @param addressId
     */
    void deleteUserAddress(String userId,String addressId);

    /**
     * 将指定的收货地址改为默认地址
     * @param userId
     * @param addressId
     */
    void updateAddressToBeDefault(String userId,String addressId);

    /**
     * 根据用户id和地址id，查询具体的地址对象信息
     * @param userId
     * @param addressId
     * @return
     */
    UserAddress queryUserAddress(String userId,String addressId);
}
