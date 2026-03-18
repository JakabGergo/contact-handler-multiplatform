package edu.bbte.idde.jgim2241.api.dto.outgoing;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class NoteResponseDto {
    Long id;
    String content;
    Date createdAt;
}
