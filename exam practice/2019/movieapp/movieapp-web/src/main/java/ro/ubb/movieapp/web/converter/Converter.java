package ro.ubb.movieapp.web.converter;

import ro.ubb.movieapp.core.model.BaseEntity;
import ro.ubb.movieapp.web.dto.BaseDto;

/**
 * Created by radu.
 */

public interface Converter<Model extends BaseEntity<Long>, Dto extends BaseDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}

