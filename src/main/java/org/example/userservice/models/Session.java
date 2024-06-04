package org.example.userservice.models;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.example.userservice.models.enums.SessionStatus;

import java.util.Date;

@Setter
@Getter
@Entity
public class Session extends BaseModel {
    private String token;
    @ManyToOne
    private User user;
    private Date expiryDate;
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus sessionStatus;
}
