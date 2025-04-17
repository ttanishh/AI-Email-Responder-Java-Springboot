package com.chetan.email_writer.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class EmailRequest {
    private String emailContent;
    private String tone;
}
