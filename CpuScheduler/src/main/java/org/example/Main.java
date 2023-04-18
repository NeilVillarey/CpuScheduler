package org.example;

public class Main {
    public static void main(String[] args) {

        // Create processes
        Process p1 = new Process(1, 0, 6,1);
        Process p2 = new Process(2, 1, 4,2);
        Process p3 = new Process(3, 2, 9,3);
        Process p4 = new Process(4, 3, 3,4);
        Process p5 = new Process(5, 4, 5,5);
        // Create schedulers
        NonPreemptiveSJFScheduler nonPreemptiveScheduler = new NonPreemptiveSJFScheduler();
        PreemptiveSJFScheduler preemptiveScheduler = new PreemptiveSJFScheduler();
        NonPreemptivePriorityScheduler nonPreemptiveSPcheduler = new NonPreemptivePriorityScheduler();
        // Add processes to schedulers
        nonPreemptiveScheduler.addProcess(p1);
        nonPreemptiveScheduler.addProcess(p2);
        nonPreemptiveScheduler.addProcess(p3);
        nonPreemptiveScheduler.addProcess(p4);
        nonPreemptiveScheduler.addProcess(p5);


        preemptiveScheduler.addProcess(p1);
        preemptiveScheduler.addProcess(p2);
        preemptiveScheduler.addProcess(p3);
        preemptiveScheduler.addProcess(p4);
        preemptiveScheduler.addProcess(p5);

        nonPreemptiveSPcheduler.addProcess(p1);
        nonPreemptiveSPcheduler.addProcess(p2);
        nonPreemptiveSPcheduler.addProcess(p3);
        nonPreemptiveSPcheduler.addProcess(p4);
        nonPreemptiveSPcheduler.addProcess(p5);

        // Schedule processes
        nonPreemptiveScheduler.schedule();
        preemptiveScheduler.schedule();
        nonPreemptiveSPcheduler.schedule();
        // Print process stats
      //  System.out.println("Non-Preemptive SJF Scheduler");
        nonPreemptiveScheduler.printProcessStats();

     //   System.out.println("\nPreemptive SJF Scheduler");
        preemptiveScheduler.printProcessStats();

       // System.out.println("\nNon-Preemptive Priority Scheduler");
        nonPreemptiveSPcheduler.printProcessStats();


    }
}

