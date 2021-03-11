package com.app.domino.controller;

import com.app.domino.context.DominoChainContext;
import com.app.domino.controller.validator.StartingPieceIndexValidator;
import com.app.domino.service.DominoChainValueCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.app.domino.controller.validator.DominosValidator;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * Controller in charge of exposing Domino Chain API
 */
@RestController
@EnableWebMvc
public class DominoChainController {

    private final static String APPLICATION_JSON = "application/json";
    private final DominoChainValueCalculator dominoChainValueCalculator;
    private final DominosValidator dominosValidator;
    private final StartingPieceIndexValidator startingPieceIndexValidator;

    @Autowired
    public DominoChainController(DominoChainValueCalculator dominoChainValueCalculator,
                                 DominosValidator dominosValidator,
                                 StartingPieceIndexValidator startingPieceIndexValidator) {

        this.dominoChainValueCalculator = dominoChainValueCalculator;
        this.dominosValidator = dominosValidator;
        this.startingPieceIndexValidator = startingPieceIndexValidator;
    }

    @PostMapping(value = "/domino", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(value = CREATED)
    public int calculateDominoChainValue(@RequestBody DominoChainContext context) {

        startingPieceIndexValidator.validate(context.getStartingPieceIndex(), context.getDominos().size());
        dominosValidator.validate(context.getDominos());
        return dominoChainValueCalculator.execute(context.getDominos(), context.getStartingPieceIndex());
    }
}
