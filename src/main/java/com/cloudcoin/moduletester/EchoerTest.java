package com.cloudcoin.moduletester;

import global.cloudcoin.ccbank.Echoer.Echoer;
import global.cloudcoin.ccbank.core.CallbackInterface;
import global.cloudcoin.ccbank.core.ServantRegistry;

import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;
import java.time.Instant;

public class EchoerTest {
    private static String CommandsFolder = ModuleTester.RootPath + "Command\\";
    ServantRegistry sr;

    public  EchoerTest(ServantRegistry sri){
        sr = sri;
        Instant start = Instant.now();
        StartEchoerTest();
        Instant end = Instant.now();
        System.out.println("One Tests,Time Elapsed: " + Duration.between(start, end).toMillis() + "ms");
    }

    public void StartEchoerTest(){
        try {

            Echoer e = (Echoer) sr.getServant("Echoer");
            e.launch(new EchoerAsser());

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    class EchoerAsser implements CallbackInterface{
        public void callback(final Object result){
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(ModuleTester.LogsPath + "Echoer\\")))
            {
                for(Path p: stream)
                {
                    String logfilename = p.getFileName().toString();
                    if(logfilename.contains("ready"))
                        System.out.println("Found log for Raida: " + logfilename.split("_")[0]);
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
/*
    public static void saveCommand(byte[] command) throws IOException {
        String filename = TestUtils.ensureFilenameUnique("echoer"// + LocalDateTime.now().format(timestampFormat)
                , ".command", CommandsFolder);
        Files.createDirectories(Paths.get(CommandsFolder));
        Files.write(Paths.get(CommandsFolder + filename), command);
    }

    public static byte[] makeCommand() {
        return ("{\n" +
                "  \"command\": \"echo\"\n" +


                "}").getBytes();
    }
*/
}
