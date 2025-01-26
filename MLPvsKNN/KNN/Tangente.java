class Tangente implements TransferFunction {

    /**
     * Fonction tangente hyperbolique : σ(x) = tanh(x)
     * @param value entrée
     * @return sortie de la fonction tangente hyperbolique
     */
    @Override
    public double evaluate(double value) {
        return Math.tanh(value);
    }

    /**
     * Dérivée de la fonction tangente hyperbolique : σ'(x) = 1 - σ(x)^2
     * @param value résultat de la fonction tangente hyperbolique
     * @return sortie de la dérivée
     */
    @Override
    public double evaluateDer(double value) {
        return 1.0 - value * value;
    }
}
