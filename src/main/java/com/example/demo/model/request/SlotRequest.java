package com.example.demo.model.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.convert.Jsr310Converters;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SlotRequest {
    private String nameSlot;

    private Time startTime;

    private Time endTime;

    private Date startDate;

    private Date endDate;
}
