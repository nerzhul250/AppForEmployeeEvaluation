package com.usoft.rest;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.usoft.dtos.IndicatorDTO;
import com.usoft.dtos.PositionDTO;
import com.usoft.model.Indicator;
import com.usoft.model.Position;
import com.usoft.service.PositionServiceInt;

@CrossOrigin(origins = {"*"}, methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE})
@RestController
@RequestMapping("/usoftapi/positions")
public class ApiPositionController implements ApiPositionControllerInt{

	@Autowired
	private PositionServiceInt positionService;

	@Override
	@PostMapping("")
	public String createPosition(@RequestBody Position position) {
		try {
			positionService.createPosition(position);
			return "true";
		}
		catch (Exception e) {
			return "false";
		}
		
	}

	@Override
	@GetMapping("")
	public Iterable<Position> findAllPositions() {
		return positionService.findAll();
	}

	@Override
	@GetMapping("/{id}")
	public Position findPosition(@PathVariable(value="id") Long id) {
		return positionService.findPosition(id);
	}

	@Override
	@DeleteMapping("/{id}")
	public String deletePosition(@PathVariable Long id) {
		try {
			positionService.deletePosition(positionService.findPosition(id));
			return "true";
		} catch (Exception e) {
			// TODO: handle exception
			return "false";
		}
		
	}

	@Override
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public String updatePosition(@RequestBody Position position) {
		try {
			positionService.updatePosition(position);
			return "true";
		} catch (Exception e) {
			// TODO: handle exception
			return "false";
		}

		//		Indicator indicatorUpdate;
//		boolean result = false;
//		
//		try {
//			//Este casteo podria no funcionar, si no funciona usar id en la URL
//			indicatorUpdate = service.find((int) indicator.getId());
//			indicatorUpdate.setName(indicator.getName());
//			indicatorUpdate.setDescription(indicator.getDescription());
//			
//			result = service.update(indicatorUpdate);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		if(result) {
//			return "true";
//		}else {
//			return "false";
//		}
	}
	

}
