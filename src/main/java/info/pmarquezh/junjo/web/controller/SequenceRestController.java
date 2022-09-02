package info.pmarquezh.junjo.web.controller;

//   Standard Libraries Imports
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

//   Third Party Libraries Imports
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//   FENIX Framework Imports


//   Domain Imports
import info.pmarquezh.junjo.model.sequence.SequenceRec;
import info.pmarquezh.junjo.service.SequenceService;
import info.pmarquezh.junjo.model.sequence.SequenceDTO;

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
@Slf4j
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
    public ResponseEntity<Void> persistSequence ( @RequestBody @Valid SequenceDTO sequenceDTO ) {

        String newSequenceId = sequenceService.persistSequence ( sequenceDTO );
        HttpHeaders headers = new HttpHeaders ( );

        if ( !newSequenceId.equals( "" ) ) {
            headers.add ( "Location", newSequenceId );
            return new ResponseEntity<> ( headers, HttpStatus.CREATED );
        } else {
            return new ResponseEntity<> ( HttpStatus.BAD_REQUEST );
        }

    }

    /**
     * Retrieves all Sequences [R].
     * @return
     */
    @GetMapping( { "" } )
    public ResponseEntity<List<SequenceRec>> retrieveSequences ( ) {

        List<SequenceRec> sequences = sequenceService.retrieveSequences ( );

        if ( !sequences.isEmpty ( ) ) {
            return new ResponseEntity<> ( sequences, HttpStatus.OK );

        } else {
            return new ResponseEntity<> ( new ArrayList<> ( ), HttpStatus.NOT_FOUND );
        }

    }

    /**
     * Retrieves a Sequence [R].
     * @return
     */
    @GetMapping( { "/{sequenceId}" } )
    public ResponseEntity<SequenceRec> retrieveSequence ( @PathVariable ( "sequenceId" ) String sequenceId ) {

        SequenceRec sequence = sequenceService.retrieveSequence ( sequenceId );

        if ( sequence != null  ) {
            return new ResponseEntity<> ( sequence, HttpStatus.OK );
        } else {
            return new ResponseEntity<> ( HttpStatus.NOT_FOUND );
        }

    }

    /**
     * Updates a Sequence [U].
     * @return
     */
    @PutMapping( { "/{sequenceId}" } )
    public ResponseEntity<Void> updateSequence ( @PathVariable ( "sequenceId" ) String sequenceId, @RequestBody SequenceDTO sequenceDTO ) {

        int updateStatus = sequenceService.updateSequence ( sequenceId, sequenceDTO );

        switch ( updateStatus ) {
            case 204:
                return new ResponseEntity<> ( HttpStatus.NO_CONTENT );

            case 400:
                return new ResponseEntity<> ( HttpStatus.BAD_REQUEST );

            case 404:
                return new ResponseEntity<> ( HttpStatus.NOT_FOUND );

            default:
                return new ResponseEntity<> ( HttpStatus.I_AM_A_TEAPOT );
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
            return new ResponseEntity<> ( HttpStatus.NO_CONTENT );

        } else {
            return new ResponseEntity<> ( HttpStatus.NOT_FOUND ); //   CORRECT RESPONSE STATUS?

        }

    }

    /**
     * Generate next element in the sequence.
     * @return
     */
    @GetMapping( { "/{sequenceId}/generate" } )
    public ResponseEntity<String> generateNextElementInSequence ( @PathVariable ( "sequenceId" ) String sequenceId ) {

        String generatedElement = sequenceService.getNextInSequence ( sequenceId );

        if ( !generatedElement.isEmpty ( ) ) {

            return new ResponseEntity<> ( generatedElement, HttpStatus.OK );

        } else {
            return new ResponseEntity<> ( HttpStatus.NOT_FOUND );

        }
    }

    /**
     * Generate next "n" elements in the sequence.
     * @return
     */
    @GetMapping( { "/{sequenceId}/generate/{quantity}" } )
    public ResponseEntity<List<String>> generateNextElementsInSequence ( @PathVariable ( "sequenceId" ) String sequenceId, @PathVariable ( "quantity" ) int quantity  ) {

        List<String> generatedElements = sequenceService.getNextElementsInSequence ( sequenceId, quantity );

        if ( !generatedElements.isEmpty ( ) ) {

            return new ResponseEntity<> ( generatedElements, HttpStatus.OK );

        } else {
            return new ResponseEntity<> ( new ArrayList<> ( ), HttpStatus.NOT_FOUND );

        }

    }

}
