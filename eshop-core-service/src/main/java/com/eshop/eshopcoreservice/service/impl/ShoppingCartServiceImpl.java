package com.eshop.eshopcoreservice.service.impl;

import com.eshop.eshopcoreservice.dto.ProductDto;
import com.eshop.eshopcoreservice.entity.ShoppingCartEntity;
import com.eshop.eshopcoreservice.entity.ShoppingCartItemEntity;
import com.eshop.eshopcoreservice.exception.ServiceException;
import com.eshop.eshopcoreservice.repository.ShoppingCartItemRepository;
import com.eshop.eshopcoreservice.repository.ShoppingCartRepository;
import com.eshop.eshopcoreservice.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Override
    public ShoppingCartEntity saveOrUpdate(ShoppingCartItemEntity cartItem) {


        ShoppingCartEntity existingShoppingCart = shoppingCartRepository.findByCustomerId(8L).orElse(null);

        if (existingShoppingCart != null) {
            ShoppingCartItemEntity item = populateShoppingCartItem(cartItem,existingShoppingCart);

            existingShoppingCart.getLineItems().add(item);

            return shoppingCartRepository.save(existingShoppingCart);
        }

        ShoppingCartEntity newShoppingCart = new ShoppingCartEntity();


        return shoppingCartRepository.save(newShoppingCart);
    }

    @Override
    public ShoppingCartItemEntity populateShoppingCartItem(ShoppingCartItemEntity item,ShoppingCartEntity shoppingCart) throws ServiceException {

        item.setItemPrice(item.getItemPrice());
        item.setProductId(item.getProductId());
        item.setSku(item.getSku());
        item.setQuantity(item.getQuantity());

        return shoppingCartItemRepository.save(item);

    }


    @Override
    public ShoppingCartEntity getShoppingCart(Long customerId) {
        return shoppingCartRepository.findByCustomerId(customerId).orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "Shopping cart is empty!."));
    }

    @Override
    public void removeShoppingCart(Long id) {
        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findById(id).orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "Shopping Cart is does not exists in given id!."));
        shoppingCartEntity.setDeleted(true);
        shoppingCartRepository.save(shoppingCartEntity);
    }
}
