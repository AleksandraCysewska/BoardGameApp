package com.example.board.game.service.repositories;

import com.example.board.game.model.entities.GameOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameOffersRepository extends JpaRepository<GameOffer, Long> {
}
