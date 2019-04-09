package com.cloudcoin.moduletester;

import global.cloudcoin.ccbank.ShowCoins.ShowCoinsResult;
import global.cloudcoin.ccbank.ShowCoins.ShowCoins;
import global.cloudcoin.ccbank.core.CallbackInterface;
import global.cloudcoin.ccbank.core.ServantRegistry;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public class ShowCoinsTest {

    private static String RootPath = ModuleTester.RootPath;
    private static String CommandFolder = RootPath + "Command\\";
    private static String LogsPath = ModuleTester.LogsPath + "ShowCoins\\";
    ServantRegistry sr;
    int TestNumber = 1;


    public ShowCoinsTest(ServantRegistry sri) {
        sr = sri;
        createDirectories();
        //setup
        TestUtils.FlushFolder("Bank");
        TestUtils.FlushFolder("Gallery");
        TestUtils.FlushFolder("Lost");
        TestUtils.FlushFolder("Fracked");
        //Instant start = Instant.now();
        StartShowCoinsTest();
        //Instant end = Instant.now();
        //System.out.println("Four Tests,Time Elapsed: " + Duration.between(start, end).toMillis() + "ms");

    }

    public static void setRootPath(String[] args) {
        if (args == null || args.length == 0)
            return;

        if (Files.isDirectory(Paths.get(args[0]))) {
            RootPath = args[0];
        }
    }

    public static void createDirectories() {
        try {
            Files.createDirectories(Paths.get(RootPath));
            Files.createDirectories(Paths.get(LogsPath));
            Files.createDirectories(Paths.get(RootPath + "Bank\\"));
            Files.createDirectories(Paths.get(RootPath + "Fracked\\"));
			Files.createDirectories(Paths.get(RootPath + "Lost\\"));
			Files.createDirectories(Paths.get(RootPath + "Gallery\\"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void StartShowCoinsTest() {

            try {


                switch(TestNumber){
                    case 1:
                        System.out.println("Testing Show 1 CloudCoin (Bank)");
                        TestUtils.saveFile(makeCloudCoinCounterfeit(1), 1, "Bank");
                        break;
                    case 2:
                        System.out.println("Testing Show 10 CloudCoins (Fracked)");
                        for (int i = 0; i < 5; i++)
                            TestUtils.saveFile(makeCloudCoinCounterfeit(1 + i), 1 + i, "Fracked");
                        TestUtils.saveFile(makeCloudCoinCounterfeit(2097154), 2097154, "Fracked");
                        break;
                    case 3:
                        System.out.println("Testing Show 100 CloudCoins (Lost)");
                        TestUtils.saveFile(makeCloudCoinCounterfeit(6291458), 6291458, "Lost");
                        break;
                    case 4:
                        System.out.println("Testing Show 1000 CloudCoins (Bank)");
                        TestUtils.FlushFolder("Bank");
                        for (int i = 0; i < 4; i++)
                            TestUtils.saveFile(makeCloudCoinCounterfeit(14680066 + i), 14680066 + i, "Bank");
                        break;
                    case 5:
                        System.out.println("All Tests Complete. Clearing out Folders used in testing.");
                        TestUtils.FlushFolder("Bank");
                        TestUtils.FlushFolder("Gallery");
                        TestUtils.FlushFolder("Lost");
                        TestUtils.FlushFolder("Fracked");
                        return;
                }

                //execute
                ShowCoins sc = (ShowCoins) sr.getServant("ShowCoins");
                sc.launch(new ShowCoinsAssert());
            } catch (Exception e) {
                System.out.println("Uncaught exception - " + e.getLocalizedMessage());
                e.printStackTrace();
            }
    }
/*
stem.out.println("Testing Show Gallery");
                if(Files.exists(Paths.get(LogsPath + "Gallery.false.txt")))
                System.out.println("TEST 4 SUCCESSFUL");
                else
                System.out.println("No Log file found. TEST FAILED");
*/

    class ShowCoinsAssert implements CallbackInterface {
        public void callback(final Object result) {
            final Object fresult = result;
            ShowCoinsResult scresult = (ShowCoinsResult) fresult;

            switch(TestNumber){
                case 1:
                    if(Files.exists(Paths.get(LogsPath + "Bank_1_1_0_0_0_0.txt")))
                        System.out.println("TEST 1 SUCCESSFUL");
                    else
                        System.out.println("No Log file found. TEST FAILED");
                    break;
                case 2:
                    if(Files.exists(Paths.get(LogsPath + "Fracked_10_5_1_0_0_0.txt")))
                        System.out.println("TEST 2 SUCCESSFUL");
                    else
                        System.out.println("No Log file found. TEST FAILED");
                    break;
                case 3:
                    if(Files.exists(Paths.get(LogsPath + "Lost_100_0_0_0_1_0.txt")))
                        System.out.println("TEST 3 SUCCESSFUL");
                    else
                        System.out.println("No Log file found. TEST FAILED");
                    break;
                case 4:
                    if(Files.exists(Paths.get(LogsPath + "Bank_1000_0_0_0_0_4.txt")))
                        System.out.println("TEST 4 SUCCESSFUL");
                    else
                        System.out.println("No Log file found. TEST FAILED");
                    break;
            }
            TestNumber++;
            StartShowCoinsTest();
        }
    }

    public static byte[] makeCloudCoinCounterfeit(int sn) {
        return ("{\n" +
                "  \"cloudcoin\": [\n" +
                "    {\n" +
                "      \"nn\": 1,\n" +
                "      \"sn\": " + sn + ",\n" +
                "      \"an\": [\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\"\n" +
                "      ],\n" +
                "      \"ed\": \"11-2020\",\n" +
                "      \"pown\": \"fffffffffffffffffffffffff\",\n" +
                "      \"aoid\": []\n" +
                "    }\n" +
                "  ]\n" +
                "}").getBytes();
    }

}
