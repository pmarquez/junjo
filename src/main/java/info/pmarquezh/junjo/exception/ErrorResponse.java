package info.pmarquezh.junjo.exception;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String                  httpStatusName;
    private int                     httpStatusCode;
    private String                  uri;
    private List<ErrorMessageDto>   errors;
}
