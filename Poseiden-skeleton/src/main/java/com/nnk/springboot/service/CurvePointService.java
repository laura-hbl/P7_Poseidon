package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.CurvePointRepository;
import com.nnk.springboot.util.DTOConverter;
import com.nnk.springboot.util.ModelConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods that allow interaction between CurvePoint business logic and CurvePoint repository.
 *
 * @author Laura Habdul
 */
@Service
public class CurvePointService implements ICurvePointService {

    /**
     * CurvePointService logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(CurvePointService.class);

    /**
     * CurvePointRepository instance.
     */
    private final CurvePointRepository curvePointRepository;

    /**
     * DTOConverter instance.
     */
    private final DTOConverter dtoConverter;

    /**
     * ModelConverter instance.
     */
    private final ModelConverter modelConverter;

    /**
     * Constructor of class CurvePointService.
     * Initialize curvePointRepository, dtoConverter and modelConverter.
     *
     * @param curvePointRepository CurvePointRepository instance
     * @param dtoConverter         DTOConverter instance
     * @param modelConverter       ModelConverter instance
     */
    @Autowired
    public CurvePointService(final CurvePointRepository curvePointRepository, final DTOConverter dtoConverter,
                             final ModelConverter modelConverter) {
        this.curvePointRepository = curvePointRepository;
        this.dtoConverter = dtoConverter;
        this.modelConverter = modelConverter;
    }

    /**
     * Converts the curvePointDTO object to add to a CurvePoint Model object, saved it to database by calling
     * CurvePointRepository's save method. Then, converts the saved curvePoint to a CurvePointDTO object.
     *
     * @param curvePointDTO the curvePoint to be added
     * @return The curvePoint saved converted to a CurvePointDTO object
     */
    public CurvePointDTO addCurvePoint(final CurvePointDTO curvePointDTO) {
        LOGGER.debug("Inside CurvePointService.addCurvePoint");

        CurvePoint curvePoint = modelConverter.toCurvePoint(curvePointDTO);
        curvePoint.setCreationDate(LocalDateTime.now());
        CurvePoint curvePointSaved = curvePointRepository.save(curvePoint);

        return dtoConverter.toCurvePointDTO(curvePointSaved);
    }

    /**
     * Checks if the given curvePoint to update is registered by calling CurvePointRepository's findById method,
     * if so converts the CurvePoint object to an CurvePoint object, updates data, then saved to database by calling
     * CurvePointRepository's save method and converted to a CurvePointDTO object. Else, ResourceNotFoundException
     * is thrown.
     *
     * @param curvePointId  id of the curvePoint to be updated
     * @param curvePointDTO the curvePoint to be updated
     * @return The curvePoint updated converted to a CurvePointDTO object
     */
    public CurvePointDTO updateCurvePoint(final int curvePointId, final CurvePointDTO curvePointDTO) {
        LOGGER.debug("Inside CurvePointService.updateCurvePoint");

        curvePointRepository.findById(curvePointId).orElseThrow(() ->
                new ResourceNotFoundException("No curvePoint registered with this id"));

        CurvePoint curvePoint = modelConverter.toCurvePoint(curvePointDTO);
        curvePoint.setId(curvePointId);
        curvePoint.setAsOfDate(LocalDateTime.now());
        CurvePoint curvePointUpdated = curvePointRepository.save(curvePoint);

        return dtoConverter.toCurvePointDTO(curvePointUpdated);
    }

    /**
     * Checks if the given curvePoint to delete is registered by calling CurvePointRepository's findById
     * method, if so curvePoint found is deleted by calling CurvePointRepository's delete method. Else,
     * ResourceNotFoundException is thrown.
     *
     * @param curvePointId id of the curvePoint to be deleted
     */
    public void deleteCurvePoint(final int curvePointId) {
        LOGGER.debug("Inside CurvePointService.deleteCurvePoint");

        curvePointRepository.findById(curvePointId).orElseThrow(() ->
                new ResourceNotFoundException("No curvePoint registered with this id"));

        curvePointRepository.deleteById(curvePointId);
    }

    /**
     * Calls CurvePointRepository's findById method to retrieves the curvePoint with the given id and checks if
     * curvePoint exists in database, if so converts the found CurvePoint to CurvePointDTO object. Else,
     * ResourceNotFoundException is thrown.
     *
     * @param curvePointId id of the curvePoint to be found
     * @return The curvePoint found converted to an CurvePointDTO object
     */
    public CurvePointDTO getCurvePointById(final int curvePointId) {
        LOGGER.debug("Inside CurvePointService.getCurvePointById");

        CurvePoint curvePoint = curvePointRepository.findById(curvePointId).orElseThrow(() ->
                new ResourceNotFoundException("No curvePoint registered with this id"));

        return dtoConverter.toCurvePointDTO(curvePoint);
    }

    /**
     * Retrieves all curvePoints by calling CurvePointRepository's findAll() method and each curvePoint from
     * the list is converted to an CurvePointDTO object and added to an ArrayList.
     *
     * @return The curvePoint list
     */
    public List<CurvePointDTO> getAllCurvePoint() {
        LOGGER.debug("Inside CurvePointService.getAllCurvePoint");

        List<CurvePoint> curvePoints = curvePointRepository.findAll();
        List<CurvePointDTO> curvePointList = new ArrayList<>();

        for (CurvePoint curvePoint : curvePoints) {
            CurvePointDTO curvePointDTO = dtoConverter.toCurvePointDTO(curvePoint);
            curvePointList.add(curvePointDTO);
        }

        return curvePointList;
    }
}
