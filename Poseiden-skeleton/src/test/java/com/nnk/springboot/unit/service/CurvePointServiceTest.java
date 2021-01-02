package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;
import com.nnk.springboot.util.DTOConverter;
import com.nnk.springboot.util.ModelConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurvePointServiceTest {
    
    @InjectMocks
    private CurvePointService curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;

    @Mock
    private DTOConverter dtoConverter;

    @Mock
    private ModelConverter modelConverter;

    private static CurvePoint curvePoint1;

    private static CurvePoint curvePoint2;

    private static CurvePointDTO curvePoint1DTO;

    private static CurvePointDTO curvePoint2DTO;

    private static List<CurvePointDTO> curvePointListDTO;

    @Before
    public void setUp() {
        curvePoint1DTO = new CurvePointDTO(1, 3,null, BigDecimal.TEN, BigDecimal.TEN,
                LocalDateTime.of(2019, 12, 22, 11, 23));
        curvePoint2DTO = new CurvePointDTO(2, 4,null, BigDecimal.TEN, BigDecimal.TEN,
                LocalDateTime.of(2019, 12, 22, 11, 23));
        curvePoint1 = new CurvePoint(1, 3, null, BigDecimal.TEN, BigDecimal.TEN,
                LocalDateTime.of(2019, 12, 22, 11, 23));
        curvePoint2 = new CurvePoint(2, 4, null, BigDecimal.TEN, BigDecimal.TEN,
                LocalDateTime.of(2019, 12, 22, 11, 23));
        curvePointListDTO = Arrays.asList(curvePoint1DTO, curvePoint2DTO);
    }

    @Test
    @Tag("AddCurvePoint")
    @DisplayName("Given a CurvePoint to save, when addCurvePoint, then curvePoint should be saved correctly")
    public void givenACurvePoint_whenAddCurvePoint_thenCurvePointShouldBeSavedCorrectly() {
        CurvePoint curvePointToAdd = new CurvePoint(3, BigDecimal.TEN, BigDecimal.TEN);
        CurvePointDTO curvePointToAddDTO = new CurvePointDTO(3, BigDecimal.TEN, BigDecimal.TEN);
        when(modelConverter.toCurvePoint(any(CurvePointDTO.class))).thenReturn(curvePointToAdd);
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint1);
        when(dtoConverter.toCurvePointDTO(any(CurvePoint.class))).thenReturn(curvePoint1DTO);

        CurvePointDTO curvePointSaved = curvePointService.addCurvePoint(curvePointToAddDTO);

        assertThat(curvePointSaved).isEqualToComparingFieldByField(curvePoint1DTO);
        InOrder inOrder = inOrder(curvePointRepository, dtoConverter, modelConverter);
        inOrder.verify(modelConverter).toCurvePoint(any(CurvePointDTO.class));
        inOrder.verify(curvePointRepository).save(any(CurvePoint.class));
        inOrder.verify(dtoConverter).toCurvePointDTO(any(CurvePoint.class));
    }

    @Test
    @Tag("UpdateCurvePoint")
    @DisplayName("Given a registered curvePoint, when updateCurvePoint, then curvePoint should be updated correctly")
    public void givenACurvePointToUpdate_whenUpdateCurvePoint_thenCurvePointShouldBeUpdateCorrectly() {
        CurvePoint convertedCurvePoint = new CurvePoint(3, BigDecimal.ONE, BigDecimal.TEN);
        CurvePointDTO curvePoint1DTOUpdated = new CurvePointDTO(1, 3, LocalDateTime.of(2019, 12,
                29, 12, 20), BigDecimal.ONE, BigDecimal.TEN, LocalDateTime.of(2019,
                12, 22, 11, 23));
        CurvePoint curvePoint1Updated = new CurvePoint(1, 3, LocalDateTime.of(2019, 12,
                29, 12, 20), BigDecimal.ONE, BigDecimal.TEN, LocalDateTime.of(2019,
                12, 22, 11, 23));
        when(curvePointRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(curvePoint1));
        when(modelConverter.toCurvePoint(any(CurvePointDTO.class))).thenReturn(convertedCurvePoint);
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint1Updated);
        when(dtoConverter.toCurvePointDTO(any(CurvePoint.class))).thenReturn(curvePoint1DTOUpdated);

        CurvePointDTO result = curvePointService.updateCurvePoint(1,
                new CurvePointDTO(3, BigDecimal.ONE, BigDecimal.TEN));

        assertThat(result).isEqualTo(curvePoint1DTOUpdated);
        InOrder inOrder = inOrder(curvePointRepository, dtoConverter);
        inOrder.verify(curvePointRepository).findById(anyInt());
        inOrder.verify(curvePointRepository).save(any(CurvePoint.class));
        inOrder.verify(dtoConverter).toCurvePointDTO(any(CurvePoint.class));
    }

    @Test
    @Tag("DeleteCurvePoint")
    @DisplayName("Given a curvePoint Id, when deleteCurvePoint, then delete process should be done in correct order")
    public void givenACurvePointId_whenDeleteCurvePoint_thenDeletingShouldBeDoneInCorrectOrder() {
        when(curvePointRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(curvePoint1));

        curvePointService.deleteCurvePoint(anyInt());

        InOrder inOrder = inOrder(curvePointRepository);
        inOrder.verify(curvePointRepository).findById(anyInt());
        inOrder.verify(curvePointRepository).deleteById(anyInt());
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("DeleteCurvePoint")
    @DisplayName("If CurvePoint can't be found, when deleteCurvePoint, then throw ResourceNotFoundException")
    public void givenUnFoundCurvePoint_whenDeleteCurvePoint_thenResourceNotFoundExceptionIsThrown() {
        when(curvePointRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        curvePointService.deleteCurvePoint(anyInt());
    }

    @Test
    @Tag("GetCurvePointById")
    @DisplayName("Given a curvePoint id, when getCurvePointById, then expected curvePoint should be returned correctly")
    public void givenACurvePointId_whenGetCurvePointById_thenExpectedCurvePointShouldBeReturnCorrectly() {
        when(curvePointRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(curvePoint1));
        when(dtoConverter.toCurvePointDTO(any(CurvePoint.class))).thenReturn(curvePoint1DTO);

        CurvePointDTO curvePointFound = curvePointService.getCurvePointById(1);

        assertThat(curvePointFound).isEqualToComparingFieldByField(curvePoint1DTO);
        InOrder inOrder = inOrder(curvePointRepository, dtoConverter);
        inOrder.verify(curvePointRepository).findById(anyInt());
        inOrder.verify(dtoConverter).toCurvePointDTO(any(CurvePoint.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetCurvePointById")
    @DisplayName("If curvePoint can't be found, when getCurvePointById, then throw ResourceNotFoundException")
    public void givenUnFoundCurvePoint_whenGetCurvePointById_thenResourceNotFoundExceptionIsThrown() {
        when(curvePointRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        curvePointService.getCurvePointById(1);
    }

    @Test
    @Tag("GetAllCurvePoint")
    @DisplayName("Given an curvePoint list, when getAllCurvePoint, then result should match expected curvePoint list")
    public void givenACurvePointList_whenGetAllCurvePoint_thenReturnExpectedCurvePointList() {
        when(curvePointRepository.findAll()).thenReturn(Arrays.asList(curvePoint1, curvePoint2));

        when(dtoConverter.toCurvePointDTO(curvePoint1)).thenReturn(curvePoint1DTO);
        when(dtoConverter.toCurvePointDTO(curvePoint2)).thenReturn(curvePoint2DTO);

        List<CurvePointDTO> result = curvePointService.getAllCurvePoint();

        assertThat(result).isEqualTo(curvePointListDTO);
        InOrder inOrder = inOrder(curvePointRepository, dtoConverter);
        inOrder.verify(curvePointRepository).findAll();
        inOrder.verify(dtoConverter).toCurvePointDTO(curvePoint1);
        inOrder.verify(dtoConverter).toCurvePointDTO(curvePoint2);
    }
}
