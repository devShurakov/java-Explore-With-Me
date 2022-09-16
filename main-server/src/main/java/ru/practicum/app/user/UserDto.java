package ru.practicum.app.user;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;

    @NotNull
//    @NotBlank(message = "{validation.field.audiometryTaskId.notNull}")
    private String name;

    @NotNull
//    @NotBlank(message = "{validation.field.audiometryTaskId.notNull}")
    private String email;
}
