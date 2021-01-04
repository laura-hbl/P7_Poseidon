package com.nnk.springboot.unit.util;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.dto.*;
import com.nnk.springboot.util.ModelConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelConverterTest {
    private ModelConverter modelConverter = new ModelConverter();

    @Test
    @DisplayName("Given a BidListDTO, when ToBidList, then result should match expected BidList")
    public void givenABidListDTO_whenToBidList_thenReturnExpectedBidList() {
        BidList expectedBidList = new BidList("account", "type", BigDecimal.TEN);
        BidListDTO bidListDTO = new BidListDTO("account", "type", BigDecimal.TEN);

        BidList result = modelConverter.toBidList(bidListDTO);

        assertThat(result).isEqualToComparingFieldByField(expectedBidList);
    }

    @Test
    @DisplayName("Given a CurvePointDTO, when ToCurvePoint, then result should match expected CurvePoint")
    public void givenACurvePointDTO_whenToCurvePoint_thenReturnExpectedCurvePoint() {
        CurvePoint expectedCurvePoint = new CurvePoint(1, BigDecimal.TEN, BigDecimal.TEN);
        CurvePointDTO curvePoint = new CurvePointDTO(1, BigDecimal.TEN, BigDecimal.TEN);

        CurvePoint result = modelConverter.toCurvePoint(curvePoint);

        assertThat(result).isEqualToComparingFieldByField(expectedCurvePoint);
    }

    @Test
    @DisplayName("Given a TradeDTO, when ToTrade, then result should match expected Trade")
    public void givenATradeDTO_whenToTrade_thenReturnExpectedTrade() {
        Trade expectedTrade = new Trade("account", "type", BigDecimal.TEN);
        TradeDTO tradeDTO = new TradeDTO("account", "type", BigDecimal.TEN);

        Trade result = modelConverter.toTrade(tradeDTO);

        assertThat(result).isEqualToComparingFieldByField(expectedTrade);
    }

    @Test
    @DisplayName("Given a RatingDTO, when ToRating, then result should match expected Rating")
    public void givenARatingDTO_whenRating_thenReturnExpectedRating() {
        Rating expectedRating = new Rating("moody", "standP",
                "fitch", 3);
        RatingDTO ratingDTO = new RatingDTO("moody", "standP","fitch", 3);

        Rating result = modelConverter.toRating(ratingDTO);

        assertThat(result).isEqualToComparingFieldByField(expectedRating);
    }

    @Test
    @DisplayName("Given a RuleNameDTO, when ToRuleName, then result should match expected RuleName")
    public void givenARuleNameDTO_whenRuleName_thenReturnExpectedRuleName() {
        RuleName expectedRuleName = new RuleName("name", "description","json",
                "template", "sqlStr", "sqlPart");
        RuleNameDTO ruleNameDTO = new RuleNameDTO("name", "description","json", "template",
                "sqlStr", "sqlPart");

        RuleName result = modelConverter.toRuleName(ruleNameDTO);

        assertThat(result).isEqualToComparingFieldByField(expectedRuleName);
    }

    @Test
    @DisplayName("Given an UserDTO, when ToUser, then result should match expected User")
    public void givenAUserDTO_whenUser_thenReturnExpectedUser() {
        User expectedUser = new User("Laura", "passwordA111&", "Laura", "USER");
        UserDTO ruleNameDTO = new UserDTO("Laura", "passwordA111&", "Laura", "USER");

        User result = modelConverter.toUser(ruleNameDTO);

        assertThat(result).isEqualToComparingFieldByField(expectedUser);
    }
}
