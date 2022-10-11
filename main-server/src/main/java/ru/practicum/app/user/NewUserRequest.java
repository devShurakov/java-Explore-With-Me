package ru.practicum.app.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

//@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {

//    private Integer id;

    @NotBlank(message = "не может быть пустым")
    private String name;

    @NotBlank(message = "не может быть пустым")
    @Email
    private String email;
}
