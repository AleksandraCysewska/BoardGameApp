package com.example.board.game.service.repositories;

import com.example.board.game.model.entities.UpdateOfDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountOfGamesRepository extends JpaRepository<UpdateOfDiscount, Long> {
}
