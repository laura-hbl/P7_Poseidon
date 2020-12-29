package com.nnk.springboot.util;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.dto.*;
import org.springframework.stereotype.Component;

/**
 * Allows the conversion of DTO class to Model class.
 *
 * @author Laura Habdul
 */
@Component
public class DTOConverter {

    /**
     * Converts BidList to BidListDTO.
     *
     * @param bidList BidList object to convert
     * @return The BidListDTO object
     */
    public BidListDTO toBidListDTO(final BidList bidList) {

        return new BidListDTO(bidList.getId(), bidList.getAccount(), bidList.getType(), bidList.getBidQuantity());
    }

    /**
     * Converts CurvePoint to CurvePointDTO.
     *
     * @param curvePoint CurvePoint object to convert
     * @return The CurvePointDTO object
     */
    public CurvePointDTO toCurvePointDTO(final CurvePoint curvePoint) {

        return new CurvePointDTO(curvePoint.getId(), curvePoint.getCurveId(), curvePoint.getTerm(),
                curvePoint.getValue());
    }

    /**
     * Converts Trade to TradeDTO.
     *
     * @param trade Trade object to convert
     * @return The TradeDTO object
     */
    public TradeDTO toTradeDTO(final Trade trade) {

        return new TradeDTO(trade.getId(), trade.getAccount(), trade.getType(), trade.getBuyQuantity());
    }

    /**
     * Converts RatingDTO to RatingDTO.
     *
     * @param rating Rating object to convert
     * @return The RatingDTO object
     */
    public RatingDTO toRatingDTO(final Rating rating) {

        return new RatingDTO(rating.getId(), rating.getMoodysRating(), rating.getStandPoorRating(),
                rating.getFitchRating(), rating.getOrderNumber());
    }

    /**
     * Converts RuleName to RuleNameDTO.
     *
     * @param ruleName RuleName object to convert
     * @return The RuleNameDTO object
     */
    public RuleNameDTO toRuleNameDTO(final RuleName ruleName) {

        return new RuleNameDTO(ruleName.getId(), ruleName.getName(), ruleName.getDescription(), ruleName.getJson(),
                ruleName.getTemplate(), ruleName.getSqlStr(), ruleName.getSqlPart());
    }

    /**
     * Converts User to UserDTO.
     *
     * @param user User object to convert
     * @return The UserDTO object
     */
    public UserDTO toUserDTO(final User user) {

        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getFullname(), user.getRole());
    }
}
