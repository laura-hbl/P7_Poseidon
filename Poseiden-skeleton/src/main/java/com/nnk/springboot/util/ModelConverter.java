package com.nnk.springboot.util;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.dto.*;
import org.springframework.stereotype.Component;

/**
 * Allows the conversion of Model class to DTO class.
 *
 * @author Laura Habdul
 */
@Component
public class ModelConverter {

    /**
     * Converts BidListDTO to BidList.
     *
     * @param bidListDTO BidListDTO object to convert
     * @return The BidList object
     */
    public BidList toBidList(final BidListDTO bidListDTO) {

        return new BidList(bidListDTO.getAccount(), bidListDTO.getType(), bidListDTO.getBidQuantity());
    }

    /**
     * Converts CurvePointDTO to CurvePoint.
     *
     * @param curvePointDTO CurvePointDTO object to convert
     * @return The CurvePoint object
     */
    public CurvePoint toCurvePoint(final CurvePointDTO curvePointDTO) {

        return new CurvePoint(curvePointDTO.getCurveId(), curvePointDTO.getTerm(), curvePointDTO.getValue());
    }

    /**
     * Converts TradeDTO to Trade.
     *
     * @param tradeDTO TradeDTO object to convert
     * @return The Trade object
     */
    public Trade toTrade(final TradeDTO tradeDTO) {

        return new Trade(tradeDTO.getAccount(), tradeDTO.getType(), tradeDTO.getBuyQuantity());
    }

    /**
     * Converts RatingDTO to Rating.
     *
     * @param ratingDTO RatingDTO object to convert
     * @return The Rating object
     */
    public Rating toRating(final RatingDTO ratingDTO) {

        return new Rating(ratingDTO.getMoodysRating(), ratingDTO.getStandPoorRating(), ratingDTO.getFitchRating(),
                ratingDTO.getOrderNumber());
    }

    /**
     * Converts RuleNameDTO to RuleName.
     *
     * @param ruleNameDTO RuleNameDTO object to convert
     * @return The RuleName object
     */
    public RuleName toRuleName(final RuleNameDTO ruleNameDTO) {

        return new RuleName(ruleNameDTO.getName(), ruleNameDTO.getDescription(), ruleNameDTO.getJson(),
                ruleNameDTO.getTemplate(), ruleNameDTO.getSqlStr(), ruleNameDTO.getSqlPart());
    }
}
