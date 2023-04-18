package org.example;

import java.util.Comparator;

public class FCFSScheduler extends Scheduler {

    @Override
    public void schedule() {
        // Sort processes by arrival time
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        int currentTime = 0;

        for (Process p : processes) {
            if (currentTime < p.getArrivalTime()) {
                currentTime = p.getArrivalTime();
            }
            currentTime += p.getBurstTime();
            p.setCompletionTime(currentTime);
            p.setTurnaroundTime(p.getCompletionTime() - p.getArrivalTime());
            p.setWaitingTime(p.getTurnaroundTime() - p.getBurstTime());

            scheduledProcesses.add(p);
        }
    }

    @Override
    public void printProcessStats() {
        System.out.println("PID\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (Process p : scheduledProcesses) {
            System.out.println(p.getPid() + "\t" + p.getCompletionTime() + "\t\t" + p.getTurnaroundTime() + "\t\t" + p.getWaitingTime());
        }
    }
}
