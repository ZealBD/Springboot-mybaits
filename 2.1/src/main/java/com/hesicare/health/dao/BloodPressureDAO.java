package com.hesicare.health.dao;

import com.hesicare.common.utils.IHibernateBaseDao;
import com.hesicare.health.entity.BloodPressure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class BloodPressureDAO {

	protected static final Logger LOGGER = LoggerFactory.getLogger(BloodPressureDAO.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private IHibernateBaseDao<BloodPressure> hibernateBloodPressureDao;


	  public List<BloodPressure> getPressureByState() {
		List<BloodPressure> bloodPressures = null;
		try {
			bloodPressures = hibernateBloodPressureDao.getListIsNotNull(BloodPressure.class,"status");
		} catch (Exception e) {
			LOGGER.debug("getBloodPressureByCardNumber: " + e);
		}
		return bloodPressures;
	}
	
	public void updatePressureById(Long id, short State) {
		this.jdbcTemplate.update("update patient_blood_pressure_view set status = ? where id = ?", State, id);
	}


}
