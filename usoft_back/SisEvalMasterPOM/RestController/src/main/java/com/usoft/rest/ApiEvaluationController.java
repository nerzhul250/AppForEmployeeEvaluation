package com.usoft.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usoft.dtos.EvaluationDTO;
import com.usoft.dtos.EvaluationcriteriaDTO;
import com.usoft.dtos.UserDTO;
import com.usoft.model.Evaluation;
import com.usoft.model.Evaluationcriteria;
import com.usoft.service.EvaluationServiceInt;

@CrossOrigin
@RestController
@RequestMapping("/usoftapi/evaluations")
public class ApiEvaluationController implements ApiEvaluationControllerInt {

	@Autowired
	private EvaluationServiceInt evaluationService;

	/**
	 * GET Rest service that returns all registered evaluations.
	 */
	@Override
	@GetMapping("")
	public Iterable<Evaluation> findAllEvaluations() {
		return evaluationService.findAllEvaluations();
	}

	/**
	 * GET Rest service that returns a HashMap of a User's evaluations, in which the key value is the id<br>
	 * of the period they were made in.
	 */
	@Override
	@GetMapping("/user/{id}")
	public HashMap<String,EvaluationDTO> getEvaluationsOfUser(@PathVariable Integer id) {
		HashMap<String,EvaluationDTO> map = new HashMap<String,EvaluationDTO>();
		Iterable<Evaluation> allEvaluations = evaluationService.getEvaluationsOfUser((int) id);
		for (Evaluation evaluation : allEvaluations) {
			// Initialize DTOs
			EvaluationDTO evaluationDTO = new EvaluationDTO();
			UserDTO employeeDTO = new UserDTO();
			UserDTO evaluatorDTO = new UserDTO();
			
			// Prepare Employee DTO
			employeeDTO.setId(evaluation.getUsuario2().getId());
			employeeDTO.setFirstName(evaluation.getUsuario2().getFirstName());
			employeeDTO.setLastName(evaluation.getUsuario2().getLastName());
			employeeDTO.setEmail(evaluation.getUsuario2().getEmail());
			
			// Prepare Evaluator DTO
			evaluatorDTO.setId(evaluation.getUsuario1().getId());
			evaluatorDTO.setFirstName(evaluation.getUsuario1().getFirstName());
			evaluatorDTO.setLastName(evaluation.getUsuario1().getLastName());
			evaluatorDTO.setEmail(evaluation.getUsuario1().getEmail());
			
			// Prepare EvaluationCriteria DTOs
			ArrayList<EvaluationcriteriaDTO> allDTOEvaluationCriteria = new ArrayList<EvaluationcriteriaDTO>();			
			for(Evaluationcriteria ec : evaluation.getEvaluationcriterias()) {
				EvaluationcriteriaDTO ecDTO = new EvaluationcriteriaDTO();
				ecDTO.setComment(ec.getComentario());
				ecDTO.setCriteria_id((int) ec.getCriteriaId());
				ecDTO.setValue(ec.getValor().intValue());
				allDTOEvaluationCriteria.add(ecDTO);
			}
			
			// Set own variables.
			evaluationDTO.setComment(evaluation.getComentario());
			evaluationDTO.setCareerPlan((int)evaluation.getCareerplan().getId());
			evaluationDTO.setPeriod((int)evaluation.getPeriod().getId());
			evaluationDTO.setEvaluator((int)evaluatorDTO.getId());
			evaluationDTO.setEmployee((int)employeeDTO.getId());
			evaluationDTO.setCriteria(allDTOEvaluationCriteria);
			
			// Put evaluationDTO into hashmap
			map.put(evaluation.getPeriod().getId()+"", evaluationDTO);
		}
		return map;
	}

	/**
	 * POST Rest service that registers an Evaluation by receiving data from an EvaluationDTO object.
	 */
	@Override
	@PostMapping("")
	public String createEvaluation(@RequestBody EvaluationDTO evaluation) {
		System.out.println(evaluation.toString());
		Evaluation e = new Evaluation();
		e.setIsdeleted(BigDecimal.ZERO);
		e.setComentario(evaluation.getComment());
		int careerPlan = evaluation.getCareerPlan();
		int period = evaluation.getPeriod();
		int evaluator = evaluation.getEvaluator();
		int employee= evaluation.getEmployee();
		ArrayList<EvaluationcriteriaDTO> criteria = evaluation.getCriteria();
		ArrayList<String[]> criteriaData = new ArrayList<String[]>();
		for (EvaluationcriteriaDTO evaluationcriteriaDTO : criteria) {
			String[] data = new String[3];
			data[0] = evaluationcriteriaDTO.getCriteria_id() + "";
			data[1] = evaluationcriteriaDTO.getValue() + "";
			data[2] = evaluationcriteriaDTO.getComment() + "";
			criteriaData.add(data);
		}
		try {
			Evaluation ee = evaluationService.createEvaluation(e, careerPlan, period, evaluator, employee, criteriaData);
			System.out.println("After service method ends, the id found is: " + ee.getId());
			return "true";
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
			return "false";
		}
	}

