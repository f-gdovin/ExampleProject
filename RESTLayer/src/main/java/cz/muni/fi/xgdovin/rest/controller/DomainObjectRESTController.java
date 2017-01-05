package cz.muni.fi.xgdovin.rest.controller;

import cz.muni.fi.xgdovin.dao.domain.DomainObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import cz.muni.fi.xgdovin.service.service.DomainObjectService;

import java.util.List;

@RestController
@RequestMapping("/domain")
public class DomainObjectRESTController<E extends DomainObject> {

    private final static Logger logger = LoggerFactory.getLogger(DomainObjectRESTController.class);

    @Autowired
    private DomainObjectService<E> domainObjectService;

    //-------------------Retrieve All Entities--------------------------------------------------------
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<E>> findAll() {

        List<E> resultList = domainObjectService.findAll();

        if(resultList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    //-------------------Retrieve Single Entity--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<E> get(@PathVariable("id") long id) {
        logger.debug("Fetching Entity with id " + id);
        E result = domainObjectService.findById(id);
        if (result == null) {
            logger.warn("Entity with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //-------------------Create an Entity --------------------------------------------------------
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody E entity, UriComponentsBuilder ucBuilder) {
        logger.debug("Creating Entity " + entity);

        E result = null;
        try {
            result = domainObjectService.create(entity);
        } catch (Exception ex) {
            logger.warn("Entity " + entity + " cannot be created. Reason: " + ex);
        }
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(result.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    //------------------- Update an Entity --------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<E> update(@PathVariable("id") long id, @RequestBody E entity) {
        logger.debug("Updating Entity " + id);

        E result = domainObjectService.update(entity);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //------------------- Delete an Entity --------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<E> delete(@PathVariable("id") long id) {
        logger.debug("Fetching & deleting Entity with id " + id);

        E entity = domainObjectService.findById(id);
        if (entity == null) {
            logger.warn("Unable to delete. Entity with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        domainObjectService.delete(entity);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}