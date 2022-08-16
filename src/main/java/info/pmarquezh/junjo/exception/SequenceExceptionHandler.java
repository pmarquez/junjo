package info.pmarquezh.junjo.exception;

//   Standard Libraries Imports

import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

//   ns Framework Imports

//   Domain Imports


/**
 * SequenceExceptionHandler.java<br><br>
 * Creation Date 2022-06-04 12:50<br><br>
 * <b>DESCRIPTION:</b><br><br>
 * <p></p>
 *
 * <PRE>
 * <table width="90%" border="1" cellpadding="3" cellspacing="2">
 * <tr><th colspan="2">   History   </th></tr>
 *
 * <tr>
 * <td width="20%">Version 1.0<br>
 * Version Date: 2022-06-04 12:50<br>
 *
 * @author pmarquezh </td>
 * <td width="80%"><p>Creation</p></td>
 * </tr>
 * </table>
 * </PRE>
 * @author pmarquezh
 * @version 1.0 - 2022-06-04 12:50
 */
@Log
@RestControllerAdvice
public class SequenceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ErrorDetails handleExceptions(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails ( LocalDate.now ( ), ex.getMessage ( ), "Exception" );
        return errorDetails;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid ( MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request ) {

        List<ErrorMessageDto> validationErrorDetails = ex.getBindingResult ( )
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorMessageDto(error.getObjectName(),error.getField(),error.getDefaultMessage(),error.getRejectedValue().toString()))
                .collect(Collectors.toList());

        ErrorResponse response = new ErrorResponse(status.name(), status.value(),validationErrorDetails);
        return new ResponseEntity<>(response,status );

    }

}
