package info.pmarquezh.junjo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageDto {
    private String object;
    private String field;
    private String message;
    private Object rejectedValue;

    public ErrorMessageDto(String object, String field, String message) {
        this.object = object;
        this.field = field;
        this.message = message;
    }

    public ErrorMessageDto ( String object, String message ) {
        this.object = object;
        this.message = message;
    }

}
