package com.ourtimesheet.common;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by hassan on 3/31/16.
 */
public abstract class Entity implements Serializable {

    @Id
    private final UUID id;

    public Entity(UUID id) {
        this.id = id;
    }

    public Entity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
}