package com.usoft.service;

import java.math.BigDecimal;
import java.time.DateTimeException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoft.model.Period;
import com.usoft.repo.PeriodRepositoryInt;

@Service
public class PeriodService implements PeriodServiceInt {

	// TODO Ask the following questions:
	// 1. If we delete a Period, should we delete all Evaluations associated with
	// said period (and eCriteria)?
	// R/ NO. If a period has evaluations, it can't be deleted.
	// 2. What should the methods for the controller return (if any)?
	// 
	// 3. Should we use exceptions, and if so how?
	// Yes. Try doing it like Users did it.

	@Autowired
	private PeriodRepositoryInt periodRepo;

	/**
	 * Create a period and save it to the database.
	 * 
	 * @param period Period entity. It is expected to have a name, a start date and
	 *               an end date.The start date must be before the end date.
	 */
	@Override
	@Transactional
	public boolean createPeriod(Period period) {
		try {
			if (period.getStartDate().before(period.getEndDate())) {
				periodRepo.save(period);
				return true;
			} else {
				throw new DateTimeException("A period's start date can't be after its end date.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	/**
	 * Return an iterable list of all periods.
	 */
	@Override
	@Transactional
	public Iterable<Period> findAllPeriods() {
		return periodRepo.findAll();
	}

	/**
	 * Find a period by its ID.
	 */
	@Override
	@Transactional
	public Period findPeriod(Integer id) {
		return periodRepo.find(id);
	}

	/**
	 * Update a period.
	 * 
	 * @param period Period entity. It contains the ID of the original Period as
	 *               well as the new data that will overwrite the old information.
	 */
	@Override
	@Transactional
	public String updatePeriod(Period period) {
		periodRepo.update(period);
		return "";
	}

	/**
	 * Delete a period by its ID.
	 */
	@Override
	@Transactional
	public void deletePeriod(Integer id) throws Exception{
		Period period = periodRepo.find(id);
		// Throw in case we receive an ID that doesn't exist.
		if (period==null)
			throw new Exception("The period is already deleted.");
		// Throw if the evaluation is already logically deleted.
		if (period.getIsdeleted().equals(BigDecimal.ONE))
			throw new Exception("The period is already deleted.");
		// Throw if the evaluation already has evaluations associated to it.
		if(period.getEvaluations().size()>0)
			throw new Exception("This period can't be deleted because it's associated to evaluations.");
		period.setIsdeleted(BigDecimal.ONE);
		periodRepo.update(period);
	}

}
