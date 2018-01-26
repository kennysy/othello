package ks.othello;

import ks.common.GridPrinter;

import java.util.Collection;

import static ks.othello.OthelloGrid.Color;

public interface OthelloPrinter extends GridPrinter {
    OthelloLocation printUserQuery(Color currentColor, Collection<OthelloLocation> validMove, OthelloLocation result);

    void printTips(Collection<OthelloLocation> validMove);

    void warnNoValidInput(Color currentColor);

    void printWinner(Color winner, int whiteCount, int blackCount);

    void warnInvalidInput(Color currentColor, OthelloLocation input);
}
