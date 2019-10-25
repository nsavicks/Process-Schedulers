package Scheduler;

import Processes.*;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public abstract class Scheduler {

    public abstract Pcb get(int cpuId);
    public abstract void put(Pcb pcb);
    public static Scheduler createScheduler(String[] args)
    {
        if (args.length <= 0) return null;

        String command = args[0];

        switch (command) {
            case "SJF":
                double coef = Double.parseDouble(args[1]);
                int pre = Integer.parseInt(args[2]);
                return new ShortestJobFirst(coef, pre);
            case "MFQ":
                int nq = Integer.parseInt(args[1]);
                long[] slices = new long[nq];
                for (int i = 0; i < nq; i++) {
                    slices[i] = Long.parseLong(args[i + 2]);
                }
                return new MultilevelFeedbackQueue(nq, slices);
            case "CFS":
                return new CompletelyFairScheduler();
            default:
                return null;
        }
    }

    public static void main(String[] params)
    {

    }
}