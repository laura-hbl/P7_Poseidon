package com.nnk.springboot.util;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.dto.*;
import org.springframework.stereotype.Component;

@Component
public class ModelConverter {

    public BidList toBidList(final BidListDTO bidListDTO) {

        return new BidList(bidListDTO.getId(), bidListDTO.getAccount(), bidListDTO.getType(), bidListDTO.getBidQuantity());
    }

    public CurvePoint toCurvePoint(final CurvePointDTO curvePointDTO) {

        return new CurvePoint(curvePointDTO.getId(), curvePointDTO.getCurveId(), curvePointDTO.getTerm(),
                curvePointDTO.getValue());
    }

    public Trade toTrade(TradeDTO tradeDTO) {

        return new Trade(tradeDTO.getId(), tradeDTO.getAccount(), tradeDTO.getType(), tradeDTO.getBuyQuantity());
    }

    public Rating toRating(RatingDTO ratingDTO) {

        return new Rating(ratingDTO.getId(), ratingDTO.getMoodysRating(), ratingDTO.getStandPoorRating(),
                ratingDTO.getFitchRating(), ratingDTO.getOrderNumber());
    }

    public RuleName toRuleName(RuleNameDTO ruleNameDTO) {

        return new RuleName(ruleNameDTO.getId(), ruleNameDTO.getName(), ruleNameDTO.getDescription(),
                ruleNameDTO.getJson(), ruleNameDTO.getTemplate(), ruleNameDTO.getSqlStr(), ruleNameDTO.getSqlStr());
    }
}
