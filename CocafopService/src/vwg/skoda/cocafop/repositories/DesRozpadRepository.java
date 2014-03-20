package vwg.skoda.cocafop.repositories;

import java.util.List;

import vwg.skoda.cocafop.entities.DesRozpad;

public interface DesRozpadRepository {

	List<DesRozpad> getDesRozpad(String kifa);

}
