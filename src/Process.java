import java.util.Arrays;

public class Process {
    private int processID;
    public int processArrivalTime;
    private int numberOfPages;
    private int memoryRefs[];
    private int pageRefs[];
    public int processQuanta[];
    private int LRUFrameArray[];
    private int frameArrayLength;
    public int pageFault = 0;
    public int alreadyExecutedIdx;

    Process(int processID, int processArrivalTime, int numberOfPages, int[] memoryRefs, int[] pageRefs, int[] processQuanta, int[] LRUFrameArray, int frameArrayLength) {
        this.processID = processID;
        this.processArrivalTime = processArrivalTime;
        this.numberOfPages = numberOfPages;
        this.memoryRefs = memoryRefs;
        this.pageRefs = pageRefs;
        this.processQuanta = processQuanta;
        this.LRUFrameArray = LRUFrameArray;
        this.frameArrayLength = frameArrayLength;
        this.alreadyExecutedIdx = 0;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public int getProcessArrivalTime() {
        return processArrivalTime;
    }

    public void setProcessArrivalTime(int processArrivalTime) {
        this.processArrivalTime = processArrivalTime;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int[] getMemoryRefs() {
        return memoryRefs;
    }

    public void setMemoryRefs(int[] memoryRefs) {
        this.memoryRefs = memoryRefs;
    }

    public int[] getPageRefs() {
        return pageRefs;
    }

    public void setPageRefs(int[] pageRefs) {
        this.pageRefs = pageRefs;
    }

    public int[] getProcessQuanta() {
        return processQuanta;
    }

    public void setProcessQuanta(int[] processQuanta) {
        this.processQuanta = processQuanta;
    }

    public int[] getLRUFrameArray() {
        return LRUFrameArray;
    }

    public void setLRUFrameArray(int[] LRUFrameArray) {
        this.LRUFrameArray = LRUFrameArray;
    }

    public int getFrameArrayLength() {
        return frameArrayLength;
    }

    public void setFrameArrayLength(int frameArrayLength) {
        this.frameArrayLength = frameArrayLength;
    }

    @Override
    public String toString() {
        return "Process{" +
                ", processID=" + processID +
                ", processArrivalTime=" + processArrivalTime +
                ", numberOfPages=" + numberOfPages +
                ", memoryRefs=" + Arrays.toString(memoryRefs) +
                ", pageRefs=" + Arrays.toString(pageRefs) +
                ", processQuanta=" + Arrays.toString(processQuanta) +
                ", LRUFrameArray=" + Arrays.toString(LRUFrameArray) +
                ", frameArrayLength=" + frameArrayLength +
                ", pageFault=" + pageFault +
                ", alreadyExecutedIdx=" + alreadyExecutedIdx +
                '}';
    }
}
