package com.nnk.springboot.service;

import com.nnk.springboot.dto.CurvePointDTO;

import java.util.List;

/**
 * CurvePointService interface.
 *
 * @author Laura Habdul
 */
public interface ICurvePointService {

    /**
     * Adds a new curvePoint in database.
     *
     * @param curvePoint the curvePoint to be added
     * @return The curvePoint saved converted to a CurvePointDTO object
     */
    CurvePointDTO addCurvePoint(final CurvePointDTO curvePoint);

    /**
     * Updates an curvePoint based on the given id.
     *
     * @param curvePointId id of the curvePoint to be updated
     * @param curvePoint   the curvePoint to be updated
     * @return The curvePoint updated converted to a CurvePointDTO object
     */
    CurvePointDTO updateCurvePoint(final int curvePointId, final CurvePointDTO curvePoint);

    /**
     * Deletes an curvePoint based on the given id.
     *
     * @param curvePointId id of the curvePoint to be deleted
     */
    void deleteCurvePoint(final int curvePointId);

    /**
     * Retrieves an curvePoint based on the given id.
     *
     * @param curvePointId id of the curvePoint to be found
     * @return The curvePoint retrieved converted to a CurvePointDTO object
     */
    CurvePointDTO getCurvePointById(final int curvePointId);

    /**
     * Retrieves the curvePoint list.
     *
     * @return The curvePoint list retrieved where each CurvePoint is converted to a CurvePointDTO object
     */
    List<CurvePointDTO> getAllCurvePoint();
}
