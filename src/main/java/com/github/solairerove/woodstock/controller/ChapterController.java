package com.github.solairerove.woodstock.controller;

import com.github.solairerove.woodstock.dto.ChapterDTO;
import com.github.solairerove.woodstock.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/references/{refId}/chapters", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ChapterController {

    private final ChapterService service;

    @Autowired
    public ChapterController(ChapterService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@PathVariable Long refId, @RequestBody ChapterDTO chapterDTO) {
        return new ResponseEntity<>(service.create(refId, chapterDTO), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{chapterId}")
    public ResponseEntity get(@PathVariable Long refId, @PathVariable Long chapterId) {
        return new ResponseEntity<>(service.get(refId, chapterId), HttpStatus.OK);
    }

    @RequestMapping
    public ResponseEntity getAll(@PathVariable Long refId) {
        return new ResponseEntity<>(service.getAll(refId), HttpStatus.OK);
    }
}