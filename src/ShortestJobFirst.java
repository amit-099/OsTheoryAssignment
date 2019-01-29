import java.util.*;

public class ShortestJobFirst {
    private ArrayList<Process> processArrayList;
    private ArrayList<Integer> pageFaultList = new ArrayList<>();

    private int numberOfProcess;
    private int executionTime;
    private int turnAroundTime;
    private int waitingTime;
    private int totalTime;
    private int totalFaults;

    ShortestJobFirst(ArrayList<Process> processArrayList, int numberOfProcess) {
        this.processArrayList = processArrayList;
        this.numberOfProcess = numberOfProcess;
        this.executionTime = 0;
        this.turnAroundTime = 0;
        this.waitingTime = 0;
        this.totalTime = 0;
        this.totalFaults = 0;
    }

    public ArrayList<Process> getProcessArrayList() {
        return processArrayList;
    }

    public void setProcessArrayList(ArrayList<Process> processArrayList) {
        this.processArrayList = processArrayList;
    }

    public ArrayList<Integer> getPageFaultList() {
        return pageFaultList;
    }

    public void setPageFaultList(ArrayList<Integer> pageFaultList) {
        this.pageFaultList = pageFaultList;
    }

    public int getNumberOfProcess() {
        return numberOfProcess;
    }

    public void setNumberOfProcess(int numberOfProcess) {
        this.numberOfProcess = numberOfProcess;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public double getTurnAroundTime() {
        return (double)turnAroundTime / (double)numberOfProcess;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public double getWaitingTime() {
        return (double)waitingTime / (double)numberOfProcess;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getTotalFaults() {
        return totalFaults;
    }

    public void setTotalFaults(int totalFaults) {
        this.totalFaults = totalFaults;
    }

    private void updateThings(PriorityQueue<Process> processPriorityQueue) {
        executionTime += new ProcessOperation(processPriorityQueue.peek()).getExecutionTime() + new ProcessOperation(processPriorityQueue.peek()).getPageFaultTime();
        totalTime += new ProcessOperation(processPriorityQueue.peek()).getExecutionTime() + new ProcessOperation(processPriorityQueue.peek()).getPageFaultTime();
        waitingTime += totalTime - processPriorityQueue.peek().getProcessArrivalTime() - new ProcessOperation(processPriorityQueue.peek()).getExecutionTime();
        turnAroundTime += totalTime - processPriorityQueue.peek().getProcessArrivalTime();
        processPriorityQueue.remove();
    }

    public void SJF() {
        for (Process aProcess : processArrayList) {
            ProcessOperation processOperation = new ProcessOperation(aProcess);
            int faults = processOperation.LRU();
            pageFaultList.add(faults);
            totalFaults += faults;
        }

        processArrayList.sort(new ArrivalTimeComp());
        PriorityQueue<Process> processPriorityQueue = new PriorityQueue<>(new ExecutionTimeComp());

        processPriorityQueue.add(processArrayList.get(0));
        totalTime += processArrayList.get(0).getProcessArrivalTime();

        int i = 1;
        while (i < processArrayList.size()) {
            while(i < processArrayList.size() && processArrayList.get(i).getProcessArrivalTime() <= totalTime) {
                processPriorityQueue.add(processArrayList.get(i));
                i++;
            }
            updateThings(processPriorityQueue);

            if(i < processArrayList.size() && processPriorityQueue.isEmpty() && processArrayList.get(i).getProcessArrivalTime() > totalTime)
                totalTime = processArrayList.get(i).getProcessArrivalTime();
        }

        while(!processPriorityQueue.isEmpty()) {
            updateThings(processPriorityQueue);
        }
    }
}
