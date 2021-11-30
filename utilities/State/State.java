package utilities.State;


import game.G_Manager;

public abstract class State {
        G_Manager manager;

        State(G_Manager manager) {
            this.manager = manager;
        }

        public abstract String pause();
        public abstract String resume();
}

