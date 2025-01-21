package ia.algo.recherche;

import ia.framework.common.Action;
import ia.framework.common.State;
import ia.framework.recherche.HasHeuristic;
import ia.framework.recherche.SearchNode;
import ia.framework.recherche.SearchProblem;
import ia.framework.recherche.TreeSearch;

import java.util.LinkedList;
import java.util.HashSet;

public class GFS extends TreeSearch {

    public GFS(SearchProblem problem, State initialState) {
        super(problem, initialState);
    }

    @Override
    public boolean solve() {
        // Initialise la frontière comme une liste
        frontier = new LinkedList<>();
        frontier.add(SearchNode.makeRootSearchNode(initial_state));

        // Ensemble des états explorés
        explored = new HashSet<>();

        while (!frontier.isEmpty()) {
            // Trouver et retirer le nœud avec la plus petite heuristique
            SearchNode bestNode = null;
            for (SearchNode node : frontier) {
                if (bestNode == null || node.getHeuristic() < bestNode.getHeuristic()) {
                    bestNode = node;
                }
            }
            frontier.remove(bestNode);

            State currentState = bestNode.getState();

            // Vérifie si l'état courant est un état but
            if (problem.isGoalState(currentState)) {
                end_node = bestNode;
                return true;
            }

            // Ajoute l'état courant à l'ensemble des explorés
            explored.add(currentState);

            for (Action action : problem.getActions(currentState)) {
                SearchNode child = SearchNode.makeChildSearchNode(problem, bestNode, action);
                State childState = child.getState();

                // Vérifie si l'état enfant est déjà exploré ou dans la frontière
                if (!explored.contains(childState)) {
                    if (childState instanceof HasHeuristic) {
                        frontier.add(child);
                    }
                }
            }
        }

        return false; // Aucun chemin trouvé vers un état but
    }
}
