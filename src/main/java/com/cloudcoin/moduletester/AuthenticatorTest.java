package com.cloudcoin.moduletester;

import global.cloudcoin.ccbank.Authenticator.Authenticator;
import global.cloudcoin.ccbank.Authenticator.AuthenticatorResult;
import global.cloudcoin.ccbank.core.CallbackInterface;
import global.cloudcoin.ccbank.core.ServantRegistry;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public class AuthenticatorTest {

    private static String RootPath = ModuleTester.RootPath;
    static String TestCoinName = "100.CloudCoin.1.6377545..stack";
    ServantRegistry sr;
    int TestNumber = 1;


    public AuthenticatorTest(ServantRegistry sri) {
        createDirectories();
        sr = sri;
        Instant start = Instant.now();
        StartAuthenticatorTest();
        Instant end = Instant.now();
        System.out.println("Three Tests,Time Elapsed: " + Duration.between(start, end).toMillis() + "ms");
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
            Files.createDirectories(Paths.get(RootPath + "Detected\\"));
            Files.createDirectories(Paths.get(RootPath + "Suspect\\"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void StartAuthenticatorTest() {
        System.out.println();

        System.out.println("Starting test for Authenticator");
        System.out.println("Emptying Detected and Suspect Folder");
        //TestUtils.FlushFolder("Suspect");
        //TestUtils.FlushFolder("Detected");

            try {

                switch (TestNumber) {
                    case 1:
                        System.out.println("1. Authenticate 1 CloudCoin (Counterfeit)");
                        TestUtils.saveFile(makeCloudCoinCounterfeit(1), 1, "Suspect");
                        break;
                    case 2:
                        System.out.println("2. Authenticate 10 CloudCoins (Counterfeit)");
                        for (int i = 0; i < 10; i++)
                            TestUtils.saveFile(makeCloudCoinCounterfeit(2 + i), 2 + i, "Suspect");
                        break;
                    case 3:
                        System.out.println("3. Authenticate 100 CloudCoins (Counterfeit)");
                        for (int i = 0; i < 100; i++)
                            TestUtils.saveFile(makeCloudCoinCounterfeit(100 + i), 100 + i, "Suspect");
                        break;

                    case 4:
                        System.out.println("All Tests Complete. Clearing out Folders used in testing.");
                        //TestUtils.FlushFolder("Detected");
                        //TestUtils.FlushFolder("Suspect");
                        return;
                }
                Authenticator au = (Authenticator) sr.getServant("Authenticator");
                au.launch(new AuthenticatorAssert());
            } catch (Exception e) {
                System.out.println("Uncaught exception - " + e.getLocalizedMessage());
                e.printStackTrace();
            }

    }

    class AuthenticatorAssert implements CallbackInterface {
        public void callback(final Object result) {
            final Object fresult = result;
            AuthenticatorResult ar = (AuthenticatorResult) fresult;
            boolean AllCoinsDetected = true;
            switch(TestNumber){
                case 1:
                    if(Files.exists(Paths.get(RootPath + "Detected\\" + TestUtils.getDenomination(1) + ".CloudCoin.1." + 1 + ".stack"))) {
                        System.out.println("TEST 1 SUCCESS");
                    }
                    else{
                        System.out.println("TEST 1 FAILED: Test coin not found in Detected folder.");
                    }
                    break;
                case 2:
                    for (int j = 0; j < 10; j++)
                        if(!Files.exists(Paths.get(RootPath + "Detected\\" + TestUtils.getDenomination(2 + j) + ".CloudCoin.1." + (2+j) + ".stack")))
                            AllCoinsDetected = false;
                    if(AllCoinsDetected) {
                        System.out.println("TEST 2 SUCCESS");
                    }
                    else{
                        System.out.println("TEST 2 FAILED: a Test coin not found in Detected folder.");
                    }
                    break;
                case 3:
                    for (int j = 0; j < 100; j++)
                        if(!Files.exists(Paths.get(RootPath + "Detected\\" + TestUtils.getDenomination(100 + j) + ".CloudCoin.1." + (100+j) + ".stack")))
                            AllCoinsDetected = false;
                    if(AllCoinsDetected) {
                        System.out.println("TEST 3 SUCCESS");
                    }
                    else{
                        System.out.println("TEST 3 FAILED: a Test coin not found in Detected folder.");
                    }
                    break;
            }

            TestNumber++;
            StartAuthenticatorTest();
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
                "      \"pown\": \"uuuuuuuuuuuuuuuuuuuuuuuuu\",\n" +
                "      \"aoid\": []\n" +
                "    }\n" +
                "  ]\n" +
                "}").getBytes();
    }

}
