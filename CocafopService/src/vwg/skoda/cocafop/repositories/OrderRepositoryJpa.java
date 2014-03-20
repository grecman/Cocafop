package vwg.skoda.cocafop.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import vwg.skoda.cocafop.entities.Order;
import vwg.skoda.cocafop.objects.OrdersGroup;

public class OrderRepositoryJpa implements OrderRepository {

	static Logger log = Logger.getLogger(OrderRepositoryJpa.class);

	@PersistenceContext(name = "CocafopService")
	private EntityManager entityManager;

	@Override
	public Order getOneOrder(Integer id) {
		log.debug("\t###\t getOneOrder(" + id + ")");
		return entityManager.find(Order.class, id);
	}

	@Override
	public List<OrdersGroup> getOrderGroup(String idPlant, String modelKey, Integer rok, Integer mesic) {
		List<OrdersGroup> out = new ArrayList<OrdersGroup>();
		log.debug("\t###\t getOrderGroup(" + idPlant + ", " + modelKey + ", " + rok + " - " + mesic + ")");
		for (Object o:  entityManager.createQuery(
						"SELECT o.modelKeyVersion, o.colour, o.modelYear, o.deliveryProgram, NVL(o.options,''), count(o) FROM Order o WHERE o.wk=:wk AND o.modelKey=:mk AND o.rok=:r AND o.mesic=:m GROUP BY o.modelKeyVersion, o.colour, o.modelYear, o.deliveryProgram, o.options ORDER BY count(o) DESC").setParameter("wk", idPlant).setParameter("mk", modelKey).setParameter("r", rok).setParameter("m", mesic).getResultList()) {
			Object[] oo = (Object[])o;
			log.debug("\t###\t\t found:"+oo[0] + "\t"+oo[1] + "\t"+oo[2] + "\t"+oo[3] + "\t"+oo[4] + "\t"+oo[5]);
			OrdersGroup og = new OrdersGroup( (String) oo[0],(String) oo[1],(Integer) oo[2], (String) oo[3], (String) oo[4], (Long) oo[5]);
			out.add(og);
		}
		return out;
	}
	
	@Override
	public Order getOneOrder(String wk, String modelKey, Integer rok, Integer mesic, String modelKeyVersion, String colour, Integer modelYear, String deliveryProgram,
			String options) {
		log.debug("\t###\t getOneOrder("+wk+", "+modelKey+", "+rok+"/"+mesic+", "+modelKeyVersion+", "+colour+", "+modelYear+", "+deliveryProgram+", '"+options+"')  ... jen ty co jsou v GZ38T_DES_ROZPAD");
		return entityManager
				.createQuery(
						"SELECT b FROM Order b WHERE b.wk=:wk AND b.modelKey=:mk AND b.rok=:rok AND b.mesic=:mesic AND b.modelKeyVersion=:mkv AND b.colour=:colour AND b.modelYear=:my AND b.deliveryProgram=:dp AND NVL(b.options,'noOptions')=:options AND rownum=1 AND b.kifa IN (SELECT DISTINCT d.kifa FROM DesRozpad d) ORDER BY productionDate DESC",
						Order.class).setParameter("wk", wk).setParameter("mk", modelKey).setParameter("rok", rok).setParameter("mesic", mesic).setParameter("mkv", modelKeyVersion).setParameter("colour", colour).setParameter("my", modelYear).setParameter("dp", deliveryProgram).setParameter("options", options).getSingleResult();
		
	}



}