	@Override
	public HashMap<String,EvaluationDTO> getEvaluationsByPeriod(long period) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * GET Rest service that returns a HashMap HashMaps of user evaluations, outer map key value is the <br>
	 * id of the evaluated user, and the inner map key is the ID value of the period the evaluation was made in.
	 */
//	@Override
//	@GetMapping("/careerplan/{careerplan}")
//	public HashMap<String,ArrayList<EvaluationDTO>> getEvaluationsByCareerPlan(@PathVariable long careerplan) {
//		System.out.println("Entering the method");
//		HashMap<String,ArrayList<EvaluationDTO>> map = new HashMap<String,ArrayList<EvaluationDTO>>();
//		Iterable<Evaluation> allEvaluations = evaluationService.getEvaluationsByCareerPlan((int) careerplan);
//		System.out.println(allEvaluations.toString());
//		for (Evaluation evaluation : allEvaluations) {
//			System.out.println(evaluation.getUsuario1().getFirstName());
//			// Initialize DTOs
//			EvaluationDTO evaluationDTO = new EvaluationDTO();
//			UserDTO employeeDTO = new UserDTO();
//			UserDTO evaluatorDTO = new UserDTO();
//			
//			// Prepare Employee DTO
//			employeeDTO.setId(evaluation.getUsuario2().getId());
//			employeeDTO.setFirstName(evaluation.getUsuario2().getFirstName());
//			employeeDTO.setLastName(evaluation.getUsuario2().getLastName());
//			employeeDTO.setEmail(evaluation.getUsuario2().getEmail());
//			
//			// Prepare Evaluator DTO
//			evaluatorDTO.setId(evaluation.getUsuario1().getId());
//			evaluatorDTO.setFirstName(evaluation.getUsuario1().getFirstName());
//			evaluatorDTO.setLastName(evaluation.getUsuario1().getLastName());
//			evaluatorDTO.setEmail(evaluation.getUsuario1().getEmail());
//			
//			// Prepare EvaluationCriteria DTOs
//			ArrayList<EvaluationcriteriaDTO> allDTOEvaluationCriteria = new ArrayList<EvaluationcriteriaDTO>();			
//			for(Evaluationcriteria ec : evaluation.getEvaluationcriterias()) {
//				EvaluationcriteriaDTO ecDTO = new EvaluationcriteriaDTO();
//				ecDTO.setComment(ec.getComentario());
//				ecDTO.setCriteria_id((int) ec.getCriteriaId());
//				ecDTO.setValue(ec.getValor().intValue());
//				allDTOEvaluationCriteria.add(ecDTO);
//			}
//			
//			
//			// Set own variables.
//			evaluationDTO.setComment(evaluation.getComentario());
//			evaluationDTO.setPeriod((int)evaluation.getPeriod().getId());
//			evaluationDTO.setCareerPlan((int)evaluation.getCareerplan().getId());
//			evaluationDTO.setEvaluator((int)evaluatorDTO.getId());
//			evaluationDTO.setEmployee((int)employeeDTO.getId());
//			evaluationDTO.setCriteria(allDTOEvaluationCriteria);
//			
//			// Put evaluationDTO into hashmap
//			ArrayList<EvaluationDTO> currentList = null;
//			if(map.containsKey(evaluation.getUsuario2().getId()+"")){
//				currentList = map.get(evaluation.getUsuario2().getId()+"");
//			} else {
//				currentList = new ArrayList<EvaluationDTO>();
//			}
//			currentList.add(evaluationDTO);
//			System.out.println("Associated: "+currentList.toString());
//			System.out.println();
//			map.put(evaluation.getUsuario2().getId()+"", currentList);
//		}
//		return map;
//	}
	
