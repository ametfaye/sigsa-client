package org.cages.moulinette.scheduler.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class ScheduledTasks {
        
	@Scheduled(cron = " 0 0 12 * * ?")
    public void scheduleTaskWithCronExpression() {	

    }
}
