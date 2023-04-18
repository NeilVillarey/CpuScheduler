package org.example;

import java.util.ArrayList;
import java.util.List;

public abstract class Scheduler {
    protected List<Process> processes;
    protected List<Process> scheduledProcesses;

    public Scheduler() {
        this.processes = new ArrayList<>();
        this.scheduledProcesses = new ArrayList<>();
    }

    public void addProcess(Process p) {
        processes.add(p);
    }

    public abstract void schedule();

    public abstract void printProcessStats();
}
