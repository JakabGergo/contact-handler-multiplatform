package edu.bbte.idde.jgim2241.api.dto.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NoteUpdateDto {
    @NotEmpty
    @Size(max = 512)
    private String content;
}
