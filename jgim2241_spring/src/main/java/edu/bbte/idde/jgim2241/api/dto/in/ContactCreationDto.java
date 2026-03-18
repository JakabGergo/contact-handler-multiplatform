package edu.bbte.idde.jgim2241.api.dto.in;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ContactCreationDto implements Serializable {
    @NotEmpty
    @Size(max = 256)
    String name;

    @NotEmpty
    @Size(max = 256)
    String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$") //+ is optional at the start
    String phoneNumber;

    @NotNull
    @Past
    Date birthdate;

    @Size(max = 512)
    String address;
}
