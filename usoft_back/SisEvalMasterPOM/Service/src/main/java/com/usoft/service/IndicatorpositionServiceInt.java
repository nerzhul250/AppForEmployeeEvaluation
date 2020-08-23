package com.usoft.service;

import com.usoft.model.Indicatorposition;

public interface IndicatorpositionServiceInt {
	public void save(Indicatorposition indicatorposition);
	public void update(Indicatorposition indicatorposition);
	public Iterable<Indicatorposition> findAll();
	public Indicatorposition findById(Integer id);
	public void delete(Integer id);

}
