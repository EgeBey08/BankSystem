package BankSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class System{
    public static void main(String[] args) throws IOException{
        String currentDir = "/home/egecik/Masaüstü/JavaDersleri/BankSystem";// example/..../example/BankSystem/
        String accountInfoPath = currentDir + "AccountInfo.txt";
        String transferInfoPath = currentDir + "TransferInfo.txt";

        int numberOfAccounts = numberOflines(accountInfoPath); 
        int[] acctNums = new int[numberOfAccounts];
        String[] names = new String[numberOfAccounts];
        String[] surnames = new String[numberOfAccounts];
        double[] balances = new double[numberOfAccounts];

        int numberOfTransfers = numberOflines(transferInfoPath);
        int[] transferIdList = new int[numberOfTransfers];
        int[] acctNumFromList = new int[numberOfTransfers];
        int[] acctNumToList = new int[numberOfTransfers];
        double[] amountlist = new double[numberOfTransfers];

        readAccountInfo(acctNums, names, surnames, balances, accountInfoPath);
        readTransferInfo(transferIdList, acctNumFromList, acctNumToList, amountlist, transferInfoPath);
        makeAllTransfers(transferIdList, acctNumFromList, acctNumToList, amountlist, acctNums, balances, currentDir + "System.log");
        writeAccountInfo(acctNums, names, surnames, balances, currentDir + "AccountInfoOut.txt");
    }
    static int numberOflines(String filePath) throws FileNotFoundException{
        Scanner scan = new Scanner(new File(filePath));
        int count = 0;
        while(scan.hasNextLine()){
            scan.nextLine();
            count ++;
        }
        scan.close();
        return count;
    }
    static void readAccountInfo(int[] acctNums, String[] names, String[] surnames, double[] balances, String filePath) throws FileNotFoundException{
        Scanner scan = new Scanner(new File(filePath));
        int count = 0;
        while(scan.hasNextLine()){
            acctNums[count] = scan.nextInt();
            names[count] = scan.next();
            surnames[count] = scan.next();
            balances[count] = scan.nextDouble();
            count ++;
        }
        scan.close();
    }
    static void readTransferInfo(int[] idList, int[] acctNumFromList, int[] acctNumToList, double[] amountList, String filePath) throws FileNotFoundException{
        Scanner scan = new Scanner(new File(filePath));
        int count = 0;
        while(scan.hasNextLine()){
            String id = scan.next();
            idList[count] = Integer.parseInt(id.substring(id.length()-2));
            acctNumFromList[count] = scan.nextInt();
            acctNumToList[count] = scan.nextInt();
            amountList[count] = scan.nextDouble();
            count ++;
        }
        scan.close();
    }
    static boolean deposit(double[] balances, int index, double amount){
        try{
            balances[index] += amount;
            return true;
        }catch(InputMismatchException exception){
            return false;
        }
    }
    static boolean withdrawal(double[] balances, int index, double amount){
        try{
            if((balances[index] - amount) >= 0){
                balances[index] -= amount;
                return true;
            }
            return false;
        }catch(InputMismatchException exception){
            return false;
        }
    }
    static int indexOf(int[] arr, int element){
        int index = 0;
        for(int item : arr){
            if(item == element){
                return index;
            }
            index ++;
        }
        return 666;
    }
    static int transfer(int[] acctNums, double[] balances, int acctNumFrom, int acctNumTo, double amount){
        if(indexOf(acctNums, acctNumTo) == 666){
            return 1;
        }else if(indexOf(acctNums, acctNumFrom) == 666){
            return 2;
        }else if(!withdrawal(balances, indexOf(acctNums, acctNumFrom), amount)){
            return 3;
        }else{
            // succesful
            withdrawal(balances, indexOf(acctNums, acctNumFrom), amount);
            deposit(balances, indexOf(acctNums, acctNumTo), amount);
            return 0;
        }
    }
    static void makeAllTransfers(int[] transferIds, int[] acctNumFromList, int[] acctNumToList, double[] amountList, int[] acctNums, double[] balances, String filePath) throws IOException{
        FileWriter writer = new FileWriter(new File(filePath));
        for(int i = 0; i < transferIds.length; i++){
            String response = transferResponse(transfer(acctNums, balances, acctNumFromList[i], acctNumToList[i], amountList[i]), transferIds[i]);
            writer.write(response);
        }
        writer.close();
    }
    static String transferResponse(int transferCase, int transferId){
        switch(transferCase){
            case 0:
                return String.format("Transfer TR%s resulted in code 0: TransferSuccesful\n", transferId);
            case 1:
                return String.format("Transfer TR%s resulted in code 1: To Account not found\n", transferId);
            case 2:
                return String.format("Transfer TR%s resulted in code 2: From Account not found\n", transferId);
            case 3:
                return String.format("Transfer TR%s resulted in code 3: Insufficient Funds\n", transferId);
        }
        return null;
    }
    static void writeAccountInfo(int[] acctNums, String[] names, String[] surnames, double[] balances, String filePath) throws IOException{
        FileWriter writer = new FileWriter(new File(filePath));
        int count = 0;
        while(count < acctNums.length){
            writer.write(String.format("%s %s %s %s\n", acctNums[count], names[count], surnames[count], balances[count]));
            count ++;
        }
        writer.close();
    }
}