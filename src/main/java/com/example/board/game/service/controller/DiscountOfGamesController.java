package com.example.board.game.service.controller;

import com.example.board.game.model.dto.GameOffersDTO;
import com.example.board.game.model.entities.GameOffer;
import com.example.board.game.model.entities.UpdateOfDiscount;
import com.example.board.game.service.DiscountOfGamesService;
import com.example.board.game.service.repositories.DiscountOfGamesRepository;
import com.example.board.game.service.repositories.GameOffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/promotion")
public class DiscountOfGamesController {

    @Autowired
    private DiscountOfGamesService discountOfGamesService;
    @Autowired
    private DiscountOfGamesRepository discountOfGamesRepository;
    @Autowired
    private GameOffersRepository gameOffersRepository;

    //@CrossOrigin
    @RequestMapping(value = "/getLatest", method = RequestMethod.GET)
    public List<GameOffersDTO> getLatest() throws IOException {
        List<GameOffersDTO> gameOffersDTOS = discountOfGamesService.getContentFromWeb();

        /*
        data create
         */
        gameOffersDTOS.forEach(gameOffersDTO -> {
            gameOffersRepository.saveAndFlush(new GameOffer(null, gameOffersDTO.getOffer(), gameOffersDTO.getDate()));
        });
        gameOffersRepository.deleteById(gameOffersRepository.findAll().stream()
                .max(Comparator.comparing(GameOffer::getOfferId)).get().getOfferId());
        gameOffersRepository.flush();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();

        discountOfGamesRepository.saveAndFlush(new UpdateOfDiscount(null, yesterday, 0, false));
        /*
        end of data create
        */
        Date dateUpdate = discountOfGamesRepository.findAll().stream()
                .max(Comparator.comparing(UpdateOfDiscount::getUpdateDate)).get().getUpdateDate();

        List<GameOffersDTO> newOffers = gameOffersDTOS.stream()
                .filter(gameOffer -> gameOffer.getDate().after(dateUpdate))
                .collect(Collectors.toList());
        newOffers.forEach(offer -> {
            gameOffersRepository.saveAndFlush(new GameOffer(null, offer.getOffer(), offer.getDate()));
        });
        discountOfGamesRepository.saveAndFlush(new UpdateOfDiscount(null, new Date(), 0, false));
        return newOffers;
    }
}
