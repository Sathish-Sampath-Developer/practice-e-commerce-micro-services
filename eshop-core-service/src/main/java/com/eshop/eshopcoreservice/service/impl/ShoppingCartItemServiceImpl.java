package com.eshop.eshopcoreservice.service.impl;

import com.eshop.eshopcoreservice.entity.ShoppingCartItemEntity;
import com.eshop.eshopcoreservice.exception.ServiceException;
import com.eshop.eshopcoreservice.repository.ShoppingCartItemRepository;
import com.eshop.eshopcoreservice.service.ShoppingCartItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Override
    public ShoppingCartItemEntity addCartItem(ShoppingCartItemEntity shoppingCartItem) {
        return shoppingCartItemRepository.save(shoppingCartItem);
    }

    @Override
    public List<ShoppingCartItemEntity> getAllCartItemByIds(List<Long> ids) {
        return shoppingCartItemRepository.findAllByIdIn(ids).orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "Shopping item is empty"));
    }

    @Override
    public ShoppingCartItemEntity updateCartItem(Long id, ShoppingCartItemEntity shoppingCartItem) {
        ShoppingCartItemEntity shoppingCartItemToUpdate = shoppingCartItemRepository.findById(id).orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "Shopping Cart item is does not exists in given id!."));

        shoppingCartItemToUpdate.setItemPrice(shoppingCartItem.getItemPrice());
        shoppingCartItemToUpdate.setSku(shoppingCartItem.getSku());
        shoppingCartItemToUpdate.setQuantity(shoppingCartItem.getQuantity());
        shoppingCartItemToUpdate.setProductId(shoppingCartItem.getProductId());

        return shoppingCartItemRepository.save(shoppingCartItemToUpdate);
    }

    @Override
    public void removeCartItem(Long id) {
        ShoppingCartItemEntity shoppingCartItemEntity = shoppingCartItemRepository.findById(id).orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "Shopping Cart item is does not exists in given id!."));
        shoppingCartItemEntity.setDeleted(true);
        shoppingCartItemRepository.save(shoppingCartItemEntity);
    }
}
