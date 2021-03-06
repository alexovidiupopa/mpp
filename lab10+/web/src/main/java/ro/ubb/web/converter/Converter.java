package ro.ubb.web.converter;

import ro.ubb.core.model.BaseEntity;
import ro.ubb.web.dto.BaseDto;

import java.io.Serializable;

/**
 * Created by radu.
 */

public interface Converter<Id extends Serializable, Model extends BaseEntity<Id>, Dto extends BaseDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}

