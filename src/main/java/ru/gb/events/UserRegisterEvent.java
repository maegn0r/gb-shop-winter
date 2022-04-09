package ru.gb.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.pojo.RegisterMessage;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterEvent implements Serializable{
    static final long serialVersionUID = -8863620718965821729L;
    private RegisterMessage registerMessage;
}

