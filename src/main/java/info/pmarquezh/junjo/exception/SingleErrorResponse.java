package info.pmarquezh.junjo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleErrorResponse {
    private LocalDateTime           timestamp;
    private String                  httpStatusName;
    private int                     httpStatusCode;
    private String                  uri;
    private String                  message;
}