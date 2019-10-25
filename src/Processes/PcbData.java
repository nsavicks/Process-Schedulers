package Processes;

public class PcbData {

    private long nextExecutionEstimation = 0; // SJF
    private int lastQueueIndex;  // MFQ
    private long executionStart; // CFS
    private long executionDuration; // CFS
    private long waitingStart; // CFS

    public long getNextExecutionEstimation()
    {
        return nextExecutionEstimation;
    }

    public void setNextExecutionEstimation(long nextExecutionEstimation)
    {
        this.nextExecutionEstimation = nextExecutionEstimation;
    }

    public int getLastQueueIndex()
    {
        return lastQueueIndex;
    }

    public void setLastQueueIndex(int lastQueueIndex)
    {
        this.lastQueueIndex = lastQueueIndex;
    }

    public long getExecutionStart()
    {
        return executionStart;
    }

    public void setExecutionStart(long executionStart)
    {
        this.executionStart = executionStart;
    }

    public long getExecutionDuration()
    {
        return executionDuration;
    }

    public void setExecutionDuration(long executionDuration)
    {
        this.executionDuration = executionDuration;
    }

    public long getWaitingStart()
    {
        return waitingStart;
    }

    public void setWaitingStart(long waitingStart)
    {
        this.waitingStart = waitingStart;
    }
}
