package com.example.project01.service.shop;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.example.project01.model.shop.dao.CartDAO;
import com.example.project01.model.shop.dto.CartDTO;

@Service
public class CartServiceImpl implements CartService {

	@Inject
	CartDAO cartDao;
	
	@Override
	public List<CartDTO> cartMoney() {
		return cartDao.cartMoney();
	}

	@Override
	public void insert(CartDTO dto) {
		cartDao.insert(dto);
	}

	@Override
	public List<CartDTO> listCart(String userid) {
		return cartDao.listCart(userid);
	}

	@Override
	public void delete(int cart_id) {
		cartDao.delete(cart_id);
	}

	@Override
	public void deleteAll(String userid) {
		cartDao.deleteAll(userid);
	}

	@Override
	public int sumMoney(String userid) {
		return cartDao.sumMoney(userid);
	}

	@Override
	public int countCart(String userid, int product_id) {
		return cartDao.countCart(userid, product_id);
	}

	@Override
	public void updateCart(CartDTO dto) {
		cartDao.updateCart(dto);
	}

	@Override
	public void modifyCart(CartDTO dto) {
		cartDao.modifyCart(dto);
	}

}
