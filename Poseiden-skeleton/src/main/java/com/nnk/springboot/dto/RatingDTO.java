package com.nnk.springboot.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class RatingDTO {

    private Integer id;

    private String moodysRating;

    private String standPoorRating;

    private String fitchRating;

    private Integer orderNumber;
}
