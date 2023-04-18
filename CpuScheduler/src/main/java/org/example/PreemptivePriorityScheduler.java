package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PreemptivePriorityScheduler extends Scheduler {

    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        int currentTime = processes.stream().mapToInt(Process::getArrivalTime).min().orElse(0);
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getPriority));

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).getArrivalTime() <= currentTime) {
                Process arrivingProcess = processes.remove(0);
                readyQueue.add(arrivingProcess);
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                int remainingTime = currentProcess.getRemainingTime();
                boolean isPreempted = false;

                for (Process p : processes) {
                    if (p.getArrivalTime() > currentTime && p.getArrivalTime() < currentTime + remainingTime) {
                        if (p.getPriority() < currentProcess.getPriority()) {
                            int timeSlice = p.getArrivalTime() - currentTime;
                            currentProcess.setRemainingTime(remainingTime - timeSlice);
                            remainingTime = timeSlice;
                            readyQueue.add(currentProcess);
                            isPreempted = true;
                            break;
                        }
                    }
                }

                if (!isPreempted) {
                    currentProcess.setRemainingTime(0);
                }

                int completionTime = currentTime + remainingTime;
                currentProcess.setCompletionTime(completionTime);
                currentProcess.setTurnaroundTime(completionTime - currentProcess.getArrivalTime());
                currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
                scheduledProcesses.add(currentProcess);
                currentTime = completionTime;
            } else {
                currentTime++;
            }
        }
    }

    @Override
    public void printProcessStats() {
        System.out.println("Preemptive Priority Scheduler");
        System.out.println("PID\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (Process p : scheduledProcesses) {
            System.out.println(p.getPid() + "\t" + p.getCompletionTime() + "\t\t" + p.getTurnaroundTime() + "\t\t" + p.getWaitingTime());
        }
    }
}
