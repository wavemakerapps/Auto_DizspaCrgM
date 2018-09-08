/*Copyright (c) 2018-2019 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.auto_dizspacrgm.hrdb.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.wavemaker.commons.MessageResource;
import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.auto_dizspacrgm.hrdb.Vacation;


/**
 * ServiceImpl object for domain model class Vacation.
 *
 * @see Vacation
 */
@Service("hrdb.VacationService")
@Validated
public class VacationServiceImpl implements VacationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VacationServiceImpl.class);


    @Autowired
    @Qualifier("hrdb.VacationDao")
    private WMGenericDao<Vacation, Integer> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<Vacation, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "hrdbTransactionManager")
    @Override
    public Vacation create(Vacation vacation) {
        LOGGER.debug("Creating a new Vacation with information: {}", vacation);

        Vacation vacationCreated = this.wmGenericDao.create(vacation);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(vacationCreated);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Vacation getById(Integer vacationId) {
        LOGGER.debug("Finding Vacation by id: {}", vacationId);
        return this.wmGenericDao.findById(vacationId);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Vacation findById(Integer vacationId) {
        LOGGER.debug("Finding Vacation by id: {}", vacationId);
        try {
            return this.wmGenericDao.findById(vacationId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No Vacation found with id: {}", vacationId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public List<Vacation> findByMultipleIds(List<Integer> vacationIds, boolean orderedReturn) {
        LOGGER.debug("Finding Vacations by ids: {}", vacationIds);

        return this.wmGenericDao.findByMultipleIds(vacationIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "hrdbTransactionManager")
    @Override
    public Vacation update(Vacation vacation) {
        LOGGER.debug("Updating Vacation with information: {}", vacation);

        this.wmGenericDao.update(vacation);
        this.wmGenericDao.refresh(vacation);

        return vacation;
    }

    @Transactional(value = "hrdbTransactionManager")
    @Override
    public Vacation delete(Integer vacationId) {
        LOGGER.debug("Deleting Vacation with id: {}", vacationId);
        Vacation deleted = this.wmGenericDao.findById(vacationId);
        if (deleted == null) {
            LOGGER.debug("No Vacation found with id: {}", vacationId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), Vacation.class.getSimpleName(), vacationId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "hrdbTransactionManager")
    @Override
    public void delete(Vacation vacation) {
        LOGGER.debug("Deleting Vacation with {}", vacation);
        this.wmGenericDao.delete(vacation);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Page<Vacation> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Vacations");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Page<Vacation> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Vacations");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service hrdb for table Vacation to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service hrdb for table Vacation to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }



}