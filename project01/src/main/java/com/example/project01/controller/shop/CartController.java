package com.example.project01.controller.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.project01.model.shop.dto.CartDTO;
import com.example.project01.service.shop.CartService;

@Controller
@RequestMapping("/shop/cart/*")
public class CartController {

	@Inject
	CartService cartService;
	
	@RequestMapping("delete.do")
	public String delete(int cart_id) {
		cartService.delete(cart_id);
		return "redirect:/shop/cart/list.do";
	}
	
	@RequestMapping("deleteAll.do")
	public String deleteAll(HttpSession session) {
		String userid=(String)session.getAttribute("userid");
		if(userid!=null) {
			cartService.deleteAll(userid);
		}
		return "redirect:/shop/cart/list.do";
	}
	
	@RequestMapping("update.do")
	public String update(int[] amount, int[] cart_id, HttpSession session) {
		String userid=(String)session.getAttribute("userid");
		for(int i=0; i<cart_id.length; i++) {
			if(amount[i] == 0 ) {
				cartService.delete(cart_id[i]);
			}else {
				CartDTO dto=new CartDTO();
				dto.setUserid(userid);
				dto.setCart_id(cart_id[i]);
				dto.setAmount(amount[i]);
				cartService.modifyCart(dto);
			}
		}
		return "redirect:/shop/cart/list.do";
	}
	
	@RequestMapping("list.do")
	public ModelAndView list(HttpSession session, ModelAndView mav) {
		Map<String,Object> map=new HashMap<>();
		String userid=(String)session.getAttribute("userid");
		if(userid!=null) {
			List<CartDTO> list=cartService.listCart(userid);
			int sumMoney=cartService.sumMoney(userid);
			int fee=sumMoney >= 30000 ? 0 : 2500; //3만원 이상이면 배송료 면제
			map.put("sumMoney", sumMoney);
			map.put("fee", fee);
			map.put("sum", sumMoney+fee);
			map.put("list", list);
			map.put("count", list.size());
			mav.setViewName("shop/cart_list");
			mav.addObject("map", map);
			return mav;
		}else {
			return new ModelAndView("member/login", "", null);
		}
	}
	
	@RequestMapping("insert.do")
	public String insert(CartDTO dto, HttpSession session) {
		String userid=(String)session.getAttribute("userid");
		dto.setUserid(userid);
		cartService.insert(dto);
		return "redirect:/shop/cart/list.do";
	}
}
