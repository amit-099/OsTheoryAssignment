import java.util.Comparator;

public class ProcessOperation {
    private Process aProcess;
    private int[] frameArray;
    private int[] pageRef;

    public ProcessOperation() {}

    ProcessOperation(Process aProcess) {
        this.aProcess = aProcess;
        frameArray = aProcess.getLRUFrameArray();
        pageRef = aProcess.getPageRefs();
    }

    public void fixFramePosition(int currentIdx) {
        for(int i = currentIdx; i > 0; i--) {
            int temp = frameArray[i];
            frameArray[i] = frameArray[i - 1];
            frameArray[i - 1] = temp;
        }
    }

    public int getFrameID(int aPageID) {
        for(int i = 0; i < aProcess.getFrameArrayLength(); i++) if(frameArray[i] == aPageID) return i;
        return -1;
    }

    public int findFreeSlot() {
        return aProcess.getFrameArrayLength() - 1;
    }

    public int LRU() {
        aProcess.pageFault = 0;
        for(int i = 0; i < aProcess.getPageRefs().length; i++) {
            int frameID = getFrameID(pageRef[i]);
            if(frameID == -1) {
                aProcess.pageFault += 1;
                frameArray[findFreeSlot()] = pageRef[i];
                fixFramePosition(findFreeSlot());
            } else {
                fixFramePosition(frameID);
            }
        }
        return aProcess.pageFault;
    }

    public int getExecutionTime() {
        return aProcess.getMemoryRefs().length * 30;
    }

    public int getPageFaultTime() {
        return aProcess.pageFault * 60;
    }
}

class ArrivalTimeComp implements Comparator<Process> {

    @Override
    public int compare(Process p1, Process p2) {
        if(p1.getProcessArrivalTime() == p2.getProcessArrivalTime())
            return (new ProcessOperation(p1).getExecutionTime() + new ProcessOperation(p1).getPageFaultTime()) -
                    (new ProcessOperation(p2).getExecutionTime() + new ProcessOperation(p2).getPageFaultTime());
        else return p1.getProcessArrivalTime() - p2.getProcessArrivalTime();
    }
}

class ExecutionTimeComp implements Comparator<Process> {

    @Override
    public int compare(Process p1, Process p2) {
        return (new ProcessOperation(p1).getExecutionTime() + new ProcessOperation(p1).getPageFaultTime()) -
                (new ProcessOperation(p2).getExecutionTime() + new ProcessOperation(p2).getPageFaultTime());
    }
}

class ArrivalTimeCompFCFS implements Comparator<Process> {

    @Override
    public int compare(Process p1, Process p2) {
        return p1.getProcessArrivalTime() - p2.getProcessArrivalTime();
    }
}

class ArrivalTimeCompRRS implements Comparator<Process> {

    @Override
    public int compare(Process p1, Process p2) {
        if(p1.getProcessArrivalTime() >= p2.getProcessArrivalTime()) return 1;
        else return p1.getProcessArrivalTime() - p2.getProcessArrivalTime();
    }
}
