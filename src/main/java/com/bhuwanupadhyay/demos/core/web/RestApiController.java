package com.bhuwanupadhyay.demos.core.web;


import com.bhuwanupadhyay.demos.core.model.*;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("preview")
@RestController
public class RestApiController implements IRestApi {

    private static final Logger log = LoggerFactory.getLogger(RestApiController.class);

    private final DefaultService entityService;

    public RestApiController(DefaultService entityService) {
        this.entityService = entityService;
    }

    @Override
    public ResponseEntity<Void> create(EntityProperties properties) {
        log.atDebug().log(STR."Creating new entity information with number of properties: \{properties.size()}");
        EntityId entityId = entityService.create(properties);
        log.atDebug().log(STR."Entity information created with id: \{entityId.id()}");
        return ResponseEntity.created(URI.create(STR."/entity/\{entityId.id()}")).build();
    }

    @Override
    public ResponseEntity<Void> patch(String entityId, EntityProperties properties) {
        log.atDebug().log(STR."Updating exsitng entity \{entityId} information with number of properties: \{properties.size()}");
        EntityId existingEntityId = new EntityId(entityId);
        entityService.update(existingEntityId, properties);
        log.atDebug().log(STR."Entity information updated with id: \{entityId}");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<EntityInfo> get(String entityId) {
        log.atDebug().log(STR."Retreiving exsitng entity \{entityId} information");
        EntityId existingEntityId = new EntityId(entityId);
        EntityInfo entityInfo = entityService.getById(existingEntityId);
        log.atDebug().log(STR."Entity information retreived with id: \{entityId}");
        return ResponseEntity.ok(entityInfo);
    }

    @Override
    public ResponseEntity<Void> delete(String entityId) {
        log.atDebug().log(STR."Deleting exsitng entity \{entityId} information");
        EntityId existingEntityId = new EntityId(entityId);
        entityService.deleteById(existingEntityId);
        log.atDebug().log(STR."Entity information deleted with id: \{entityId}");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PageResults<EntityInfo>> search(SearchQuery query) {
        log.atDebug().log(STR."Search entities with search query: \{query}");
        PageResults<EntityInfo> results = entityService.search(query);
        log.atDebug().log(STR."Entities searched with search query: \{query}");
        return ResponseEntity.ok(results);
    }


}