package com.usoft.repo;

import com.usoft.model.Indicatorposition;

public interface IndicatorpositionRepositoryInt {
	public void save(Indicatorposition indicatorposition);
	public void update(Indicatorposition indicatorposition);
	public Iterable<Indicatorposition> findAll();
	public Indicatorposition findById(Integer id);
	public void delete(Integer id);
}
