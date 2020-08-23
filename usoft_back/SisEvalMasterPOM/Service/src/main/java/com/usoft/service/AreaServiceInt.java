package com.usoft.service;

import com.usoft.model.Area;

public interface AreaServiceInt {
	public void createArea(Area area);

	public Area updateArea(Area area);

	public void deleteArea(Area area);

	public Area findArea(long id);

	public Iterable<Area> findAll();
}
