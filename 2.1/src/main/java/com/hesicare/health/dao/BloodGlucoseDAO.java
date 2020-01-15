package com.hesicare.health.dao;

import com.hesicare.common.utils.IHibernateBaseDao;
import com.hesicare.health.entity.BloodGlucose;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class BloodGlucoseDAO {

	protected static final Logger LOGGER = LoggerFactory.getLogger(BloodGlucoseDAO.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private IHibernateBaseDao<BloodGlucose> hibernateBloodGlucoseDao;

	public List<BloodGlucose> getBloodGlucoseByOperateState(String operateState) {
		List<BloodGlucose> bloodGlucoses = null;
		try {
			bloodGlucoses = hibernateBloodGlucoseDao.getList(BloodGlucose.class, "data_status", operateState);
			System.out.println(bloodGlucoses+"213edd");
		} catch (Exception e) {
			LOGGER.debug("getBloodGlucoseByOperateState: " + e);
		}
		return bloodGlucoses;
	}
	
	public void updateBloodGlucoseOperateState(Long id, short operateState) {
		this.jdbcTemplate.update("update patient_blood_glucose_view set data_status = ? where id = ?", operateState, id);
	}

}
