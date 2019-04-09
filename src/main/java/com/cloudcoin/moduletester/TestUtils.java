package com.cloudcoin.moduletester;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

public class TestUtils {
    public static void printLines(String cmd, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(cmd + " " + line);
        }
    }

    public static void runProcess(String command) throws Exception{
        Process pro = Runtime.getRuntime().exec(command);
        printLines(command + " stdout:", pro.getInputStream());
        printLines(command + " stderr:", pro.getErrorStream());
        pro.waitFor();
        System.out.println(command + " exitValue() " + pro.exitValue());
    }

    public static void FlushFolder(String folder) {

        File file = new File(ModuleTester.RootPath +File.separator + folder);
        try {
            if (file == null || !file.exists())
                return;

            File[] listFiles = file.listFiles();
            if (listFiles != null)
                for (File childFile : listFiles) {
                    if (childFile.isDirectory()) {
                        FlushFolder(folder + File.separator + childFile.getName());
                    }
                        if (!childFile.delete()) {
                            throw new IOException();
                        }

                }
        }
        catch(IOException e){
            System.out.println("Uncaught exception - " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
/*
    public static void FlushSpecificFolder(String folder) {
        try{
            Files.newDirectoryStream(Paths.get(folder + "\\")).forEach(file ->{
                if(Files.isDirectory(file))
                    FlushSpecificFolder(file.toString());
                try{Files.deleteIfExists(file);}catch (IOException e){
                    System.out.println("Uncaught exception - " + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            });
        }
        catch(IOException e){
            System.out.println("Uncaught exception - " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
    */

    public static String saveFile(byte[] cloudCoin, int sn, String folder) throws IOException {
        String filename = ensureFilenameUnique(getDenomination(sn) + ".CloudCoin.1." + sn ,".stack", ModuleTester.RootPath + folder + "\\");
        Files.write(Paths.get(ModuleTester.RootPath + folder + "\\" + filename), cloudCoin);
        return filename;
    }

    public static String saveDummyFile(String folder) throws IOException {
        byte[] dummyData = "0000000000000".getBytes();
        String filename = ensureFilenameUnique("TestDummyFile",".txt", folder + "\\");
        Files.write(Paths.get(folder + "\\" + filename), dummyData);
        return filename;
    }

    public static String saveFile(byte[] cloudCoin, int sn, String folder, String extension) throws IOException {
        String filename = ensureFilenameUnique(getDenomination(sn) + ".CloudCoin.1." + sn,extension, ModuleTester.RootPath + folder + "\\");
        Files.write(Paths.get(ModuleTester.RootPath + folder + "\\" + filename), cloudCoin);
        return filename;

    }


    public static String ensureFilenameUnique(String filename, String extension, String folder) {
        if (!Files.exists(Paths.get(folder + filename + extension)))
            return filename + extension;

        filename = filename + '.';
        String newFilename;
        int loopCount = 0;
        do {
            newFilename = filename + Integer.toString(++loopCount);
        }
        while (Files.exists(Paths.get(folder + newFilename + extension)));
        return newFilename + extension;
    }

    public static int getDenomination(int sn) {
        int nom;
        if (sn < 1)
            nom = 0;
        else if ((sn < 2097153))
            nom = 1;
        else if ((sn < 4194305))
            nom = 5;
        else if ((sn < 6291457))
            nom = 25;
        else if ((sn < 14680065))
            nom = 100;
        else if ((sn < 16777217))
            nom = 250;
        else
            nom = 0;

        return nom;
    }
    public static String[] selectFileNamesInFolder(String folderPath) {
        File folder = new File(folderPath);
        Collection<String> files = new ArrayList<>();
        if (folder.isDirectory()) {
            File[] filenames = folder.listFiles();

            if (null != filenames) {
                for (File file : filenames) {
                    if (file.isFile()) {
                        files.add(file.getName());
                    }
                }
            }
        }
        return files.toArray(new String[]{});
    }
}
