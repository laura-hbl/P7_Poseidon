package com.nnk.springboot.unit.util;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.dto.*;
import com.nnk.springboot.util.DTOConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class DTOConverterTest {

    private DTOConverter dtoConverter = new DTOConverter();

    @Test
    @DisplayName("Given an User, when ToUserDTO, then result should match expected UserDTO")
    public void givenAnUser_whenToUserDTO_thenReturnExpectedUserDTO() {
        UserDTO expectedUserDTO = new UserDTO(1, "Laura", "passwordA123&", "Laura",
                "USER");
        User user = new User(1, "Laura", "passwordA123&", "Laura", "USER");

        UserDTO result = dtoConverter.toUserDTO(user);

        assertThat(result).isEqualToComparingFieldByField(expectedUserDTO);
    }

    @Test
    @DisplayName("Given a BidList, when ToBidListDTO, then result should match expected BidListDTO")
    public void givenABidList_whenToBidListDTO_thenReturnExpectedBidListDTO() {
        BidListDTO expectedBidListDTO = new BidListDTO(1, "account", "type", BigDecimal.TEN);
        BidList bid = new BidList(1, "account", "type", BigDecimal.TEN);

        BidListDTO result = dtoConverter.toBidListDTO(bid);

        assertThat(result).isEqualToComparingFieldByField(expectedBidListDTO);
    }

    @Test
    @DisplayName("Given a CurvePoint, when ToCurvePointDTO, then result should match expected CurvePointDTO")
    public void givenACurvePoint_whenToCurvePointDTO_thenReturnExpectedCurvePointDTO() {
        CurvePointDTO expectedCurvePointDTO = new CurvePointDTO(1,1, BigDecimal.TEN, BigDecimal.TEN);
        CurvePoint curvePoint = new CurvePoint(1, 1, BigDecimal.TEN, BigDecimal.TEN);

        CurvePointDTO result = dtoConverter.toCurvePointDTO(curvePoint);

        assertThat(result).isEqualToComparingFieldByField(expectedCurvePointDTO);
    }

    @Test
    @DisplayName("Given a Trade, when ToTradeDTO, then result should match expected TradeDTO")
    public void givenATrade_whenTradeDTO_thenReturnExpectedTradeDTO() {
        TradeDTO expectedTradeDTO = new TradeDTO(1, "account", "type", BigDecimal.TEN);
        Trade trade = new Trade(1, "account", "type", BigDecimal.TEN);

        TradeDTO result = dtoConverter.toTradeDTO(trade);

        assertThat(result).isEqualToComparingFieldByField(expectedTradeDTO);
    }

    @Test
    @DisplayName("Given a Rating, when ToRatingDTO, then result should match expected RatingDTO")
    public void givenARating_whenRatingDTO_thenReturnExpectedRatingDTO() {
        RatingDTO expectedRatingDTO = new RatingDTO(1, "moody", "standP",
                "fitch", 3);
        Rating rating = new Rating(1, "moody", "standP","fitch", 3);

        RatingDTO result = dtoConverter.toRatingDTO(rating);

        assertThat(result).isEqualToComparingFieldByField(expectedRatingDTO);
    }

    @Test
    @DisplayName("Given a RuleName, when ToRuleNameDTO, then result should match expected RuleNameDTO")
    public void givenARuleName_whenRuleNameDTO_thenReturnExpectedRuleNameDTO() {
        RuleNameDTO expectedRuleNameDTO = new RuleNameDTO(1, "name", "description","json",
                "template", "sqlStr", "sqlPart");
        RuleName ruleName = new RuleName(1, "name", "description","json", "template",
                "sqlStr", "sqlPart");

        RuleNameDTO result = dtoConverter.toRuleNameDTO(ruleName);

        assertThat(result).isEqualToComparingFieldByField(expectedRuleNameDTO);
    }
}
