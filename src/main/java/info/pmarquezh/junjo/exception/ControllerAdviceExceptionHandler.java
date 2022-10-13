package info.pmarquezh.junjo.exception;

//   Standard Libraries Imports
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

//   Third Party Libraries Imports
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//   ns Framework Imports


//   Domain Imports


/**
 * ControllerAdviceExceptionHandler.java<br><br>
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
@Slf4j
@RestControllerAdvice
public class ControllerAdviceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler ( { NoSuchElementException.class } )
    public ResponseEntity<SingleErrorResponse> handleNoSuchElementException ( NoSuchElementException ex, WebRequest request ) {

        log.debug ( "Request  : " + request );
        log.debug ( "Exception: " + ex      );

        SingleErrorResponse response = handleFourOhFours ( request );

        return new ResponseEntity<> ( response, HttpStatus.NOT_FOUND );

    }

    @ExceptionHandler ( { EmptyResultDataAccessException.class } )
    public ResponseEntity<SingleErrorResponse> handleEmptyResultSetException ( EmptyResultDataAccessException ex, WebRequest request ) {

        log.debug ( "Request  : " + request );
        log.debug ( "Exception: " + ex      );

        SingleErrorResponse response = handleFourOhFours ( request );

        return new ResponseEntity<> ( response, HttpStatus.NOT_FOUND );

    }

    private SingleErrorResponse handleFourOhFours ( WebRequest request ) {

        SingleErrorResponse response = new SingleErrorResponse ( LocalDateTime.now ( ),
                "Not Found",
                404,
                ( (ServletWebRequest) request ).getRequest ( ).getRequestURI ( ),
                "Resource not found." );

        return response;

    }

    @ExceptionHandler ( Exception.class )
    public ResponseEntity<SingleErrorResponse> handleExceptions ( Exception ex, WebRequest request ) {

        log.debug ( "Request  : " + request );
        log.debug ( "Exception: " + ex      );

        SingleErrorResponse response = new SingleErrorResponse ( LocalDateTime.now ( ),
                "Internal Server Error",
                500,
                ( (ServletWebRequest) request ).getRequest ( ).getRequestURI ( ),
                "An generic internal exception has occurred, the support team has been notified, please try again later." );

        return new ResponseEntity<> ( response, HttpStatus.INTERNAL_SERVER_ERROR );

    }

    @ExceptionHandler ( RuntimeException.class )
    public ResponseEntity<SingleErrorResponse>  handleRuntimeExceptions ( RuntimeException ex, WebRequest request ) {

        log.debug ( "Request  : " + request );
        log.debug ( "Exception: " + ex      );

        SingleErrorResponse response = new SingleErrorResponse ( LocalDateTime.now ( ),
                "Internal Server Error",
                500,
                ( (ServletWebRequest) request ).getRequest ( ).getRequestURI ( ),
                "An generic internal runtime exception has occurred, the support team has been notified, please try again later." );

        return new ResponseEntity<> ( response, HttpStatus.INTERNAL_SERVER_ERROR );
    }

    /**
     * EXPERIMENTAL
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler ( {DataAccessException.class, DataIntegrityViolationException.class} )
    public ResponseEntity<SingleErrorResponse> handleDatabaseErrors ( Exception ex, WebRequest request ) {

        log.debug ( "Request  : " + request );
        log.debug ( "Exception: " + ex      );

        SingleErrorResponse response = new SingleErrorResponse ( LocalDateTime.now ( ),
                "Internal Server Error",
                500,
                ( (ServletWebRequest) request ).getRequest ( ).getRequestURI ( ),
                "An internal database error has occurred, the support team has been notified, please try again later." );

        return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid ( MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request ) {

        List<ErrorMessageDto> fieldErrorDetails     = ex.getBindingResult ( )
                                                        .getFieldErrors ( )
                                                        .stream ( )
                                                        .map ( error -> new ErrorMessageDto ( error.getObjectName ( ),
                                                                error.getField ( ),
                                                                error.getDefaultMessage ( ),
                                                                ObjectUtils.nullSafeToString ( error.getRejectedValue ( ) ) ) )
                                                        .toList ( );

        List<ErrorMessageDto> globalErrorDetails    = ex.getBindingResult ( )
                                                        .getGlobalErrors ( )
                                                        .stream ( )
                                                        .map ( error -> new ErrorMessageDto ( error.getObjectName ( ),
                                                                error.getDefaultMessage ( ) ) )
                                                        .toList ( );

        List<ErrorMessageDto> validationErrorDetails = new ArrayList<> ( );

        validationErrorDetails.addAll ( fieldErrorDetails );
        validationErrorDetails.addAll ( globalErrorDetails );

        ErrorResponse response = new ErrorResponse    ( LocalDateTime.now ( ),
                                                        status.name ( ),
                                                        status.value ( ),
                                                        ( (ServletWebRequest) request ).getRequest ( ).getRequestURI ( ),
                                                        validationErrorDetails );

        return new ResponseEntity<> ( response, status );

    }

}
