package com.example.board.game.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
//@Entity
@ToString
//@Table(name = "GAME_OFFERS")
public class GameOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;
    @Column(length = 3000)
    private String offer;
    @Column
    private Date dateOfOffer;

}
