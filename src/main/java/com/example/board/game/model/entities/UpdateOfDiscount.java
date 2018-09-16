package com.example.board.game.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "UPDATES")
public class UpdateOfDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long updateId;
    @Column
    private Date updateDate;
    @Column
    private int userID;
    @Column
    private boolean alertIsSend;

}
