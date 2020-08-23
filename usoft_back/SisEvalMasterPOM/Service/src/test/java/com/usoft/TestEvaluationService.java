package com.usoft;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.usoft.model.Careerplan;
import com.usoft.model.Criteria;
import com.usoft.model.Evaluation;
import com.usoft.model.Period;
import com.usoft.model.Usuario;
import com.usoft.repo.CareerPlanRepository;
import com.usoft.repo.CriteriaRepository;
import com.usoft.repo.EvaluationCriteriaRepository;
import com.usoft.repo.EvaluationRepository;
import com.usoft.repo.PeriodRepository;
import com.usoft.repo.UserRepository;
import com.usoft.service.EvaluationService;

public class TestEvaluationService {

	@Mock
	private EvaluationRepository repo;

	@Mock
	private UserRepository userRepo;

	@Mock
	private CareerPlanRepository careerPlanRepo;

	@Mock
	private PeriodRepository periodRepo;

	@Mock
	private EvaluationCriteriaRepository evaluationCriteriaRepo;

	@Mock
	private CriteriaRepository criteriaRepo;

	@InjectMocks
	private EvaluationService evaluationService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

//	@Test
	public void createEvaluation() {
		Evaluation evaluation = new Evaluation();
		evaluation.setComentario("");
//		Usuario emple = userRepo.find(employee);
		Usuario emple = new Usuario();
		emple.setId(1);
//		Usuario evalu = userRepo.find(evaluator);
		Usuario evalu = new Usuario();
		evalu.setId(2);
//		Period perio = periodRepo.findPeriod(period);
		Period perio = new Period();
		perio.setId(1);
//		Careerplan plan = careerPlanRepo.findCareerPlan(careerPlan);
		Careerplan plan = new Careerplan();
		plan.setId(1);
		ArrayList<String[]> criteriaData = new ArrayList<String[]>();
		System.out.println("Testing failure.");
		assertNull(evaluationService.createEvaluation(evaluation, 1, 1, 2, 1, criteriaData));
		String[] data = {"1","10","a."};
		String[] moreData = {"2", "8", "b."};
		criteriaData.add(data);
		criteriaData.add(moreData);
		System.out.println("Testing success");
//		evaluation.setCareerplan(plan);
//		evaluation.setPeriod(perio);
//		evaluation.setUsuario1(evalu);
//		evaluation.setUsuario2(emple);
//		when(evaluationService.createEvaluation(evaluation, 1, 1, 2, 1, criteriaData)).thenReturn(evaluation);
		when(userRepo.find(1)).thenReturn(emple);
		when(userRepo.find(2)).thenReturn(evalu);
		when(periodRepo.find(1)).thenReturn(perio);
		when(careerPlanRepo.findById(1)).thenReturn(plan);
		Criteria cA = new Criteria();
		cA.setId(1);
		Criteria cB = new Criteria();
		cB.setId(2);
		when(criteriaRepo.find(1)).thenReturn(cA);
		when(criteriaRepo.find(2)).thenReturn(cB);
		Evaluation c = evaluationService.createEvaluation(evaluation, 1, 1, 2, 1, criteriaData);
//		assertNotNull(evaluationService.createEvaluation(evaluation, 1, 1, 2, 1, criteriaData));
	}

	@Test
	public void findAllEvaluations() {
		Evaluation evaluation = new Evaluation();
		evaluation.setId(13);
		ArrayList<Evaluation> test = new ArrayList<Evaluation>();
		test.add(evaluation);
		when(repo.findAll()).thenReturn(test);
		Iterable<Evaluation> allEvaluations = evaluationService.findAllEvaluations();
		assertTrue(allEvaluations == test);
	}

	@Test
	public void findEvaluation() {
		Evaluation evaluation = new Evaluation();
		evaluation.setId(13);
		when(repo.find(13)).thenReturn(evaluation);
		try {
			Evaluation e1 = evaluationService.findEvaluation(13);			
			assertTrue(e1==evaluation);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		
		Evaluation evaluation2 = new Evaluation();
		evaluation2.setId(132);
		when(repo.find(12)).thenThrow(new RuntimeException("No evaluation registered with id: 12"));
		try {
			Evaluation e2 = evaluationService.findEvaluation(12);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
		
		
	}

	@Test
	public void deleteEvaluation() {
		Evaluation evaluation = new Evaluation();
		evaluation.setId(13);
		evaluation.setIsdeleted(BigDecimal.ZERO);
		try {
			when(repo.find(13)).thenReturn(evaluation);
			evaluationService.deleteEvaluation(13);
			int removeState = evaluationService.findEvaluation(13).getIsdeleted().intValue();
			assertTrue(removeState==1);
		} catch (Exception e) {
			fail();
		}
		
		Evaluation evaluation2 = new Evaluation();
		evaluation2.setId(12);
		evaluation2.setIsdeleted(BigDecimal.ONE);
		try {
			when(repo.find(12)).thenReturn(evaluation2);
			evaluationService.deleteEvaluation(12);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}

	}
	

//	
//	@Test
//	public void createEvaluationSuccess() {
//		Usuario user=new Usuario();
//		user.setArea(new Area());
//		user.setBirthday(Date.valueOf("1999-6-2"));
//		user.setEmail("sada@hotmail.com");
//		user.setFirstName("sara");
//		user.setLastName("ortis");
//		user.setPhone("3116953284");
//		user.setState(new State());
//		
//		
//		Evaluation evaluation = new Evaluation();
//		evaluation.setCareerplan(new Careerplan());
//		evaluation.setComentario("");
//		evaluation.setPeriod(new Period());
//		evaluation.setUsuario1(user);
//		evaluation.setUsuario2(new Usuario());
//		
//		assertTrue(evaluationService.createEvaluation(evaluation));
//	}

//	@Test
//	public void createEvaluationFailure() {
//		Usuario user=new Usuario();
//		user.setArea(new Area());
//		user.setBirthday(Date.valueOf("1999-6-2"));
//		user.setEmail("sada@hotmail.com");
//		user.setFirstName("sara");
//		user.setLastName("ortis");
//		user.setPhone("3116953284");
//		user.setState(new State());
//		
//		
//		Evaluation evaluation = new Evaluation();
//		evaluation.setCareerplan(new Careerplan());
//		evaluation.setComentario("");
//		evaluation.setPeriod(new Period());
//		evaluation.setUsuario1(user);
//		evaluation.setUsuario2(new Usuario());
//		
//		assertTrue(evaluationService.createEvaluation(evaluation));
//	}

}
