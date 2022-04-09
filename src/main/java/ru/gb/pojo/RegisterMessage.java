package ru.gb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterMessage {
    private String subject;
    private String mailTo;
    private String code;
}