	@Override
	@GetMapping("/careerplan/{careerplan}")
	public HashMap<String,HashMap<String,EvaluationDTO>> getEvaluationsByCareerPlan(@PathVariable long careerplan) {
		HashMap<String,HashMap<String,EvaluationDTO>> map = new HashMap<String,HashMap<String,EvaluationDTO>>();;
		Iterable<Evaluation> allEvaluations = evaluationService.getEvaluationsByCareerPlan((int) careerplan);
		for (Evaluation evaluation : allEvaluations) {
			HashMap<String,EvaluationDTO> innerMap;
			if(map.containsKey(evaluation.getUsuario2().getId()+"")) {
				innerMap = map.get(evaluation.getUsuario2().getId()+"");
			} else {
				innerMap = new HashMap<String,EvaluationDTO>();
			}
			
			
			
			System.out.println(evaluation.getUsuario1().getFirstName());
			// Initialize DTOs
			EvaluationDTO evaluationDTO = new EvaluationDTO();
			UserDTO employeeDTO = new UserDTO();
			UserDTO evaluatorDTO = new UserDTO();
			
			// Prepare Employee DTO
			employeeDTO.setId(evaluation.getUsuario2().getId());
			employeeDTO.setFirstName(evaluation.getUsuario2().getFirstName());
			employeeDTO.setLastName(evaluation.getUsuario2().getLastName());
			employeeDTO.setEmail(evaluation.getUsuario2().getEmail());
			
			// Prepare Evaluator DTO
			evaluatorDTO.setId(evaluation.getUsuario1().getId());
			evaluatorDTO.setFirstName(evaluation.getUsuario1().getFirstName());
			evaluatorDTO.setLastName(evaluation.getUsuario1().getLastName());
			evaluatorDTO.setEmail(evaluation.getUsuario1().getEmail());
			
			// Prepare EvaluationCriteria DTOs
			ArrayList<EvaluationcriteriaDTO> allDTOEvaluationCriteria = new ArrayList<EvaluationcriteriaDTO>();			
			for(Evaluationcriteria ec : evaluation.getEvaluationcriterias()) {
				EvaluationcriteriaDTO ecDTO = new EvaluationcriteriaDTO();
				ecDTO.setComment(ec.getComentario());
				ecDTO.setCriteria_id((int) ec.getCriteriaId());
				ecDTO.setValue(ec.getValor().intValue());
				allDTOEvaluationCriteria.add(ecDTO);
			}
			
			
			// Set own variables.
			evaluationDTO.setComment(evaluation.getComentario());
			evaluationDTO.setPeriod((int)evaluation.getPeriod().getId());
			evaluationDTO.setCareerPlan((int)evaluation.getCareerplan().getId());
			evaluationDTO.setEvaluator((int)evaluatorDTO.getId());
			evaluationDTO.setEmployee((int)employeeDTO.getId());
			evaluationDTO.setCriteria(allDTOEvaluationCriteria);
			
			// Put evaluationDTO into hashmap
//			ArrayList<EvaluationDTO> currentList = null;
//			if(map.containsKey(evaluation.getUsuario2().getId()+"")){
//				currentList = map.get(evaluation.getUsuario2().getId()+"");
//			} else {
//				currentList = new ArrayList<EvaluationDTO>();
//			}
//			currentList.add(evaluationDTO);
//			System.out.println("Associated: "+currentList.toString());
//			System.out.println();
//			map.put(evaluation.getUsuario2().getId()+"", currentList);
		
			
			innerMap.put(evaluationDTO.getPeriod()+"", evaluationDTO);
			map.put(evaluationDTO.getEmployee()+"", innerMap);
		}
		return map;
	}

	
	

	
	
	
	
	
	
	
	
	
	

	
	@Override
//	@GetMapping("/careerplan/{id}")
	public Iterable<Evaluation> getEvaluationsByPeriodAndCareerPlan(@PathVariable long period, @PathVariable long careerplan) {
		Map<String,EvaluationDTO> map = new HashMap<String,EvaluationDTO>();
		
		Iterable<Evaluation> allEvaluations = evaluationService.getEvaluationsByPeriodAndCareerPlan((int) period, (int) careerplan);
		ArrayList<EvaluationDTO> allDTOEvaluations = new ArrayList<EvaluationDTO>();
		
		for (Evaluation evaluation : allEvaluations) {
			// Make a new DTO
			EvaluationDTO eDTO = new EvaluationDTO();
			
			// Set own variables.
			eDTO.setComment(evaluation.getComentario());
			
			// Set other single DTOs (excluding Careerplan)
//			eDTO.setEmployee(employee);
//			eDTO.setEvaluator(evaluator);
			
			UserDTO employeeDTO = new UserDTO();
			UserDTO evaluatorDTO = new UserDTO();
			
			employeeDTO.setFirstName(evaluation.getUsuario1().getFirstName());
			employeeDTO.setLastName(evaluation.getUsuario1().getLastName());
			
			
			eDTO.setPeriod((int) evaluation.getPeriod().getId());
			
			// Set other multiple DTOs
			ArrayList<EvaluationcriteriaDTO> allDTOEvaluationCriteria = new ArrayList<EvaluationcriteriaDTO>();			
			for(Evaluationcriteria ec : evaluation.getEvaluationcriterias()) {
				EvaluationcriteriaDTO ecDTO = new EvaluationcriteriaDTO();
				ecDTO.setComment(ec.getComentario());
				ecDTO.setCriteria_id((int) ec.getCriteriaId());
				ecDTO.setValue(ec.getValor().intValue());
			}
			eDTO.setCriteria(allDTOEvaluationCriteria);
			
			// Save 
			map.put(evaluation.getPeriod().getName(), eDTO);
			
			
		}
		
//		return map;
		
		return evaluationService.getEvaluationsByPeriodAndCareerPlan((int) period, (int) careerplan);
	}

	@Override
	public ResponseEntity<?> deleteEvaluation(Integer id) {
		try {
			evaluationService.deleteEvaluation(id);
		} catch (Exception e) {
			ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.noContent().build();
	}
}
