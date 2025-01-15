class Sigmoid implements TransferFunction {

    /**
     * Fonction sigmoïde : σ(x) = 1 / (1 + e^(-x))
     * @param value entrée
     * @return sortie de la fonction sigmoïde
     */
    @Override
    public double evaluate(double value) {
        return 1.0 / (1.0 + Math.exp(-value));
    }

    /**
     * Dérivée de la fonction sigmoïde : σ'(x) = σ(x) * (1 - σ(x))
     * @param value résultat de la fonction sigmoïde
     * @return sortie de la dérivée
     */
    @Override
    public double evaluateDer(double value) {
        return value * (1.0 - value);
    }
}
