package ro.ubb.web.converter;

import ro.ubb.core.model.BaseEntity;
import ro.ubb.web.dto.BaseDto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by radu.
 */

public abstract class BaseConverter<Id extends Serializable,Model extends BaseEntity<Id>, Dto extends BaseDto>
        implements Converter<Id,Model, Dto> {


    public Set<Id> convertModelsToIDs(Set<Model> models) {
        return models.stream()
                .map(model -> model.getId())
                .collect(Collectors.toSet());
    }

    public Set<Id> convertDTOsToIDs(Set<Dto> dtos) {
        return dtos.stream()
                .map(dto -> (Id)dto.getId())
                .collect(Collectors.toSet());
    }

    public Set<Dto> convertModelsToDtos(Collection<Model> models) {
        return models.stream()
                .map(model -> convertModelToDto(model))
                .collect(Collectors.toSet());
    }
}
