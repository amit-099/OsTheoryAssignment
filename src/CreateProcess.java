import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CreateProcess {
    private String input;

    CreateProcess(String input) {
        this.input = input;
    }

    public Process inputProcessing() {
        String[] temp = input.split(",");
        int ID = Integer.parseInt(temp[0]);
        int numberOfPages = Integer.parseInt(temp[1]);
        int arrivalTime = Integer.parseInt(temp[2]);

        int[] memoryRefs = new int[temp.length - 3];
        int[] pageRefs = new int[memoryRefs.length];
        int[] processQuanta = new int[pageRefs.length];

        for(int i = 3; i < temp.length; i++) memoryRefs[i - 3] = Integer.parseInt(temp[i]);

        int frameArrayLength = (int) Math.ceil((double) numberOfPages / 3.0);
        int[] LRUFrameArray = new int[frameArrayLength];

        for(int i = 0; i < memoryRefs.length; i++) pageRefs[i] = memoryRefs[i] / 512;

        for(int i = 0; i < processQuanta.length; i++) processQuanta[i] = 30;

        for(int i = 0; i < frameArrayLength; i++) LRUFrameArray[i] = -1;

        return new Process(ID, arrivalTime, numberOfPages, memoryRefs, pageRefs, processQuanta, LRUFrameArray, frameArrayLength);
    }
}

class ProcessList {
    private ArrayList<Process> processArrayList = new ArrayList<>();
    private int numberOfProcess;
    private int perProcessQuanta;

    public ProcessList(ArrayList<Process> processArrayList, int numberOfProcess, int perProcessQuanta) {
        this.processArrayList = processArrayList;
        this.numberOfProcess = numberOfProcess;
        this.perProcessQuanta = perProcessQuanta;
    }

    ProcessList() {}

    public ArrayList<Process> getProcessedArrayList() throws FileNotFoundException {
        FileReader fileReader = new FileReader("input_file");
        Scanner scanner = new Scanner(fileReader);
        StringTokenizer stringTokenizer = new StringTokenizer(scanner.nextLine().replace(" ", ""), ",");
        numberOfProcess = Integer.parseInt(stringTokenizer.nextToken());
        perProcessQuanta = Integer.parseInt(stringTokenizer.nextToken());
        while(scanner.hasNext()) {
            Process aProcess = new CreateProcess(scanner.nextLine().replace(", ", ",")).inputProcessing();
            processArrayList.add(aProcess);
        }
        return processArrayList;
    }

    public void setProcessArrayList(ArrayList<Process> processArrayList) {
        this.processArrayList = processArrayList;
    }

    public int getNumberOfProcess() {
        return numberOfProcess;
    }

    public void setNumberOfProcess(int numberOfProcess) {
        this.numberOfProcess = numberOfProcess;
    }

    public int getPerProcessQuanta() {
        return perProcessQuanta;
    }

    public void setPerProcessQuanta(int perProcessQuanta) {
        this.perProcessQuanta = perProcessQuanta;
    }
}
