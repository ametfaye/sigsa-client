package sn.awi.pgca.business.service.impl;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;


public class notifMailer
{
    @Scheduled(cron="*/5 * * * * ?")
    public void demoServiceMethod()
    {
        System.out.println("Method executed at every 5 seconds. Current time is :: "+ new Date());
    }
}