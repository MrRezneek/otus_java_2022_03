package ru.otus.testprocessor;

public class TestStatistic {
    private int successfulCount;
    private int errorCount;
    private int allCount;

    public TestStatistic(int successfulCount, int errorCount, int allCount) {
        this.successfulCount = successfulCount;
        this.errorCount = errorCount;
        this.allCount = allCount;
    }

    public int getSuccessfulCount() {
        return successfulCount;
    }

    public void setSuccessfulCount(int successfulCount) {
        this.successfulCount = successfulCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }
}
