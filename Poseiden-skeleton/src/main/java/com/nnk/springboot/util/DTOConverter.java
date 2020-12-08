package com.nnk.springboot.util;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.dto.*;
import org.springframework.stereotype.Component;

@Component
public class DTOConverter {

    public BidListDTO toBidListDTO(final BidList bidList) {

        return new BidListDTO(bidList.getId(), bidList.getAccount(), bidList.getType(), bidList.getBidQuantity());
    }

    public CurvePointDTO toCurvePointDTO(final CurvePoint curvePoint) {

        return new CurvePointDTO(curvePoint.getId(), curvePoint.getCurveId(), curvePoint.getTerm(),
                curvePoint.getValue());
    }

    public TradeDTO toTradeDTO(Trade trade) {

        return new TradeDTO(trade.getId(), trade.getAccount(), trade.getType(), trade.getBuyQuantity());
    }

    public RatingDTO toRatingDTO(Rating rating) {

        return new RatingDTO(rating.getId(), rating.getMoodysRating(), rating.getStandPoorRating(),
                rating.getFitchRating(), rating.getOrderNumber());
    }

    public RuleNameDTO toRuleNameDTO(RuleName ruleName) {

        return new RuleNameDTO(ruleName.getId(), ruleName.getName(), ruleName.getDescription(), ruleName.getJson(),
                ruleName.getTemplate(), ruleName.getSqlStr(), ruleName.getSqlStr());
    }

    public UserDTO toUserDTO(final User user) {

        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getFullname(), user.getRole());
    }
}
