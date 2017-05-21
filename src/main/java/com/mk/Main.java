package com.mk;

import com.mk.service.MatchMaker;
import com.mk.service.PlayerProvider;
import com.mk.service.console.ConsoleInputService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Pavel Fursov
 */
public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Application started.");

        PlayerProvider pp = new PlayerProvider();
        MatchMaker mm = new MatchMaker();
        ConsoleInputService cih = new ConsoleInputService();

        ExecutorService ex = Executors.newFixedThreadPool(4);
        Future<?> console = ex.submit(cih);
        ex.submit(pp);
        ex.submit(mm);

        ex.shutdown();

        console.get();

        pp.stop();
        mm.stop();

        ex.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

        System.out.println("Terminated.");
    }
}
