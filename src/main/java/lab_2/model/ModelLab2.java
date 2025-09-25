package lab_2.model;

public class ModelLab2 {

    int numberOfBugDetected;//число обнаруженных ошибок
    double[] bugTime; //Массив интервалов времени появления ошибок.
    int numberBug; //Общее число ошибок в программе.
    double propCoeff;//коэффициент пропорциональности
    double averageTimeToBug; //среднее время до появления ошибки
    double timeEndTesting;//время до окончания тестирования
    int LoverPointer; //Нижняя граница
    int UpperPointer; //Верхняя граница
    double epsilon; //Погрешность

    public int getLoverPointer() {
        return LoverPointer;
    }

    public void setLoverPointer(int loverPointer) {
        LoverPointer = loverPointer;
    }

    public int getUpperPointer() {
        return UpperPointer;
    }

    public void setUpperPointer(int upperPointer) {
        UpperPointer = upperPointer;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

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
}
