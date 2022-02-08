package info.pmarquezh.junjo.web.controller;

//   Standard Libraries Imports

//   Third Party Libraries Imports
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//   FENIX Framework Imports

//   Domain Imports
import info.pmarquezh.junjo.model.sequence.SequenceRec;
import info.pmarquezh.junjo.service.SequenceService;


/**
 * SequenceRestController.java<br><br>
 * Creation Date 2022-02-08 16:55<br><br>
 * <b>DESCRIPTION:</b><br><br>
 * <p></p>
 * <PRE>
 * <table width="90%" border="1" cellpadding="3" cellspacing="2">
 * <tr>
 *     <th colspan="2">
 *         History
 *     </th>
 * </tr>
 * <tr>
 *     <td width="20%">Version 1.0<br>
 *         Version Date: 2022-02-08 16:55<br>
 *         @author pmarquezh
 *     </td>
 *     <td width="80%">
 *         <p>Creation</p>
 *     </td>
 * </tr>
 * </table>
 * </PRE>
 * @author pmarquezh
 * @version 1.0 - 2022-02-08 16:55
 */
@Log
@RestController
@RequestMapping( "/junjoAPI/1.0/sequences" )
public class SequenceRestController {

    private SequenceService sequenceService;

    @Autowired
    public void setSequenceRestService ( SequenceService sequenceService ) {
        this.sequenceService = sequenceService;
    }

    /**
     * Persists a new Sequence [C].
     * @return
     */
    @PostMapping( { "/" } )
    public ResponseEntity<Void> persistDocument (@RequestBody SequenceRec sequence ) {

//TODO ADD THE STATUSES WHERE CREATION WAS NOT POSSIBLE (Failed Validations, DB Errors, etc.)

        String newSequenceId = sequenceService.persistSequence ( sequence );

        if ( !newSequenceId.equals ( "" )  ) {
            HttpHeaders headers = new HttpHeaders();
                        headers.add("Location", newSequenceId );

            return new ResponseEntity ( headers, HttpStatus.CREATED );

        } else {
            return new ResponseEntity ( HttpStatus.BAD_REQUEST ); //   CORRECT RESPONSE STATUS?

        }

    }

}
