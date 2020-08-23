package com.usoft.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoft.model.Careerplan;
import com.usoft.model.Criteria;
import com.usoft.model.Evaluation;
import com.usoft.model.Evaluationcriteria;
import com.usoft.model.EvaluationcriteriaPK;
import com.usoft.model.Indicatorposition;
import com.usoft.model.Period;
import com.usoft.model.Usuario;
import com.usoft.repo.CareerPlanRepositoryInt;
import com.usoft.repo.CriteriaRepositoryInt;
import com.usoft.repo.EvaluationCriteriaRepositoryInt;
import com.usoft.repo.EvaluationRepositoryInt;
import com.usoft.repo.PeriodRepositoryInt;
import com.usoft.repo.UserRepositoryInt;

@Service
public class EvaluationService implements EvaluationServiceInt {
	
	@Autowired
	private EvaluationRepositoryInt evaluationRepo;
	
	@Autowired
	private UserRepositoryInt userRepo;
	
	@Autowired
	private CareerPlanRepositoryInt careerPlanRepo;
	
	@Autowired
	private PeriodRepositoryInt periodRepo;
	
	@Autowired
	private EvaluationCriteriaRepositoryInt evaluationCriteriaRepo;
	
	@Autowired
	private CriteriaRepositoryInt criteriaRepo;
	
	
	
