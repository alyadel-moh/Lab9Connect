package coding.ENUMS;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum STATE {
    // Enum to define valid states
    PENDING,
    CANCELLED,
    ACCEPTED,
    DECLINED
}
