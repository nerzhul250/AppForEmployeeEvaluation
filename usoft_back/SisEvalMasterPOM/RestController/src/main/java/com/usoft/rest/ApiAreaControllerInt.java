package com.usoft.rest;

import com.usoft.dtos.AreaDTO;
import com.usoft.model.Area;

public interface ApiAreaControllerInt {

	public String createArea(Area area);
	public Iterable<AreaDTO> findAll();
	public Area getArea(long id);
	public String updateArea(Area area);
	public String deleteArea(long id);
}
