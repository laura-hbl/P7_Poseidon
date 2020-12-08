package com.nnk.springboot.service;

import com.nnk.springboot.dto.CurvePointDTO;

import java.util.List;

public interface ICurvePointService {

    CurvePointDTO addCurvePoint(final CurvePointDTO curvePoint);

    CurvePointDTO updateCurvePoint(final int curvePointId, final CurvePointDTO curvePoint);

    void deleteCurvePoint(final int curvePointId);

    CurvePointDTO getCurvePointById(final int curvePointId);

    List<CurvePointDTO> getAllCurvePoint();
}

