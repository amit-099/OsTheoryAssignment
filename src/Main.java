import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        ProcessList processList_fcfs = new ProcessList();
        ArrayList<Process> processArrayList_fcfs = processList_fcfs.getProcessedArrayList();
        int numberOfProcess = processList_fcfs.getNumberOfProcess();
        FirstComeFirstServe firstComeFirstServe = new FirstComeFirstServe(processArrayList_fcfs, numberOfProcess);
        firstComeFirstServe.FCFS();

        System.out.print("FCFS ");
        double gain_fcfs = ((firstComeFirstServe.getTurnAroundTime() - firstComeFirstServe.getTurnAroundTime()) / firstComeFirstServe.getTurnAroundTime()) * 100.00;
        printFunction(firstComeFirstServe.getWaitingTime(), firstComeFirstServe.getTurnAroundTime(), firstComeFirstServe.getTotalFaults(), firstComeFirstServe.getPageFaultList(), gain_fcfs);


        ProcessList processList_sjf = new ProcessList();
        ArrayList<Process> processArrayList_sjf = processList_sjf.getProcessedArrayList();
        numberOfProcess = processList_sjf.getNumberOfProcess();
        ShortestJobFirst shortestJobFirst = new ShortestJobFirst(processArrayList_sjf, numberOfProcess);
        shortestJobFirst.SJF();

        System.out.print("SJF ");
        double gain_sjf = ((firstComeFirstServe.getTurnAroundTime() - shortestJobFirst.getTurnAroundTime()) / shortestJobFirst.getTurnAroundTime()) * 100.00;
        printFunction(shortestJobFirst.getWaitingTime(), shortestJobFirst.getTurnAroundTime(), shortestJobFirst.getTotalFaults(), shortestJobFirst.getPageFaultList(), gain_sjf);


        ProcessList processList_rrs = new ProcessList();
        ArrayList<Process> processArrayList_rrs = processList_rrs.getProcessedArrayList();
        int processQuanta = processList_rrs.getPerProcessQuanta();
        numberOfProcess = processList_rrs.getNumberOfProcess();
        RoundRobin roundRobin = new RoundRobin(processArrayList_rrs, numberOfProcess, processQuanta);
        roundRobin.RRS();

        System.out.print("RRS ");
        double gain_rrs = ((firstComeFirstServe.getTurnAroundTime() - roundRobin.getTurnAroundTime()) / roundRobin.getTurnAroundTime()) * 100.00;
        printFunction(roundRobin.getWaitingTime(), roundRobin.getTurnAroundTime(), roundRobin.getTotalFaults(), roundRobin.getPageFaultList(), gain_rrs);
    }

    private static void printFunction(double waitingTime, double turnAround, int totalFault, ArrayList<Integer> faultList, double gain) {
        System.out.printf("%.2f, %.2f, ", waitingTime, turnAround);
        System.out.print(totalFault);
        for(Integer integer: faultList) {
            System.out.print(", " + integer);
        }
        System.out.printf(", %.2f\n", gain);
    }
}
