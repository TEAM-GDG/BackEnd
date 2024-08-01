package com.ync.hackathon.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter @Setter
public class EmailFindResponse {

    private String message;
    private String email;
}
