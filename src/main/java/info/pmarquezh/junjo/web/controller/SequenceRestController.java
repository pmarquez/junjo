package info.pmarquezh.junjo.web.controller;

//   Standard Libraries Imports
import java.util.List;

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
    @PostMapping( { "" } )
    public ResponseEntity<Void> persistSequence ( @RequestBody SequenceRec sequence ) {

//TODO ADD THE STATUSES WHERE CREATION WAS NOT POSSIBLE (Failed Validations, DB Errors, etc.)

        String newSequenceId = sequenceService.persistSequence(sequence);

        if (!newSequenceId.equals("")) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", newSequenceId);

            return new ResponseEntity(headers, HttpStatus.CREATED);

        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST); //   CORRECT RESPONSE STATUS?

        }
    }

    /**
     * Retrieves a new Sequence [R].
     * @return
     */
    @GetMapping( { "/{sequenceId}" } )
    public ResponseEntity<SequenceRec> retrieveSequence ( @PathVariable ( "sequenceId" ) String sequenceId ) {

        SequenceRec sequence = sequenceService.retrieveSequence ( sequenceId );

        if ( sequence != null  ) {
            return new ResponseEntity ( sequence, HttpStatus.OK );

        } else {
            return new ResponseEntity ( HttpStatus.NOT_FOUND ); //   CORRECT RESPONSE STATUS?

        }

    }

    /**
     * Updates a Sequence [U].
     * @return
     */
    @PutMapping( { "/{sequenceId}" } )
    public ResponseEntity<Void> updateSequence ( @PathVariable ( "sequenceId" ) String sequenceId, @RequestBody SequenceRec sequence ) {

        String updatedSequenceId = sequenceService.updateSequence ( sequenceId, sequence );

        if ( updatedSequenceId != null  ) {
            HttpHeaders headers = new HttpHeaders ( );
            headers.add("Location", updatedSequenceId );

            return new ResponseEntity ( HttpStatus.NO_CONTENT );

        } else {
            return new ResponseEntity ( HttpStatus.BAD_REQUEST ); //   CORRECT RESPONSE STATUS?

        }

    }

    /**
     * Deletes a Sequence [D].
     * @return
     */
    @DeleteMapping( { "/{sequenceId}" } )
    public ResponseEntity<Void> deleteSequence ( @PathVariable ( "sequenceId" ) String sequenceId ) {

        String deletedSequenceId = sequenceService.deleteSequence ( sequenceId );

        if ( deletedSequenceId != null  ) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Deleted", deletedSequenceId);

            return new ResponseEntity ( HttpStatus.NO_CONTENT );

        } else {
            return new ResponseEntity ( HttpStatus.NOT_FOUND ); //   CORRECT RESPONSE STATUS?

        }

    }

    /**
     * Generate next element in the sequence.
     * @return
     */
    @GetMapping( { "generate/{sequenceId}" } )
    public ResponseEntity<String> generateNextElementInSequence ( @PathVariable ( "sequenceId" ) String sequenceId ) {

        String generatedElement = sequenceService.getNextInSequence ( sequenceId );

        if ( generatedElement != null  ) {

            return new ResponseEntity ( generatedElement, HttpStatus.OK );

        } else {
            return new ResponseEntity ( HttpStatus.NOT_FOUND ); //   CORRECT RESPONSE STATUS?

        }

    }

    /**
     * Generate next "n" elements in the sequence.
     * @return
     */
    @GetMapping( { "generate/{sequenceId}/{quantity}" } )
    public ResponseEntity<List<String>> generateNextElementsInSequence ( @PathVariable ( "sequenceId" ) String sequenceId, @PathVariable ( "quantity" ) int quantity  ) {

        List<String> generatedElements = sequenceService.getNextElementsInSequence ( sequenceId, quantity );

        if ( generatedElements != null  ) {

            return new ResponseEntity ( generatedElements, HttpStatus.OK );

        } else {
            return new ResponseEntity ( HttpStatus.NOT_FOUND ); //   CORRECT RESPONSE STATUS?

        }

    }

}
