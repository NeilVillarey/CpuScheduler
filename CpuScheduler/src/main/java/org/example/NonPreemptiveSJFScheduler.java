package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NonPreemptiveSJFScheduler extends Scheduler {

    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        int currentTime = 0;
        List<Process> remainingProcesses = new ArrayList<>(processes);

        while (!remainingProcesses.isEmpty()) {
            // Find the process with the shortest burst time that has arrived
            Process shortestJob = null;
            for (Process p : remainingProcesses) {
                if (p.getArrivalTime() <= currentTime) {
                    if (shortestJob == null || p.getBurstTime() < shortestJob.getBurstTime()) {
                        shortestJob = p;
                    }
                }
            }

            if (shortestJob != null) {
                currentTime += shortestJob.getBurstTime();
                shortestJob.setCompletionTime(currentTime);
                shortestJob.setTurnaroundTime(shortestJob.getCompletionTime() - shortestJob.getArrivalTime());
                shortestJob.setWaitingTime(shortestJob.getTurnaroundTime() - shortestJob.getBurstTime());

                scheduledProcesses.add(shortestJob);
                remainingProcesses.remove(shortestJob);
            } else {
                currentTime++;
            }
        }
    }

    @Override
    public void printProcessStats() {
        System.out.println("Non Preemptive SJF Scheduler");
        System.out.println("PID\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (Process p : scheduledProcesses) {
            System.out.println(p.getPid() + "\t" + p.getCompletionTime() + "\t\t" + p.getTurnaroundTime() + "\t\t" + p.getWaitingTime());
        }
    }
}
