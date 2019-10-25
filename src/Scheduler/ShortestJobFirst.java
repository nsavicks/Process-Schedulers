package Scheduler;

import Processes.*;

import java.util.Comparator;
import java.util.PriorityQueue;

public class ShortestJobFirst extends Scheduler {

    private boolean preemptive;
    private double coefficient;
    private PriorityQueue<Pcb> pq;

    public ShortestJobFirst(double coef, int pre)
    {
        this.coefficient = coef;
        this.preemptive = pre != 0;

        pq = new PriorityQueue<>(new Comparator<Pcb>() {
            @Override
            public int compare(Pcb o1, Pcb o2)
            {
                if (o1.getPcbData().getNextExecutionEstimation() < o2.getPcbData().getNextExecutionEstimation()) return -1;
                else return 1;
            }
        });
    }

    @Override
    public Pcb get(int cpuId)
    {
        Pcb ret = pq.poll();

        if (ret != null) {
            ret.setTimeslice(0);
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

        if (!preemptive){
            long nextEstimation = (long) (pcb.getExecutionTime()*coefficient + (1 - coefficient)*pcb.getPcbData().getNextExecutionEstimation());
            pcb.getPcbData().setNextExecutionEstimation(nextEstimation);
            pq.add(pcb);
        }
        else{

        }
    }
}
