package valejaco.crossfit.lahorie.scheduledTasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class seancesCreationTask {

    // @Scheduled(fixedDelay = 5000)
    // Tous les dimanches à 23h55
    // @Scheduled(cron = "23 55 * * 0")
    public void perform() throws Exception {
        System.out.println("Batch programmé pour tourner toutes les 5 !!! secondes");
    }

}
