package info.pmarquezh.junjo.web.controller;

//   Standard Libraries Imports
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;

//   Third Party Libraries Imports
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Validated
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
                    headers.add ( "Location", newSequenceId );

        return new ResponseEntity<> ( headers, HttpStatus.CREATED );
    }

    /**
     * Retrieves all Sequences [R].
     * @return
     */
    @GetMapping( { "" } )
    public ResponseEntity<List<SequenceRec>> retrieveSequences ( ) {
        List<SequenceRec> sequences = sequenceService.retrieveSequences ( );
        return new ResponseEntity<> ( sequences, HttpStatus.OK );
    }

    /**
     * Retrieves a Sequence [R].
     * @return
     */
    @GetMapping( { "/{sequenceId}" } )
    public ResponseEntity<SequenceRec> retrieveSequence ( @PathVariable ( "sequenceId" ) String sequenceId ) {
        SequenceRec sequence = sequenceService.retrieveSequence ( sequenceId );
        return new ResponseEntity<> ( sequence, HttpStatus.OK );
    }

    /**
     * Updates a Sequence [U].
     * @return
     */
    @PutMapping( { "/{sequenceId}" } )
    public ResponseEntity<Void> updateSequence ( @PathVariable ( "sequenceId" ) String sequenceId, @RequestBody SequenceDTO sequenceDTO ) {
        sequenceService.updateSequence ( sequenceId, sequenceDTO );
        return new ResponseEntity<> ( HttpStatus.NO_CONTENT );
    }

    /**
     * Deletes a Sequence [D].
     * @return
     */
    @DeleteMapping( { "/{sequenceId}" } )
    public ResponseEntity<Void> deleteSequence ( @PathVariable ( "sequenceId" ) String sequenceId ) {
        sequenceService.deleteSequence ( sequenceId );
        return new ResponseEntity<> ( HttpStatus.NO_CONTENT );
    }

    /**
     * Generate next element in the sequence.
     * @return
     */
    @GetMapping( { "/{sequenceId}/generate" } )
    public ResponseEntity<String> generateNextElementInSequence ( @PathVariable ( "sequenceId" ) String sequenceId ) {
        String generatedElement = sequenceService.getNextInSequence ( sequenceId );
        return new ResponseEntity<> ( generatedElement, HttpStatus.OK );
    }

    /**
     * Generate next "n" elements in the sequence.
     * @return
     */
    @GetMapping( { "/{sequenceId}/generate/{quantity}" } )
    public ResponseEntity<List<String>> generateNextElementsInSequence ( @PathVariable ( "sequenceId" ) String sequenceId, @PathVariable ( "quantity" ) @Valid @Min ( 1 ) int quantity  ) {
        List<String> generatedElements = sequenceService.getNextElementsInSequence ( sequenceId, quantity );
        return new ResponseEntity<> ( generatedElements, HttpStatus.OK );
    }

}
