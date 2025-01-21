package ia.algo.jeux;

import ia.framework.common.Action;
import ia.framework.common.ActionValuePair;
import ia.framework.jeux.Game;
import ia.framework.jeux.GameState;
import ia.framework.jeux.Player;

public class AlphaBetaPlayer extends Player {

        public AlphaBetaPlayer(Game g, boolean p1, int depth) {
            super(g, p1);
        }

        @Override
        public Action getMove(GameState state) {
            ActionValuePair val;

            if (this.player == PLAYER1) {
                val = maxValue(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            } else {
                val = minValue(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            }
            return val.getAction();
        }

        private ActionValuePair maxValue(GameState state, double alpha, double beta) {
            if (game.endOfGame(state)) {
                return new ActionValuePair(null, state.getGameValue());
            }
            double v_max = Double.NEGATIVE_INFINITY;
            Action c_max = null;

            for (Action c : game.getActions(state)) {
                GameState s_suivant = (GameState) game.doAction(state, c);
                double v = minValue(s_suivant, alpha, beta).getValue();
                if (v >= v_max) {
                    v_max = v;
                    c_max = c;
                }

                if (v_max > alpha) {
                    alpha = v_max;
                }
                if (v_max >= beta) {
                    return new ActionValuePair(c_max, v_max);
                }
            }
            return new ActionValuePair(c_max, v_max);
        }

        private ActionValuePair minValue(GameState state, double alpha, double beta) {
            if (game.endOfGame(state)) {
                return new ActionValuePair(null, state.getGameValue());
            }
            double v_min = Double.POSITIVE_INFINITY;
            Action c_min = null;

            for (Action c : game.getActions(state)) {
                GameState s_suivant = (GameState) game.doAction(state, c);
                double v = maxValue(s_suivant, alpha, beta).getValue();
                if (v <= v_min) {
                    v_min = v;
                    c_min = c;
                }
                if (v_min < beta) {
                    beta = v_min;
                }
                if (v_min <= alpha) {
                    return new ActionValuePair(c_min, v_min);
                }
            }
            return new ActionValuePair(c_min, v_min);
        }
}
