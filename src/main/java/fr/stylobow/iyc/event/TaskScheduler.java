package fr.stylobow.iyc.event;

import fr.stylobow.iyc.ImagineYourCraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@EventBusSubscriber(modid = ImagineYourCraft.MOD_ID)
public class TaskScheduler {

    private static class ScheduledTask {
        int ticksRemaining;
        final Runnable task;

        ScheduledTask(int delayTicks, Runnable task) {
            this.ticksRemaining = delayTicks;
            this.task = task;
        }
    }

    private static final List<ScheduledTask> TASKS = new ArrayList<>();

    public static void schedule(int delayTicks, Runnable task) {
        synchronized (TASKS) {
            TASKS.add(new ScheduledTask(delayTicks, task));
        }
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        synchronized (TASKS) {
            Iterator<ScheduledTask> iterator = TASKS.iterator();
            while (iterator.hasNext()) {
                ScheduledTask scheduledTask = iterator.next();
                scheduledTask.ticksRemaining--;

                if (scheduledTask.ticksRemaining <= 0) {
                    scheduledTask.task.run();
                    iterator.remove();
                }
            }
        }
    }
}