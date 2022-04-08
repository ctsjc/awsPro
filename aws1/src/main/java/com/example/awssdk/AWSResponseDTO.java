package com.example.awssdk;

import lombok.Data;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Data
@ToString
public class AWSResponseDTO {
    String status;
    String name;
    String timestamp;

    public void setTimestamp(Date creationDate) {
        Optional.ofNullable(creationDate).ifPresent(date -> {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            String strDate = formatter.format(creationDate);
            timestamp=strDate;
        });
    }
}
