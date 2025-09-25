package lab_2.model;

public class ModelLab2 {

    private int numberOfBugDetected; // число обнаруженных ошибок
    private double[] bugTime; // Массив интервалов времени появления ошибок
    private int numberBug; // Общее число ошибок в программе
    private double propCoeff; // коэффициент пропорциональности
    private double averageTimeToBug; // среднее время до появления ошибки
    private double timeEndTesting; // время до окончания тестирования

    // НОВЫЕ ПОЛЯ для лабораторной работы 2
    private int n; // число обнаруженных ошибок (аналогично numberOfBugDetected)
    private double[] xi; // массив времен обнаружения ошибок (аналогично bugTime)
    private double B; // общее число ошибок
    private double K; // коэффициент пропорциональности
    private double xNext; // время до следующей ошибки
    private double tEnd; // время до окончания тестирования


    public ModelLab2() {
    }

    public int getN() {
        return this.n;
    }

    public void setN(int n) {
        this.n = n;
        this.numberOfBugDetected = n; // синхронизируем с существующим полем
    }

    public double[] getXi() {
        return this.xi != null ? this.xi.clone() : new double[0];
    }

    public void setXi(double[] xi) {
        this.xi = xi != null ? xi.clone() : new double[0];
        this.bugTime = this.xi; // синхронизируем с существующим полем
    }

    public double getB() {
        return this.B;
    }

    public void setB(double B) {
        this.B = B;
        this.numberBug = (int) Math.round(B); // синхронизируем с существующим полем
    }

    public double getK() {
        return this.K;
    }

    public void setK(double K) {
        this.K = K;
        this.propCoeff = K; // синхронизируем с существующим полем
    }

    public double getXNext() {
        return this.xNext;
    }

    public void setXNext(double xNext) {
        this.xNext = xNext;
        this.averageTimeToBug = xNext; // синхронизируем с существующим полем
    }

    public double getTEnd() {
        return this.tEnd;
    }

    public void setTEnd(double tEnd) {
        this.tEnd = tEnd;
        this.timeEndTesting = tEnd; // синхронизируем с существующим полем
    }

    // Существующие методы оставляем как есть
    public int getNumberBug() {
        return numberBug;
    }

    public double getTimeEndTesting() {
        return timeEndTesting;
    }

    public int getNumberOfBugDetected() {
        return numberOfBugDetected;
    }

    public void setNumberOfBugDetected(int numberOfBugDetected) {
        this.numberOfBugDetected = numberOfBugDetected;
        this.n = numberOfBugDetected; // синхронизируем
    }

    public double[] getBugTime() {
        return bugTime;
    }

    public void setBugTime(double[] bugTime) {
        this.bugTime = bugTime;
        this.xi = bugTime; // синхронизируем
    }

    public double getPropCoeff() {
        return propCoeff;
    }

    public void setPropCoeff(double propCoeff) {
        this.propCoeff = propCoeff;
        this.K = propCoeff; // синхронизируем
    }

    public double getAverageTimeToBug() {
        return averageTimeToBug;
    }

    public void setAverageTimeToBug(double averageTimeToBug) {
        this.averageTimeToBug = averageTimeToBug;
        this.xNext = averageTimeToBug; // синхронизируем
    }
}

