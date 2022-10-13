package ru.practicum.app.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {


    @NotBlank(message = "не может быть пустым")
    private String name;

    @NotBlank(message = "не может быть пустым")
    @Email
    private String email;
}
