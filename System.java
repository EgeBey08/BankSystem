package BankSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class System{
    public static void main(String[] args) throws FileNotFoundException{
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
}