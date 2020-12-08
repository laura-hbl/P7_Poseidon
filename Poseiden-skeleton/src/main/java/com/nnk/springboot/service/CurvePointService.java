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

import java.util.ArrayList;
import java.util.List;

@Service
public class CurvePointService implements ICurvePointService {

    private static final Logger LOGGER = LogManager.getLogger(CurvePointService.class);

    private final CurvePointRepository curvePointRepository;

    private final DTOConverter dtoConverter;

    private final ModelConverter modelConverter;

    @Autowired
    public CurvePointService(final CurvePointRepository curvePointRepository, final DTOConverter dtoConverter,
                         final ModelConverter modelConverter) {
        this.curvePointRepository = curvePointRepository;
        this.dtoConverter = dtoConverter;
        this.modelConverter = modelConverter;
    }

    public CurvePointDTO addCurvePoint(final CurvePointDTO curvePointDTO) {
        LOGGER.debug("Inside CurvePointService.updateCurvePoint");

        CurvePoint curvePoint = modelConverter.toCurvePoint(curvePointDTO);
        CurvePoint curvePointUpdated = curvePointRepository.save(curvePoint);

        return dtoConverter.toCurvePointDTO(curvePointUpdated);
    }

    public CurvePointDTO updateCurvePoint(final int curvePointId, final CurvePointDTO curvePointDTO) {
        LOGGER.debug("Inside CurvePointService.updateCurvePoint");

        curvePointDTO.setId(curvePointId);
        CurvePoint curvePoint = modelConverter.toCurvePoint(curvePointDTO);
        CurvePoint curvePointUpdated = curvePointRepository.save(curvePoint);

        return dtoConverter.toCurvePointDTO(curvePointUpdated);
    }

    public void deleteCurvePoint(final int curvePointId) {
        LOGGER.debug("Inside CurvePointService.deleteCurvePoint");

        CurvePoint curvePoint = curvePointRepository.findById(curvePointId).orElseThrow(() ->
                new ResourceNotFoundException("No CurvePoint registered with this id"));

        curvePointRepository.delete(curvePoint);
    }

    public CurvePointDTO getCurvePointById(final int curvePointId) {
        LOGGER.debug("Inside CurvePointService.getCurvePointById");

        CurvePoint curvePoint = curvePointRepository.findById(curvePointId).orElseThrow(() ->
                new ResourceNotFoundException("No curvePoint registered with this id"));

        return dtoConverter.toCurvePointDTO(curvePoint);
    }

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
