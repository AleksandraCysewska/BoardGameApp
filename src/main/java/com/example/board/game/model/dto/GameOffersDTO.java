package com.example.board.game.model.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameOffersDTO {
    private String offer;
    private Date date;
}
