package edu.bbte.idde.jgim2241.api.dto.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class NoteCreationDto {
    @NotEmpty
    @Size(max = 512)
    private String content;

    @NotNull
    private Date createdAt;
}
