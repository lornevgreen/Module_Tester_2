package com.cloudcoin.moduletester;

/**
 * ModuleTester is a main method that allows users to decide which test they would
 *Like to run. 
 * @author Sean H. Worthington
 * @author your name here
 * @version 1.1
 */


import global.cloudcoin.ccbank.core.AppCore;
import global.cloudcoin.ccbank.core.Config;
import global.cloudcoin.ccbank.core.ServantRegistry;

import java.io.*;
import java.net.URL;

public class ModuleTester {

    ServantRegistry sr;

    public static String UserPath =  System.getProperty("user.home");
    public static String RootPath = UserPath + "\\CloudCoin\\Accounts\\DefaultUser\\";
    public static String LogsPath = RootPath + "Logs\\";

    public static void main(String[] args){
        new ModuleTester();
    }

    public ModuleTester(){
        greet();
        initSystem();
        while(true)
        {
            executeCommand(getCommand());
        }
    }

    public static void greet(){
        System.out.println("This is the Servant Tester");
        System.out.println("Used to test servant modules");
        System.out.println("As is from the CloudCoin Consortium");
        System.out.println("Last changed: 11/04/2018");
        System.out.println("");
    }//end greeting
    
    public static int getCommand(){
        //List all commands
        String commands[] = {"Quit Tester","Test Echoer","Test Exporter","test backuper","Test Pay-Forward","Test ShowCoinsTest","Test Depositer","Test Minder",
                "Test Eraser","Test Vaulter","Test LossFixer","Test Grader","Test FrackFixer","Test Unpacker","Test Authenticator"};

        System.out.println("Enter the number of the command you wish to execute");
        for(int i= 0; i < commands.length; i++)
        {
            int cn = i ;
            System.out.println(cn+". " + commands[i]);//list each command in the commands array
        }//end list all commands
        KeyboardReader reader = new KeyboardReader();
        int command = reader.readInt(0,commands.length);
        return command; 
    }

    public void executeCommand(int commandNumber ){
        switch(commandNumber)
        {
            case 0: 
            //Execute Quit Program
                System.out.println("Quiting.");
                System.exit(0);
            break;

            case 1: 
            //Execute Test Echoer
               System.out.println("Testing Echoer");
                new EchoerTest(sr);
            break;
            case 2:
                //test  Exporter
                System.out.println("Testing Exporter");
                new ExporterTest(sr);
                break;
            case 3:
                //Execute Quit Program
                System.out.println("Could run other commands.");
                //test back upper
                System.out.println("Testing BackUpper");
                new Backupper();
                break;
            case 4:
                //test  PayForward
                System.out.println("Testing Sender.");
                new Sender();

                break;
            case 5:
                //test  ShowCoins
                System.out.println("Testing ShowCoins.");
                new ShowCoinsTest(sr);
                break;
            case 6:
                //test Depositer
                System.out.println("Testing Receiver.");
                new Receiver();
                break;
            case 7:
                //test Minder
                System.out.println("Testing Minder.");
                new Minder();
                break;
            case 8:
                //test Emailer
                System.out.println("Testing Eraser.");
                new Eraser();
                break;
            case 9:
                //test Vaulter
                System.out.println("Testing Vaulter.");
                new Vaulter();
                break;
            case 10:
                //test LossFixer
                System.out.println("Testing LossFixer.");
                new LossFixer();
                break;
            case 11:
                //test Grader
                System.out.println("Testing Grader.");
                new GraderTest(sr);
                break;
            case 12:
                //test Translator
                System.out.println("Testing FrackFixer");
                new FrackFixerTest(sr);
                break;
            case 13:
                //test Translator
                System.out.println("Testing Unpacker");
                new UnpackerTest(sr);
                break;
            case 14:
                //test Authenticator
                System.out.println("Testing Authenticator");
                new AuthenticatorTest(sr);
                break;
            default:
                System.out.println("Error running command. Please try again. ");
        }//end switch

        //end execute Command
    }//end execute Command

    public  void initSystem() {
        WLogger wl;
        wl = new WLogger();

        AppCore.initPool();

        try {
            String home = System.getProperty("user.home");
            AppCore.initFolders(new File(home), wl);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }


        int d;
        String templateDir, fileName;

        templateDir = AppCore.getUserDir(Config.DIR_TEMPLATES);
        fileName = templateDir + File.separator + "jpeg1.jpg";
        File f = new File(fileName);
        if (!f.exists()) {
            System.out.println("xxx!!!");
            for (int i = 0; i < AppCore.getDenominations().length; i++) {
                d = AppCore.getDenominations()[i];
                try {
                    fileName = "jpeg" + d + ".jpg";

                    copyFile(fileName, AppCore.getUserDir(Config.DIR_TEMPLATES) + File.separator + fileName);
                } catch (IOException e) {
                    System.out.println("ERROR copying templates" + e.getMessage());
                }
            }
        }

        sr = new ServantRegistry();
        sr.registerServants(new String[]{
                "Echoer",
                "Authenticator",
                "ShowCoins",
                "Unpacker",
                "Authenticator",
                "Grader",
                "FrackFixer",
                "Exporter"
        }, AppCore.getRootPath(), wl);

    }

    public  void copyFile(String src, String dst) throws IOException {
        URL u = getClass().getClassLoader().getResource("resources/" + src);

        if (u == null)
            return;

        InputStream in = new FileInputStream(u.getFile());
        OutputStream out = new FileOutputStream(dst);

        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0)
            out.write(buf, 0, len);

        in.close();
        out.close();
    }
}//end ModuleTester

