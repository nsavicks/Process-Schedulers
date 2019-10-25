package Scheduler;

import Processes.Pcb;
import Processes.PcbData;

import java.util.Comparator;
import java.util.PriorityQueue;

public class CompletelyFairScheduler extends Scheduler {

    private PriorityQueue<Pcb> pq;

    public CompletelyFairScheduler()
    {
        this.pq = new PriorityQueue<>(new Comparator<Pcb>() {
            @Override
            public int compare(Pcb o1, Pcb o2)
            {
                if (o1.getPcbData().getExecutionDuration() < o2.getPcbData().getExecutionDuration()) return -1;
                else return 1;
            }
        });
    }

    @Override
    public Pcb get(int cpuId)
    {
        Pcb ret = pq.poll();

        if (ret != null) {
            long currentTime = Pcb.getCurrentTime();
            long waitingTime = currentTime - ret.getPcbData().getWaitingStart();
            long timeSlice = waitingTime / (long) Pcb.getProcessCount();
            ret.setTimeslice(timeSlice);
            ret.getPcbData().setExecutionStart(currentTime);
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

        PcbData pcbData = pcb.getPcbData();
        Pcb.ProcessState state = pcb.getPreviousState();
        long currentTime = Pcb.getCurrentTime();

        if (state == Pcb.ProcessState.BLOCKED || state == Pcb.ProcessState.CREATED){
            pcbData.setExecutionDuration(0);
        }
        else if (state == Pcb.ProcessState.RUNNING){
            long current = pcbData.getExecutionDuration();
            current += currentTime - pcbData.getExecutionStart();

            pcbData.setExecutionDuration(current);
        }

        pcbData.setWaitingStart(currentTime);
        pq.add(pcb);
    }
}
