package Scheduler;

import java.util.Queue;
import java.util.LinkedList;
import Processes.Pcb;
import Processes.PcbData;

public class MultilevelFeedbackQueue extends Scheduler {

    private int NoQueues;
    private long[] timeSlices;
    private Queue<Pcb>[] queues;

    public MultilevelFeedbackQueue(int nq, long[] q)
    {
        this.NoQueues = nq;
        this.timeSlices = q;
        this.queues = new Queue[nq];
        for (int i = 0; i < nq; i++) this.queues[i] = new LinkedList<>();
    }

    @Override
    public Pcb get(int cpuId)
    {
        Pcb ret = null;

        for(int i = 0; i < NoQueues; i++){
            if (!queues[i].isEmpty()){
                ret = queues[i].remove();
            }
        }

        return ret;
    }

    @Override
    public void put(Pcb pcb)
    {
        if (pcb == null) return;

        if (pcb.getPcbData() == null){
            pcb.setPcbData(new PcbData());
        }

        Pcb.ProcessState previousState = pcb.getPreviousState();
        PcbData pcbData = pcb.getPcbData();
        int index;

        if (previousState == Pcb.ProcessState.CREATED) {
            index = pcb.getPriority();
            if (index < 0) index = 0;
            if (index >= this.NoQueues) index = this.NoQueues - 1; // PROVERITI
        } else if (previousState == Pcb.ProcessState.BLOCKED) {
            index = pcbData.getLastQueueIndex();
            if (index > 0) index--;
        } else if (previousState == Pcb.ProcessState.RUNNING) {
            index = pcbData.getLastQueueIndex();
            if (index < NoQueues - 1) index++;
        }else
        {
            index = pcbData.getLastQueueIndex();
        } // PREVSTATE = READY --- > Ne bi trebala da se desi ova situacija

        if (index >= 0 && index < this.NoQueues) {
            pcbData.setLastQueueIndex(index);
            pcb.setPcbData(pcbData);
            pcb.setTimeslice(this.timeSlices[index]);
            queues[index].add(pcb);
        }
    }
}
