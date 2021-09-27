package com.dmdev.jdbc.starter;

import com.dmdev.jdbc.starter.dao.TicketDao;
import com.dmdev.jdbc.starter.dto.TicketFilter;
import com.dmdev.jdbc.starter.entity.Ticket;

import java.math.BigDecimal;

public class DaoRunner {
    public static void main(String[] args) {

     var ticket = TicketDao.getInstens().findById(12L);
        System.out.println(ticket);
    }

    private static void filterTest() {
        var ticketFilter    = new TicketFilter(2,2,null,"A2");
        var tickets   = TicketDao.getInstens().findAll(ticketFilter);
        System.out.println(tickets);
        //  saveTest();
    }

    private static void saveTest() {
        //  var ticketDao = TicketDao.getInstens();
        //  var deleteResult = ticketDao.delete(36L);
        // saveTest();
        //  System.out.println(deleteResult);

        var ticketDao = TicketDao.getInstens();
        var mayBeTicett = ticketDao.INSTANCE.findById(22L);
        System.out.println(mayBeTicett);

        mayBeTicett.ifPresent(ticket -> {
            ticket.setCost(BigDecimal.valueOf(188.88));
            ticketDao.update(ticket);
        });
    }

    private static void saveTest1() {
        var ticketDao = TicketDao.getInstens();

        var ticket = new Ticket();
        ticket.setPassengerNo("9182735");
        ticket.setPassengerName("Test Testov");
      //  ticket.setFlightId(3L);
        ticket.setSeatNo("F3");
        ticket.setCost(BigDecimal.TEN);

        var saveTicket = ticketDao.save(ticket);
        System.out.println(saveTicket);
    }
}
