package ia.algo.recherche;

import ia.framework.common.Action;
import ia.framework.common.State;
import ia.framework.recherche.SearchNode;
import ia.framework.recherche.SearchProblem;
import ia.framework.recherche.TreeSearch;

import java.util.LinkedList;
import java.util.HashSet;

public class AStar extends TreeSearch {

    public AStar(SearchProblem problem, State initialState) {
        super(problem, initialState);
    }

    @Override
    public boolean solve() {
        // Initialiser la frontière comme une liste
        frontier = new LinkedList<>();
        frontier.add(SearchNode.makeRootSearchNode(initial_state));

        // Ensemble des états explorés
        explored = new HashSet<>();

        while (!frontier.isEmpty()) {
            // Trouver et retirer le nœud avec le coût total f(n) le plus bas
            SearchNode bestNode = null;
            for (SearchNode node : frontier) {
                double nodeCost = node.getCost() + node.getHeuristic();
                double bestNodeCost = (bestNode == null) ? Double.MAX_VALUE : bestNode.getCost() + bestNode.getHeuristic();
                if (nodeCost < bestNodeCost) {
                    bestNode = node;
                }
            }
            frontier.remove(bestNode);

            State currentState = bestNode.getState();

            // Vérifier si on a atteint l'état but
            if (problem.isGoalState(currentState)) {
                end_node = bestNode;
                return true;
            }

            // Ajouter l'état courant à l'ensemble des explorés
            explored.add(currentState);

            // Générer les enfants du nœud courant
            for (Action action : problem.getActions(currentState)) {
                SearchNode child = SearchNode.makeChildSearchNode(problem, bestNode, action);
                State childState = child.getState();

                // Vérifier si l'état enfant est déjà exploré ou dans la frontière
                boolean inFrontier = false;
                SearchNode existingNode = null;

                for (SearchNode node : frontier) {
                    if (node.getState().equals(childState)) {
                        inFrontier = true;
                        existingNode = node;
                        break;
                    }
                }

                if (!explored.contains(childState)) {
                    if (!inFrontier) {
                        frontier.add(child);
                    } else if ((child.getCost() + child.getHeuristic()) < (existingNode.getCost() + existingNode.getHeuristic())) {
                        frontier.remove(existingNode);
                        frontier.add(child);
                    }
                }
            }
        }

        return false; // Aucun chemin trouvé
    }
}
