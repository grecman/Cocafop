package vwg.skoda.cocafop.repositories;

import java.util.List;

import vwg.skoda.cocafop.entities.Order;
import vwg.skoda.cocafop.objects.OrdersGroup;

public interface OrderRepository {
	Order getOneOrder(Integer id);
	Order getOneOrder(String wk, String modelKey, Integer rok, Integer mesic, String modelKeyVersion, String colour, Integer modelYear, String deliveryProgram, String options);
	List<OrdersGroup> getOrderGroup(String idPlant, String modelKey, Integer rok, Integer mesic);
	
}
