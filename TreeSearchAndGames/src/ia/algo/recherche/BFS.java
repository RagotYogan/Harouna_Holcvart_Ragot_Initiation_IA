package ia.algo.recherche;

import ia.framework.common.Action;
import ia.framework.common.State;
import ia.framework.recherche.SearchNode;
import ia.framework.recherche.SearchProblem;
import ia.framework.recherche.TreeSearch;

import java.util.LinkedList;

public class BFS extends TreeSearch {

    public BFS(SearchProblem prob, State inital_state) {
        super(prob, inital_state);
    }

    @Override
    public boolean solve() {
        // Initialiser de FiFo
        frontier = new LinkedList<>();
        frontier.add(SearchNode.makeRootSearchNode(initial_state));

        // Ensemble des états visités
        while (!frontier.isEmpty()) {
            // Récupération du premier nœud de la file
            SearchNode node = frontier.poll();
            State state = node.getState();

            if (problem.isGoalState(state)) {
                end_node = node;
                return true;
            }

            explored.add(state);

            for (Action action : problem.getActions(state)) {
                SearchNode child = SearchNode.makeChildSearchNode(problem, node, action);
                State childState = child.getState();

                if (!explored.contains(childState)) {
                    frontier.add(child);
                }
            }
        }

        return false;
    }
}