	/**
	 * Create an evaluation and save it to the Database.
	 * @param eval Evaluation entity, that only has its own data.
	 * @param careerPlan Id for the CareerPlan of the evaluation.
	 * @param period Id for the Period of the evaluation.
	 * @param evaluator Id for the user who made the evaluation.
	 * @param employee Id for the user being evaluated.
	 * @param criteriaData List of data 3-tuples with criteria ids, comments and values for the EvaluationCriteria associated with the new evaluation
	 */
	@Override
	@Transactional
	public Evaluation createEvaluation(Evaluation eval, int careerPlan, int period, int evaluator, int employee, ArrayList<String[]> criteriaData) {
		try {
			if(eval==null || criteriaData.size()==0) {
				throw new Exception("Evaluation entity OR criteria data are null.");
			}else {
				
				Iterator<Evaluation> evaluations = evaluationRepo.findUserPeriod(period, employee).iterator();	
				
				if (evaluations.hasNext()) {
					Evaluation evaluation = evaluations.next();
					evaluation.setUsuario1(userRepo.find(evaluator));
					evaluation.setComentario(eval.getComentario());					
					
					HashMap<Long, String[]> criteriaEval = new HashMap<>();
					for (String[] data : criteriaData) {
						for (int i = 0; i < data.length; i++) {
							criteriaEval.put(Long.parseLong(data[0]), data);
						}						
					}				
					
					for (Evaluationcriteria evaluationcriteria : evaluation.getEvaluationcriterias()) {						
						String[] criteriaUpadate = criteriaEval.get(evaluationcriteria.getCriteriaId());
						evaluationcriteria.setValor(new BigDecimal(criteriaUpadate[1]));
						evaluationcriteria.setComentario(criteriaUpadate[2]);							
						evaluationCriteriaRepo.update(evaluationcriteria);		

					}			
				}else {
					Evaluation e2 = eval;
					Usuario emple = userRepo.find(employee);
					Usuario evalu = userRepo.find(evaluator);
					Period perio = periodRepo.find(period);
					Careerplan plan = careerPlanRepo.findById(careerPlan);
					e2.setCareerplan(plan);
					e2.setPeriod(perio);
					e2.setUsuario1(evalu);
					e2.setUsuario2(emple);
					Evaluation e = evaluationRepo.save(e2);
					System.out.println("Before service method ends, the id found is: " + e.getId());
					for (String[] data : criteriaData) {
						Evaluationcriteria evaluationCriteria = new Evaluationcriteria();
						Criteria c = criteriaRepo.find(Integer.parseInt(data[0]));
						EvaluationcriteriaPK pk = new EvaluationcriteriaPK();
						pk.setEvaluationId(e.getId());
						pk.setCriteriaId(c.getId());
						evaluationCriteria.setIsdeleted(BigDecimal.ZERO);
						evaluationCriteria.setCriteria(c);
						evaluationCriteria.setCriteriaId(c.getId());
						evaluationCriteria.setValor(new BigDecimal(data[1]));
						evaluationCriteria.setComentario(data[2]);
						evaluationCriteria.setEvaluation(e);
						evaluationCriteria.setEvaluationId(e.getId());
						System.out.println("e.criteria setup finished");
						evaluationCriteriaRepo.save(evaluationCriteria);
					}				
					return e;		
				}				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
		
		
		
		
	}

	/**
	 * Return a list of all evaluations-
	 * Do not use this method, as at the moment we have no reason to return all evaluations.
	 */
	@Override
	@Transactional
	public Iterable<Evaluation> findAllEvaluations() {
		System.out.println("Retrieving evaluations...");
		return evaluationRepo.findAll();
	}


	/**
	 * Find an evaluation by its ID.
	 */
	@Override
	@Transactional
	public Evaluation findEvaluation(int id) {
		return evaluationRepo.find(id);
	}




	@Override
	public void deleteEvaluation(int id) throws Exception{
		Evaluation eval = this.findEvaluation(id);
		if(eval.getIsdeleted().equals(BigDecimal.ONE))
			throw new Exception("This evaluation is already deleted.");
		eval.setIsdeleted(BigDecimal.ONE);
//		for (Evaluationcriteria e : eval.getEvaluationcriterias()) {
//			e.setIsdeleted(BigDecimal.ONE);
//		}
		evaluationRepo.update(eval);
	}



	/**
	 * Return a list of evaluations done to a user by using his ID.
	 */
	@Override
	@Transactional
	public Iterable<Evaluation> getEvaluationsOfUser(int id) {
		ArrayList<Evaluation> out = new ArrayList<Evaluation>();
		Iterable<Evaluation> list = findAllEvaluations();
		for(Evaluation e : list) {
			if(e.getUsuario2().getId() == (long) id) {
				out.add(e);
			}
		}
		return out;
	}

	/**
	 * Depreciated method, don't use.
	 */
	@Override
	@Transactional
	public Iterable<Evaluation> getEvaluationsByPeriodAndCareerPlan(int period, int careerPlan) {
		ArrayList<Evaluation> out = new ArrayList<Evaluation>();
		Iterable<Evaluation> list = findAllEvaluations();
		System.out.println("Retrieved all evaluations");
		for(Evaluation e : list) {
			if(e.getPeriod().getId() == (long) period && e.getCareerplan().getId() == (long) careerPlan) {
				out.add(e);
			}
		}
		return out;
	}

	/**
	 * Find a list of evaluations by using the ID of the period they were made in.
	 */
	@Override
	@Transactional
	public Iterable<Evaluation> getEvaluationsByPeriod(int period) {
		ArrayList<Evaluation> out = new ArrayList<Evaluation>();
		Iterable<Evaluation> list = findAllEvaluations();
		for(Evaluation e : list) {
			if(e.getPeriod().getId() == (long) period) {
				out.add(e);
			}
		}
		return out;
	}

	/**
	 * Find a list of evaluations by using the ID of the CareerPlan they belong to.
	 */
	@Override
	@Transactional
	public Iterable<Evaluation> getEvaluationsByCareerPlan(int careerPlan) {
		ArrayList<Evaluation> out = new ArrayList<Evaluation>();
		Iterable<Evaluation> list = findAllEvaluations();
		for(Evaluation e : list) {
			System.out.println("Processing an evaluation");
			if((int) e.getCareerplan().getId() == careerPlan) {
				System.out.println("Adding eval to list");
				out.add(e);
			}
		}
		return out;
	}

	@Override
	public Iterable<Evaluation> findUserPeriod(long idPeriod, long idUser) {
		return evaluationRepo.findUserPeriod(idPeriod, idUser);
	}


	

}
