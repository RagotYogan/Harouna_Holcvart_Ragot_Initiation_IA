package ia.algo.jeux;

import ia.framework.common.Action;
import ia.framework.common.ActionValuePair;
import ia.framework.jeux.Game;
import ia.framework.jeux.GameState;
import ia.framework.jeux.Player;



public class MinMaxPlayer extends Player {

    public MinMaxPlayer(Game g, boolean p1,int depth) {
        super(g, p1);
    }

    @Override
    public Action getMove(GameState state) {
        ActionValuePair val;
        if (this.player == PLAYER1) {
            val = maxValue(state);
        } else {
            val = minValue(state);
        }
        return val.getAction();
    }

    private ActionValuePair maxValue(GameState state) {
        if (game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }
        double v_max = Double.NEGATIVE_INFINITY;
        Action c_max = null;

        for (Action c : game.getActions(state)) {
            GameState s_suivant = (GameState) game.doAction(state, c);
            double v = minValue(s_suivant).getValue();
            if (v >= v_max) {
                v_max = v;
                c_max = c;
            }
        }
        return new ActionValuePair(c_max, v_max);
    }



    private ActionValuePair minValue(GameState state) {
        if (game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }
        double v_min = Double.POSITIVE_INFINITY;
        Action c_min = null;

        for (Action c : game.getActions(state)) {
            GameState s_suivant = (GameState) game.doAction(state, c);
            double v = maxValue(s_suivant).getValue();
            if (v <= v_min) {
                v_min = v;
                c_min = c;
            }
        }
        return new ActionValuePair(c_min, v_min);
    }

}

