package com.nnk.springboot.integration;

import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.service.CurvePointService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource({"/application-test.properties"})
@Sql(scripts = "/data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CurvePointServiceIT {

    @Autowired
    private CurvePointService curvePointService;

    @Test
    @Tag("AddCurvePoint")
    @DisplayName("If CurvePoint is not registered, when addCurvePoint, then return CurvePoint saved")
    public void givenAnUnRegisteredCurvePoint_whenAddCurvePoint_thenCurvePointSavedShouldBeReturned() {
        CurvePointDTO curvePointDTO = new CurvePointDTO( 3, BigDecimal.TEN, BigDecimal.TEN);

        CurvePointDTO curvePointSaved = curvePointService.addCurvePoint(curvePointDTO);

        assertNotNull(curvePointSaved);
        assertThat(curvePointSaved.getCurveId()).isEqualTo(3);
        assertThat(curvePointSaved.getTerm()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    @Tag("UpdateCurvePoint")
    @DisplayName("Given an curvePoint to update, when updateCurvePoint, then return curvePoint updated")
    public void givenAnCurvePointToUpdate_whenUpdateCurvePoint_thenCurvePointUpdatedShouldBeReturned() {
        CurvePointDTO curvePointToUpdate = new CurvePointDTO(3, BigDecimal.TEN, BigDecimal.valueOf(200));

        CurvePointDTO curvePointUpdated = curvePointService.updateCurvePoint(1, curvePointToUpdate);

        assertNotNull(curvePointUpdated);
        assertThat(curvePointUpdated.getValue()).isEqualTo(BigDecimal.valueOf(200));
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("UpdateCurvePoint")
    @DisplayName("If CurvePoint id cant be found, when updateCurvePoint, then throw ResourceNotFoundException")
    public void givenUnFoundCurvePointId_whenUpdateCurvePoint_thenResourceNotFoundExceptionIsThrown() {
        CurvePointDTO curvePointToUpdate = new CurvePointDTO( 3, BigDecimal.TEN, BigDecimal.valueOf(200));

        curvePointService.updateCurvePoint(6, curvePointToUpdate);
    }

    @Test
    @Tag("DeleteCurvePoint")
    @DisplayName("Given an curvePoint to delete, when deleteCurvePoint, then curvePoint should be delete successfully")
    public void givenAnCurvePointId_whenDeleteCurvePoint_thenCurvePointShouldBeDeleteSuccessfully() {
        curvePointService.deleteCurvePoint(2);

        assertThrows(ResourceNotFoundException.class, () -> { curvePointService.deleteCurvePoint(2);
        });
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("DeleteCurvePoint")
    @DisplayName("If curvePoint id cant be found, when deleteCurvePoint, then throw ResourceNotFoundException")
    public void givenUnFoundCurvePointId_whenDeleteCurvePoint_thenResourceNotFoundExceptionIsThrown() {
        curvePointService.deleteCurvePoint(6);
    }

    @Test
    @Tag("GetCurvePointById")
    @DisplayName("Given an curvePoint id, when getCurvePointById, then expected curvePoint should be returned")
    public void givenAnCurvePointId_whenGetCurvePointById_thenExpectedCurvePointShouldBeReturned() {
        CurvePointDTO curvePoint = curvePointService.getCurvePointById(2);

        assertNotNull(curvePoint);
        assertThat(curvePoint.getCurveId()).isEqualTo(5);
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetCurvePointById")
    @DisplayName("If curvePoint id cant be found, when getCurvePointById, then throw ResourceNotFoundException")
    public void givenUnFoundCurvePointId_whenGetCurvePointById_thenResourceNotFoundExceptionIsThrown() {
        curvePointService.getCurvePointById(6);
    }

    @Test
    @Tag("GetAllCurvePoint")
    @DisplayName("When getAllCurvePoint, then curvePoint list should be returned")
    public void whenGetAllCurvePoint_thenCurvePointListShouldBeReturned() {
        List<CurvePointDTO> curvePoints = curvePointService.getAllCurvePoint();

        assertNotNull(curvePoints);
        assertThat(curvePoints.size()).isEqualTo(2);
    }
}
