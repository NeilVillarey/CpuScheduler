package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NonPreemptivePriorityScheduler extends Scheduler {

    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        int currentTime = 0;
        List<Process> remainingProcesses = new ArrayList<>(processes);

        while (!remainingProcesses.isEmpty()) {
            // Find the process with the highest priority (lowest priority value) that has arrived
            Process highestPriorityProcess = null;
            for (Process p : remainingProcesses) {
                if (p.getArrivalTime() <= currentTime) {
                    if (highestPriorityProcess == null || p.getPriority() < highestPriorityProcess.getPriority()) {
                        highestPriorityProcess = p;
                    }
                }
            }

            if (highestPriorityProcess != null) {
                currentTime += highestPriorityProcess.getBurstTime();
                highestPriorityProcess.setCompletionTime(currentTime);
                highestPriorityProcess.setTurnaroundTime(highestPriorityProcess.getCompletionTime() - highestPriorityProcess.getArrivalTime());
                highestPriorityProcess.setWaitingTime(highestPriorityProcess.getTurnaroundTime() - highestPriorityProcess.getBurstTime());

                scheduledProcesses.add(highestPriorityProcess);
                remainingProcesses.remove(highestPriorityProcess);
            } else {
                currentTime++;
            }
        }
    }

    @Override
    public void printProcessStats() {
        System.out.println("Non-Preemptive Priority Scheduler");
        System.out.println("PID\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (Process p : scheduledProcesses) {
            System.out.println(p.getPid() + "\t" + p.getCompletionTime() + "\t\t" + p.getTurnaroundTime() + "\t\t" + p.getWaitingTime());
        }
    }
}
