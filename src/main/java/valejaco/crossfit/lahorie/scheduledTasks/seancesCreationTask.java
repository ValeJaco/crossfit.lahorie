package valejaco.crossfit.lahorie.scheduledTasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import valejaco.crossfit.lahorie.dao.PlanningRepository;
import valejaco.crossfit.lahorie.dao.SeancesRepository;
import valejaco.crossfit.lahorie.models.Planning;
import valejaco.crossfit.lahorie.models.Seance;
import valejaco.crossfit.lahorie.models.SeancePlanning;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Locale;

@Component
public class seancesCreationTask {

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private SeancesRepository seancesRepository;

    // @Scheduled(fixedDelay = 30000)
    // Tous les dimanches à 23h55
    @Scheduled(cron = "12 51 09 * * 7", zone = "Europe/Paris")
    public void perform() {

        // Récupération du planning actif
        Planning planning = planningRepository.findByIsActive(true);

        if (planning != null) {

            Calendar calendar = Calendar.getInstance(Locale.FRANCE);
            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
            int currentYear = calendar.get(Calendar.YEAR);

            calendar.clear();
            calendar.set(Calendar.YEAR, currentYear);
            calendar.set(Calendar.WEEK_OF_YEAR, weekOfYear );
            // premier jour de la semaine
            OffsetDateTime referenceDate = calendar.getTime().toInstant().atOffset(ZoneOffset.UTC).plusWeeks(planning.getPostponedWeekNumber());

            planning.getSeancesPlanning().forEach(seancePlanning -> {
                OffsetDateTime creationDate = this.getDateForCreation(referenceDate, seancePlanning);
                Seance seanceToCreate = new Seance();
                seanceToCreate.seancePlanningToSeance(seancePlanning);
                seanceToCreate.setStartDate(creationDate);
                System.out.println( "Création de la séance du : " + creationDate );
                seancesRepository.save(seanceToCreate);
            });
        }
    }

    private OffsetDateTime getDateForCreation(
            OffsetDateTime referenceDate,
            SeancePlanning seancePlanning
    ) {
        Long dayOfWeek = seancePlanning.getDayOfWeek();
        if( dayOfWeek == 0 )
            dayOfWeek = dayOfWeek + 6;
        else
            dayOfWeek = dayOfWeek - 1;

        OffsetDateTime currentDay = referenceDate.plusDays(dayOfWeek);

        // TODO find how ot reaplce deprecated method later
        currentDay = currentDay.plusHours(seancePlanning.getStartTime().getHours() );
        currentDay = currentDay.plusMinutes(seancePlanning.getStartTime().getMinutes() );

        return currentDay.withOffsetSameInstant(ZoneOffset.ofHours(1));
    }
}
