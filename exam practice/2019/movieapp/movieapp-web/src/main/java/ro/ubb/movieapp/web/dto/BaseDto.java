package ro.ubb.movieapp.web.dto;

import lombok.*;

import java.io.Serializable;

/**
 * Created by radu.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BaseDto implements Serializable {
    private Long id;
}
