package org.example;

import java.util.Comparator;
import java.util.PriorityQueue;
public class PreemptiveSJFScheduler extends Scheduler {

    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        int currentTime = 0;
        int completedProcesses = 0;
        int processIndex = 0;
        Process currentProcess = null;
        PriorityQueue<Process> minHeap = new PriorityQueue<>(Comparator.comparingInt(Process::getRemainingTime));

        while (completedProcesses < processes.size()) {
            while (processIndex < processes.size() && processes.get(processIndex).getArrivalTime() <= currentTime) {
                if (currentProcess != null) {
                    minHeap.add(currentProcess);
                    currentProcess = null;
                }
                minHeap.add(processes.get(processIndex));
                processIndex++;
            }

            if (currentProcess != null && currentProcess.getRemainingTime() == 0) {
                currentProcess.setCompletionTime(currentTime);
                currentProcess.setTurnaroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
                currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
                scheduledProcesses.add(currentProcess);
                completedProcesses++;
                currentProcess = null;
            }

            if (currentProcess == null && !minHeap.isEmpty()) {
                currentProcess = minHeap.poll();
            }

            if (currentProcess != null) {
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);
                if (currentProcess.getRemainingTime() == 0) {
                    minHeap.remove(currentProcess);
                }
            }

            currentTime++;
        }
    }

    @Override
    public void printProcessStats() {
        System.out.println("Preemptive SJF Scheduler");
        System.out.println("PID\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (Process p : scheduledProcesses) {
            System.out.println(p.getPid() + "\t" + p.getCompletionTime() + "\t\t" + p.getTurnaroundTime() + "\t\t" + p.getWaitingTime());
        }
    }
}
