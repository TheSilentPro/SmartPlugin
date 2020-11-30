package tsp.smartplugin.util.tasker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import tsp.smartplugin.SmartPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Utility class for running a {@link Runnable}
 *
 * @author TheSilentPro
 */
public class Tasker {

    public static final BukkitScheduler SCHEDULER = Bukkit.getScheduler();
    private static final JavaPlugin plugin = SmartPlugin.getInstance();

    // SYNC
    public static BukkitTask sync(Runnable task) {
        return SCHEDULER.runTask(plugin, task);
    }

    public static List<BukkitTask> sync(Runnable... tasks) {
        List<BukkitTask> result = new ArrayList<>();
        Arrays.stream(tasks).forEach(task -> result.add(sync(task)));

        return result;
    }

    public static BukkitTask syncTimer(Runnable task, long delay, long repeat) {
        return SCHEDULER.runTaskTimer(plugin, task, delay, repeat);
    }

    public static BukkitTask syncTimer(Runnable task, long repeat) {
        return syncTimer(task, 0L, repeat);
    }

    public static List<BukkitTask> syncTimer(long delay, long repeat, Runnable... tasks) {
        List<BukkitTask> result = new ArrayList<>();
        Arrays.stream(tasks).forEach(task -> result.add(syncTimer(task, delay, repeat)));

        return result;
    }

    public static List<BukkitTask> syncTimer(long repeat, Runnable... tasks) {
        return syncTimer(0L, repeat, tasks);
    }

    public static BukkitTask syncLater(Runnable task, long delay) {
        return SCHEDULER.runTaskLater(plugin, task::run, delay);
    }

    public static List<BukkitTask> syncLater(long delay, Runnable... tasks) {
        List<BukkitTask> result = new ArrayList<>();
        Arrays.stream(tasks).forEach(task -> result.add(syncLater(task, delay)));

        return result;
    }

    // SYNC - CONSUMER
    public static void sync(Consumer<BukkitTask> task) {
        SCHEDULER.runTask(plugin, task);
    }

    @SafeVarargs
    public static void sync(Consumer<BukkitTask>... tasks) {
        Arrays.stream(tasks).forEach(Tasker::sync);
    }

    public static void syncTimer(Consumer<BukkitTask> task, long delay, long repeat) {
        SCHEDULER.runTaskTimer(plugin, task, delay, repeat);
    }

    public static void syncTimer(Consumer<BukkitTask> task, long repeat) {
        syncTimer(task, 0L, repeat);
    }

    @SafeVarargs
    public static void syncTimer(long delay, long repeat, Consumer<BukkitTask>... tasks) {
        Arrays.stream(tasks).forEach(task -> syncTimer(task, delay, repeat));
    }

    @SafeVarargs
    public static void syncTimer(long repeat, Consumer<BukkitTask>... tasks) {
        syncTimer(0L, repeat, tasks);
    }

    public static void syncLater(Consumer<BukkitTask> task, long delay) {
        SCHEDULER.runTaskLater(plugin, task, delay);
    }

    @SafeVarargs
    public static void syncLater(long delay, Consumer<BukkitTask>... tasks) {
        Arrays.stream(tasks).forEach(task -> syncLater(task, delay));
    }

    // ASYNC
    public static BukkitTask async(Runnable task) {
        return SCHEDULER.runTaskAsynchronously(plugin, task::run);
    }

    public static List<BukkitTask> async(Runnable... tasks) {
        List<BukkitTask> result = new ArrayList<>();
        Arrays.stream(tasks).forEach(task -> result.add(async(task)));

        return result;
    }

    public static BukkitTask asyncTimer(Runnable task, long delay, long repeat) {
        return SCHEDULER.runTaskTimerAsynchronously(plugin, task::run, delay, repeat);
    }

    public static BukkitTask asyncTimer(Runnable task, long repeat) {
        return asyncTimer(task, 0L, repeat);
    }

    public static List<BukkitTask> asyncTimer(long delay, long repeat, Runnable... tasks) {
        List<BukkitTask> result = new ArrayList<>();
        Arrays.stream(tasks).forEach(task -> result.add(asyncTimer(task, delay, repeat)));

        return result;
    }

    public static List<BukkitTask> asyncTimer(long repeat, Runnable... tasks) {
        return asyncTimer(0L, repeat, tasks);
    }

    public static BukkitTask asyncLater(Runnable task, long delay) {
        return SCHEDULER.runTaskLaterAsynchronously(plugin, task, delay);
    }

    public static List<BukkitTask> asyncLater(long delay, Runnable... tasks) {
        List<BukkitTask> result = new ArrayList<>();
        Arrays.stream(tasks).forEach(task -> result.add(asyncLater(task, delay)));

        return result;
    }

    // ASYNC - CONSUMER
    public static void async(Consumer<BukkitTask> task) {
        SCHEDULER.runTaskAsynchronously(plugin, task);
    }

    @SafeVarargs
    public static void async(Consumer<BukkitTask>... tasks) {
        Arrays.stream(tasks).forEach(Tasker::async);
    }

    public static void asyncTimer(Consumer<BukkitTask> task, long delay, long repeat) {
        SCHEDULER.runTaskTimerAsynchronously(plugin, task, delay, repeat);
    }

    public static void asyncTimer(Consumer<BukkitTask> task, long repeat) {
        asyncTimer(task, 0L, repeat);
    }

    @SafeVarargs
    public static void asyncTimer(long delay, long repeat, Consumer<BukkitTask>... tasks) {
        Arrays.stream(tasks).forEach(task -> asyncTimer(task, delay, repeat));
    }

    @SafeVarargs
    public static void asyncTimer(long repeat, Consumer<BukkitTask>... tasks) {
        asyncTimer(0L, repeat, tasks);
    }

    public static void asyncLater(Consumer<BukkitTask> task, long delay) {
        SCHEDULER.runTaskLaterAsynchronously(plugin, task, delay);
    }

    @SafeVarargs
    public static void asyncLater(long delay, Consumer<BukkitTask>... tasks) {
        Arrays.stream(tasks).forEach(task -> asyncLater(task, delay));
    }

    public static void stopAll() {
        SCHEDULER.cancelTasks(plugin);
    }

}