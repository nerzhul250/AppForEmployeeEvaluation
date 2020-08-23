package com.usoft.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.io.IOContext;
import com.usoft.dtos.IndicatorDTO;
import com.usoft.model.Indicator;
import com.usoft.model.Indicatorposition;
import com.usoft.service.IndicatorServiceInt;
import com.usoft.service.IndicatorpositionServiceInt;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/usoftapi/indicators")
public class ApiIndicatorController implements ApiIndicatorControllerInt{

	@Autowired
	private IndicatorServiceInt service;
	
	@Autowired
	private IndicatorpositionServiceInt indicatorPositionService;
	
	@Override
	@PostMapping("")
	public String save(@RequestBody Indicator indicator) {
		indicator.setIsdeleted(BigDecimal.ZERO);
		boolean result=service.save(indicator);
		if(result) {
			return "true";
		}else {
			return "false";
		}
	}
	//El / podria presentar problemas, en las pruebas de postman el / fallo
	@Override
	@PatchMapping("")
	public String update(@RequestBody IndicatorDTO indicator) {
		Indicator indicatorUpdate;
		boolean result = false;
		
		try {
			//Este casteo podria no funcionar, si no funciona usar id en la URL
			indicatorUpdate = service.find((int) indicator.getId());
			indicatorUpdate.setName(indicator.getName());
			indicatorUpdate.setDescription(indicator.getDescription());
			
			result = service.update(indicatorUpdate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result) {
			return "true";
		}else {
			return "false";
		}
	}
	
	/**
	 * This method exposes a GET rest service which purpose is to return all the registered indicators
	 */
	@Override
	@GetMapping("")
	public Iterable<IndicatorDTO> findAll() {
		Iterable<Indicator> allIndicators = service.findAll();
		List<IndicatorDTO> allIndicatorsDTO = new ArrayList<IndicatorDTO>();
		Iterator<Indicator> indicatorIterator = allIndicators.iterator();
		
		while(indicatorIterator.hasNext()) {
			Indicator indi = indicatorIterator.next();
			IndicatorDTO indiDTO = new IndicatorDTO();
			indiDTO.setId(indi.getId());
			indiDTO.setName(indi.getName());
			indiDTO.setDescription(indi.getDescription());
			allIndicatorsDTO.add(indiDTO);
		}
		
		return allIndicatorsDTO;
	}

	
	/**
	 * This method exposes a GET rest service which purpose is to return a specified (by its id) indicator
	 */
	@Override
	@GetMapping("/{id}")
	public IndicatorDTO find(@PathVariable(value = "id") Integer id) {
		try {
			Indicator indicator = service.find(id);
			IndicatorDTO indiDTO = new IndicatorDTO();
			indiDTO.setId(indicator.getId());
			indiDTO.setName(indicator.getName());
			indiDTO.setDescription(indicator.getDescription());
			return indiDTO;
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * This method responses the api request by deleteMapping, to remove an indicator
	 */
	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id")  Integer id) {
		try {
			Iterable<Indicatorposition> allIndicatorPo=indicatorPositionService.findAll(); 
			if(allIndicatorPo!=null) {
				Iterator<Indicatorposition> indicatorsPo= allIndicatorPo.iterator();
				while (indicatorsPo.hasNext()) {
					Indicatorposition ip= indicatorsPo.next();
					if(ip.getIndicator()!= null && ip.getIndicator().getId()== id) {
						return ResponseEntity.badRequest().body("Into indicatorPosition");
					}
				}
			}
			service.delete(id);				
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.noContent().build();
	}
}
