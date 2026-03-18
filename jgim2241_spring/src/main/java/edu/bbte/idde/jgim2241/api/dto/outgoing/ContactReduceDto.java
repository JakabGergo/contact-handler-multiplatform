package edu.bbte.idde.jgim2241.api.dto.outgoing;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ContactReduceDto implements Serializable {
    Long id;
    String name;
    String email;
    String phoneNumber;
}
