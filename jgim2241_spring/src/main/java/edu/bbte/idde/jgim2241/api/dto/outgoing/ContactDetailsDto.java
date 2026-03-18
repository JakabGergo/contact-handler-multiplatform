package edu.bbte.idde.jgim2241.api.dto.outgoing;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class ContactDetailsDto extends ContactReduceDto {
    Date birthdate;
    String address;
}
