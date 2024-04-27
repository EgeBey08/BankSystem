package BankSystem;

import java.io.File;
import java.io.FileNotFoundException;
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
}