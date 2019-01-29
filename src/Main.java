import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        ProcessList processList = new ProcessList();
        ArrayList<Process> processArrayList = processList.getProcessedArrayList();
        int processQuanta = processList.getPerProcessQuanta();
        int numberOfProcess = processList.getNumberOfProcess();
        ShortestJobFirst shortestJobFirst = new ShortestJobFirst(processArrayList, numberOfProcess);
        shortestJobFirst.SJF();

        System.out.print("SJF "+String.format("%.2f",shortestJobFirst.getWaitingTime())+" "+String.format("%.2f",shortestJobFirst.getTurnAroundTime())+" "+shortestJobFirst.getTotalFaults()+" ");
        for(Integer integer: shortestJobFirst.getPageFaultList())
        {
            System.out.print(integer+" ");
        }
        //System.out.println(String.format("%.2f",(Math.abs(firstComeFirstService.getTurnAroundTime()-firstJob.getTurnAroundTime())/firstJob.getTurnAroundTime())*100.00));

    }
}
