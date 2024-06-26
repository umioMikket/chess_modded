package com.umiomikket.chessgame.vanilla.figures;

import com.umiomikket.chessgame.ChessGame;
import com.umiomikket.chessgame.chess.*;
import com.umiomikket.chessgame.vanilla.Vanilla;
import com.umiomikket.chessgame.vanilla.events.EventFigureGetPositions;
import com.umiomikket.chessgame.vanilla.events.EventStatisticsGetWorkingTime;

import java.util.ArrayList;

public class FigureQueen extends Figure {
    private long howLongUpdate;

    public FigureQueen() {
        super(ChessGame.getModificationsManager().getModification("vanilla"), "queen");
        setSpriteBlackTeam("./assets/B_Queen.png");
        setSpriteWhiteTeam("./assets/W_Queen.png");

        howLongUpdate = 0l;

        addEventClass(this);
    }

    @EventHandler
    public void getFigurePositions(EventFigureGetPositions event) {
        if (event.getFigure() != this) return;
        long startTime = System.nanoTime();

        Vanilla vanilla = (Vanilla) ChessGame.getModificationsManager().getModification("vanilla");

        BoardCell cell = event.getCell();
        Board board = cell.board;
        int fx = cell.x, fy = cell.y;

        cell.setFigure(vanilla.figureRook);
        ChessGame.getEventManager().playEvent(new EventFigureGetPositions(cell));
        ArrayList<Integer[]> positions = new ArrayList<>(event.getFigurePositions());

        cell.setFigure(vanilla.figureBishop);
        ChessGame.getEventManager().playEvent(new EventFigureGetPositions(cell));
        positions.addAll(event.getFigurePositions());

        cell.setFigure(this);
        event.setFigurePositions(positions);
        howLongUpdate = System.nanoTime() - startTime;
    }

    @EventHandler
    public void getWorkingTime(EventStatisticsGetWorkingTime event) {
        event.addWorkingTime("figureQueen > getFigurePositions", howLongUpdate);
    }
}
