package org.example;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class RoundRobinScheduler extends Scheduler {

    private int timeQuantum;

    public RoundRobinScheduler(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        int currentTime = 0;
        int completedProcesses = 0;
        int processIndex = 0;
        Queue<Process> readyQueue = new LinkedList<>();
        int timeSlice = 0;

        while (completedProcesses < processes.size()) {
            while (processIndex < processes.size() && processes.get(processIndex).getArrivalTime() <= currentTime) {
                readyQueue.add(processes.get(processIndex));
                processIndex++;
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();

                timeSlice = Math.min(timeQuantum, currentProcess.getRemainingTime());
                currentTime += timeSlice;
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - timeSlice);

                if (currentProcess.getRemainingTime() == 0) {
                    currentProcess.setCompletionTime(currentTime);
                    currentProcess.setTurnaroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
                    scheduledProcesses.add(currentProcess);
                    completedProcesses++;
                } else {
                    readyQueue.add(currentProcess);
                }
            } else {
                if (processIndex < processes.size()) {
                    currentTime = processes.get(processIndex).getArrivalTime();
                } else {
                    break;
                }
            }
        }
    }

    @Override
    public void printProcessStats() {
        System.out.println("Round Robin Scheduler");
        System.out.println("PID\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (Process p : scheduledProcesses) {
            System.out.println(p.getPid() + "\t" + p.getCompletionTime() + "\t\t" + p.getTurnaroundTime() + "\t\t" + p.getWaitingTime());
        }
    }
}
