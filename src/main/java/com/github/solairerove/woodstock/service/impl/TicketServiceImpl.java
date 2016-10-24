package com.github.solairerove.woodstock.service.impl;

import com.github.solairerove.woodstock.domain.Task;
import com.github.solairerove.woodstock.domain.Ticket;
import com.github.solairerove.woodstock.dto.TicketDTO;
import com.github.solairerove.woodstock.repository.TaskRepository;
import com.github.solairerove.woodstock.repository.TicketRepository;
import com.github.solairerove.woodstock.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    private final TaskRepository taskRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, TaskRepository taskRepository) {
        this.ticketRepository = ticketRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Ticket create(Long taskId, TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setValue(ticketDTO.getValue());
        ticket.setCreatedDate(LocalDateTime.now().toString());

        Task task = taskRepository.findOne(taskId);
        task.getTickets().add(ticket);
        task.setUpdatedDate(LocalDateTime.now().toString());
        taskRepository.save(task);

        return ticket;
    }

    @Override
    public Ticket get(Long taskId, Long ticketId) {
        return ticketRepository.getTicketThatHasInTaskFromId(taskId, ticketId);
    }

    @Override
    public Iterable<Ticket> getAll(Long taskId) {
        return ticketRepository.getTicketsThatHasInTaskFromId(taskId);
    }

    @Override
    public Ticket update(Long id, TicketDTO ticketDTO) {
        return null;
    }

    @Override
    public Ticket delete(Long taskId, Long ticketId) {
        Ticket ticket = ticketRepository.getTicketThatHasInTaskFromId(taskId, ticketId);
        ticketRepository.deleteTicketThatHasInTaskFromId(taskId, ticketId);
        return ticket;
    }

    @Override
    public Iterable<Ticket> deleteAll() {
        Iterable<Ticket> tickets = ticketRepository.findAll();
        ticketRepository.deleteAll();
        return tickets;
    }
}
