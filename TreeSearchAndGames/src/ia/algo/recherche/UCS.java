package ia.algo.recherche;

import ia.framework.common.Action;
import ia.framework.common.State;
import ia.framework.recherche.SearchNode;
import ia.framework.recherche.SearchProblem;
import ia.framework.recherche.TreeSearch;

import java.util.*;

public class UCS extends TreeSearch {

    public UCS(SearchProblem problem, State initialState) {
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
            // Trouver et retirer le nœud avec le coût cumulatif le plus bas
            SearchNode minNode = null;
            for (SearchNode node : frontier) {
                if (minNode == null || node.getCost() < minNode.getCost()) {
                    minNode = node;
                }
            }
            frontier.remove(minNode);

            State state = minNode.getState();

            // Vérifier si l'état actuel est un état but
            if (problem.isGoalState(state)) {
                end_node = minNode;
                return true;
            }

            // Ajouter l'état à l'ensemble des explorés
            explored.add(state);

            // Générer les enfants du nœud actuel
            for (Action action : problem.getActions(state)) {
                SearchNode child = SearchNode.makeChildSearchNode(problem, minNode, action);
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
                    } else if (child.getCost() < existingNode.getCost()) {
                        frontier.remove(existingNode);
                        frontier.add(child);
                    }
                }
            }
        }

        return false; // Aucun chemin trouvé vers un état but
    }
}
