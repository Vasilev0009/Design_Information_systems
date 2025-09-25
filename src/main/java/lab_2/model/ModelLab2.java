package lab_2.model;

public class ModelLab2 {

    int numberOfBugDetected;//число обнаруженных ошибок
    double[] bugTime; //Массив интервалов времени появления ошибок.
    int numberBug; //Общее число ошибок в программе.
    double propCoeff;//коэффициент пропорциональности
    double averageTimeToBug; //среднее время до появления ошибки
    double timeEndTesting;//время до окончания тестирования

    public ModelLab2() {
    }

    public int getNumberBug() {
        return numberBug;
    }

    public void setTimeEndTesting(double timeEndTesting) {
        this.timeEndTesting = timeEndTesting;
    }

    public void setNumberBug(int numberBug) {
        this.numberBug = numberBug;
    }

    public double getTimeEndTesting() {
        return timeEndTesting;
    }

    public int getNumberOfBugDetected() {
        return numberOfBugDetected;
    }

    public void setNumberOfBugDetected(int numberOfBugDetected) {
        this.numberOfBugDetected = numberOfBugDetected;
    }

    public double[] getBugTime() {
        return bugTime;
    }

    public void setBugTime(double[] bugTime) {
        this.bugTime = bugTime;
    }

    public double getPropCoeff() {
        return propCoeff;
    }

    public void setPropCoeff(double propCoeff) {
        this.propCoeff = propCoeff;
    }

    public double getAverageTimeToBug() {
        return averageTimeToBug;
    }

    public void setAverageTimeToBug(double averageTimeToBug) {
        this.averageTimeToBug = averageTimeToBug;
    }

    public int getN() {
        return 0;
    }

    public void setXi(double[] clone) {
    }

    public void setN(int length) {
    }

    public void setB(double v) {
    }

    public int getB() {
        return 0;
    }

    public double[] getXi() {
        return new double[0];
    }

    public void setK(double v) {
    }

    public double getK() {
        return 0;
    }

    public void setXNext(double xNext) {
    }

    public void setTEnd(double sum) {
    }

    public Object getXNext() {
        return null;
    }

    public Object getTEnd() {
        return null;
    }
}

