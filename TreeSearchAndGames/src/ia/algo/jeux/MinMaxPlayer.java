package ia.algo.jeux;

import ia.framework.common.Action;
import ia.framework.common.ActionValuePair;
import ia.framework.jeux.Game;
import ia.framework.jeux.GameState;
import ia.framework.jeux.Player;



public class MinMaxPlayer extends Player {
    private int profondeurMax;
    private int profondeur;

    public MinMaxPlayer(Game g, boolean p1,int valueOfParam) {
        super(g, p1);
        name = "minmax";
        profondeurMax = valueOfParam;
    }

    @Override
    public Action getMove(GameState state) {
        ActionValuePair actionValuePair;
        profondeur = 0;
        if (player==PLAYER1){
            actionValuePair = maxValue(state);
        } else {
            actionValuePair = minValue(state);
        }
        return actionValuePair.getAction();
    }

    private ActionValuePair maxValue(GameState s){
        this.incStateCounter();
        if (game.endOfGame(s) || profondeur >= profondeurMax){
            return new ActionValuePair(null, s.getGameValue());
        }
        double vMax = Double.NEGATIVE_INFINITY;
        Action aMax = null;
        GameState sprime;
        for(Action a : game.getActions(s)){
            sprime = (GameState) game.doAction(s, a);
            profondeur ++;
            ActionValuePair vAprime = minValue(sprime);
            if(vAprime.getValue() >= vMax){
                vMax = vAprime.getValue();
                aMax = a;
            }
        }
        return new ActionValuePair(aMax, vMax);
    }

    private ActionValuePair minValue(GameState s){
        this.incStateCounter();
        if (game.endOfGame(s) || profondeur >= profondeurMax){
            return new ActionValuePair(null, s.getGameValue());
        }
        double vMin =  Double.POSITIVE_INFINITY;
        Action aMin = null;
        GameState sprime;
        for(Action a : game.getActions(s)){
            sprime = (GameState) game.doAction(s, a);
            profondeur ++;
            ActionValuePair vAprime = maxValue(sprime);
            if(vAprime.getValue() <= vMin){
                vMin = vAprime.getValue();
                aMin = a;
            }
        }
        return new ActionValuePair(aMin, vMin);
    }
}

