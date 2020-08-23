package com.usoft.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.usoft.dtos.AreaDTO;
import com.usoft.model.Area;
import com.usoft.service.AreaServiceInt;

@CrossOrigin(origins = { "*" }, methods = { RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST,
		RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE })
@RestController
@RequestMapping("/usoftapi/areas")
public class ApiAreaController implements ApiAreaControllerInt{
	
	@Autowired
	private AreaServiceInt areaService;

	@Override
	@PostMapping("")
	public String createArea(@RequestBody Area area) {
		try {
			System.out.println(area.getName());
			area.setIsdeleted(BigDecimal.ZERO);
			areaService.createArea(area);
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}

	@Override
	@GetMapping("")
	public Iterable<AreaDTO> findAll() {
		BigDecimal borrado=new BigDecimal("1");
		// TODO Auto-generated method stub
		List<AreaDTO> areasDTO = new ArrayList<AreaDTO>();
		Iterable<Area> areas = areaService.findAll();
		Iterator<Area> iteratorAreas = areas.iterator();
		
		while(iteratorAreas.hasNext()) {
			Area area = iteratorAreas.next();
			AreaDTO areaDTO = new AreaDTO();
			areaDTO.setId(area.getId());
			areaDTO.setDescription(area.getDescription());
			areaDTO.setName(area.getName());
			areaDTO.setIsdeleted(area.getIsdeleted());
			if(area.getIsdeleted()!=borrado) {
				areasDTO.add(areaDTO);
			}
		}
		return areasDTO;
	}
	
	@Override
	@GetMapping("/{id}")
	public Area getArea(@PathVariable(value="id") long id) {
		BigDecimal borrado=new BigDecimal("1");
		if(areaService.findArea(id).getIsdeleted()!=borrado) {
			return areaService.findArea(id);
		}else {
			return null;
		}
	}

	@Override
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public String updateArea(@RequestBody Area area) {
		area.setIsdeleted(new BigDecimal(0));
		try {
			areaService.updateArea(area);
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}

	@DeleteMapping("/{id}")
	@Override
	public String deleteArea(@PathVariable(value="id")long id) {
		try {
			Area borrar=areaService.findArea(id);
			areaService.deleteArea(borrar);
			return "true";
		} catch (Exception e) {
			return "false";
		}
		
	}

}
