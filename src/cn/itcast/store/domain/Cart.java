package cn.itcast.store.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {
	private double total = 0;//总计,积分
	Map<String, CartItem> map = new HashMap<>();//个数不确定的购物项pid Product
	
	//清空购物车
	public void clearCart() {
		map.clear();
	}
	
	//移除购物项
	public void removeCartItem(String pid) {
		map.remove(pid);
	}
	
	//添加到购物车
	public void addToCart(CartItem item) {
		//得到pid
		String id = item.getProduct().getPid();
		//判断购物车之前是否加了这个商品
		if(map.containsKey(id)) {
			CartItem oldItem = map.get(id);
			oldItem.setNum(oldItem.getNum() + item.getNum());
		} else {
			map.put(id, item);
		}
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getTotal() {
		total = 0;
		//计算获得
		Collection<CartItem> valuse = map.values();
		
		for (CartItem cartItem : valuse) {
			total += cartItem.getSubTotal();
		}
		return total;
	}

	public Map<String, CartItem> getMap() {
		return map;
	}

	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}
	
	public Collection getCartItems(){
		return map.values();
	}
}
