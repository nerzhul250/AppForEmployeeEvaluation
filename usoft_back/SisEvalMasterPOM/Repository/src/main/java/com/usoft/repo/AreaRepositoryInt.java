package com.usoft.repo;

import com.usoft.model.Area;

public interface AreaRepositoryInt {
	void save(Area area);

	Area update(Area area);

	void delete(Area area);

	Area findById(long id);
	
	public Iterable<Area> findAll();
}
