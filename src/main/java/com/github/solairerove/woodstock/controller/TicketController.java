package com.github.solairerove.woodstock.controller;

import com.github.solairerove.woodstock.domain.Ticket;
import com.github.solairerove.woodstock.dto.TicketDTO;
import com.github.solairerove.woodstock.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by krivitski-no on 9/28/16.
 */
@RestController
@ExposesResourceFor(Ticket.class)
@RequestMapping(value = "/api/tickets", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TicketController {

    private final TicketService ticketService;

    private final PagedResourcesAssembler<Ticket> assembler;

    private final EntityLinks entityLinks;

    @Autowired
    public TicketController(PagedResourcesAssembler<Ticket> assembler, TicketService ticketService, EntityLinks entityLinks) {
        this.assembler = assembler;
        this.ticketService = ticketService;
        this.entityLinks = entityLinks;
    }

    @RequestMapping
    public ResponseEntity getAllTickets(Pageable pageable) {
        return new ResponseEntity<>(assembler.toResource(ticketService.findAll(pageable)), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}")
    public ResponseEntity getTicket(@PathVariable String id) {
        Resource<Ticket> resource = new Resource<>(ticketService.get(id));
        resource.add(this.entityLinks.linkToSingleResource(Ticket.class, id));

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createTicket(@RequestBody TicketDTO ticketDTO) {
        return new ResponseEntity<>(ticketService.create(ticketDTO), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateTicket(@PathVariable String id, @RequestBody TicketDTO ticketDTO) {
        return new ResponseEntity<>(ticketService.update(id, ticketDTO), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTicket(@PathVariable String id) {
        return new ResponseEntity<>(ticketService.delete(id), HttpStatus.OK);
    }

    @RequestMapping(path = "/delete_all", method = RequestMethod.DELETE)
    public ResponseEntity deleteAll() {
        return new ResponseEntity<>(ticketService.deleteAll(), HttpStatus.ACCEPTED);
    }
}
