package ru.practicum.app.user;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    long id;

    @NotNull
    @NotBlank(message = "{validation.field.audiometryTaskId.notNull}")
    String name;

    @NotNull
    @NotBlank(message = "{validation.field.audiometryTaskId.notNull}")
    String email;
}
