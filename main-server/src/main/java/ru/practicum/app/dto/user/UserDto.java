package ru.practicum.app.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;

    @NotNull
    @NotBlank(message = "не может быть пустым")
    private String name;

    @NotNull
    @NotBlank(message = "не может быть пустым")
    @Email
    private String email;
}
