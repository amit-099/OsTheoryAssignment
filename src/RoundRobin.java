import java.util.ArrayList;
import java.util.PriorityQueue;

public class RoundRobin {
    private ArrayList<Process> processArrayList;
    private ArrayList<Integer> pageFaultList = new ArrayList<>();

    private int numberOfProcess;
    private int blockedTime;
    private int turnAroundTime;
    private int waitingTime;
    private int totalTime;
    private int totalFaults;
    private int processQuanta;

    RoundRobin(ArrayList<Process> processArrayList, int numberOfProcess, int processQuanta) {
        this.processArrayList = processArrayList;
        this.numberOfProcess = numberOfProcess;
        this.processQuanta = processQuanta;
        this.blockedTime = 0;
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

    public int getBlockedTime() {
        return blockedTime;
    }

    public void setBlockedTime(int blockedTime) {
        this.blockedTime = blockedTime;
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

    private int getExecTime(Process process, int aProcessQuanta) {
        int execTime = 0;
        int[] pageRef = process.getPageRefs();
        ProcessOperation operation = new ProcessOperation(process);

        int frameID = operation.getFrameID(pageRef[process.alreadyExecutedIdx]);

        while(frameID != -1 && process.alreadyExecutedIdx < pageRef.length && aProcessQuanta > 0) {
            operation.fixFramePosition(frameID);

            int execTempTime = Math.min(aProcessQuanta, process.processQuanta[process.alreadyExecutedIdx]);

            execTime += execTempTime;
            aProcessQuanta -= execTempTime;
            process.processQuanta[process.alreadyExecutedIdx] -= execTempTime;

            if(process.processQuanta[process.alreadyExecutedIdx] <= 0)
                process.alreadyExecutedIdx += 1;

            if(process.alreadyExecutedIdx < pageRef.length) {
                frameID = operation.getFrameID(pageRef[process.alreadyExecutedIdx]);
            }
        }
        return execTime;
    }

    public void RRS() {
        PriorityQueue<Process> processPriorityQueue = new PriorityQueue<>(new ArrivalTimeCompRRS());

        for(Process process: processArrayList) {
            processPriorityQueue.add(process);
            turnAroundTime -= process.getProcessArrivalTime();
            waitingTime -= new ProcessOperation(process).getExecutionTime();
        }

        int[] aPageRef;
        int[] returnfault;
        while(!processPriorityQueue.isEmpty()) {
            Process aProcess = processPriorityQueue.peek();
            ProcessOperation processOperation = new ProcessOperation(aProcess);
            aPageRef = aProcess.getPageRefs();

            if(aProcess.alreadyExecutedIdx >= aProcess.getPageRefs().length) {
                turnAroundTime += totalTime;
                processPriorityQueue.remove();
            } else {
                if(aProcess.getProcessArrivalTime() > totalTime) totalTime = aProcess.getProcessArrivalTime();

                if(processOperation.getFrameID(aPageRef[aProcess.alreadyExecutedIdx]) == -1) {
                    returnfault = new FirstComeFirstServe().handlePageFault(aProcess, processPriorityQueue, processOperation, totalFaults, blockedTime, totalTime);
                    totalFaults = returnfault[0];
                    blockedTime = returnfault[1];
                } else {
                    int execTime = getExecTime(aProcess, processQuanta);
                    totalTime += execTime;

                    if(execTime < processQuanta) {
                        if(aProcess.alreadyExecutedIdx >= aPageRef.length) {
                            processPriorityQueue.remove();
                            turnAroundTime += totalTime;
                        }
                    } else {
                        processPriorityQueue.remove();
                        if(aProcess.alreadyExecutedIdx >= aPageRef.length) {
                            turnAroundTime += totalTime;
                        } else {
                            aProcess.processArrivalTime = totalTime;
                            processPriorityQueue.add(aProcess);
                        }
                    }
                }
            }
        }
        waitingTime += turnAroundTime;
        for(Process process: processArrayList) {
            pageFaultList.add(process.pageFault);
        }
    }
}
