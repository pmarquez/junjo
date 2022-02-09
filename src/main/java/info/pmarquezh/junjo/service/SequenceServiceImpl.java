package info.pmarquezh.junjo.service;

//   Standard Libraries Imports

//   Third Party Libraries Imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//   FENIX Framework Imports

//   Domain Imports
import info.pmarquezh.junjo.model.sequence.SequenceRec;
import info.pmarquezh.junjo.repository.SequenceRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

/**
 * SequenceServiceImpl.java<br><br>
 * Creation Date 2022-02-08 16:58<br><br>
 * <b>DESCRIPTION:</b><br><br>
 * <p></p>
 *
 * <PRE>
 * <table width="90%" border="1" cellpadding="3" cellspacing="2">
 * <tr><th colspan="2">   History   </th></tr>
 *
 * <tr>
 * <td width="20%">Version 1.0<br>
 * Version Date: 2022-02-08 16:58<br>
 *
 * @author pmarquezh </td>
 * <td width="80%"><p>Creation</p></td>
 * </tr>
 * </table>
 * </PRE>
 * @author pmarquezh
 * @version 1.0 - 2022-02-08 16:58
 */
@Service
public class SequenceServiceImpl implements SequenceService {

    private SequenceRepository sequenceRepository;

    @Autowired
    public SequenceServiceImpl ( SequenceRepository sequenceRepository ) {
        this.sequenceRepository = sequenceRepository;
    }

    /**
     * Persists a new Sequence [C].
     *
     * @param sequence
     * @return
     */
    @Override
    public String persistSequence(SequenceRec sequence) {
        SequenceRec newSequence = SequenceRec.builder ( ).id ( UUID.randomUUID ( ).toString ( ) )
                                                         .sequenceName ( sequence.getSequenceName ( ) )
                                                         .format ( sequence.getFormat ( ) )
                                                         .currentNumericSequence ( sequence.getCurrentNumericSequence ( ) )
                                                         .currentAlphaSequence ( sequence.getCurrentAlphaSequence ( ) )
                                                         .priorityType ( sequence.getPriorityType ( ) )
                                                         .build ( );

        sequenceRepository.save ( newSequence );

//        for ( SequenceRec seq : sequenceRepository.findAll ( ) ) {
//            System.out.println ( seq.getId ( ) + " - " + seq.getSequenceName ( ) );
//        }

        return newSequence.getId ( );

    }

    /**
     * Retrieves a sequence by ID
     * @param sequenceId
     * @return
     */
    @Override
    public SequenceRec retrieveSequence ( String sequenceId ) {

        for ( SequenceRec seq : sequenceRepository.findAll ( ) ) {
            System.out.println ( seq.getId ( ) + " - " + seq.getSequenceName ( ) );
        }

        if ( sequenceRepository.existsById ( sequenceId ) ) {
            Optional<SequenceRec> sequenceWrapper = sequenceRepository.findById ( sequenceId );

            return sequenceWrapper.get ( );

        } else {
            return null;

        }

    }

    /**
     * Deletes a sequence [R]
     *
     * @param sequenceId
     * @return SequenceRec The sequence record to delete or null if not found (null is only informative).
     */
    @Override
    public String deleteSequence(String sequenceId) {

        if ( sequenceRepository.existsById ( sequenceId ) ) {
            Optional<SequenceRec> sequenceWrapper = sequenceRepository.findById ( sequenceId );
                                                    sequenceRepository.delete ( sequenceWrapper.get ( ) );

            return sequenceWrapper.get ( ).getId ( );

        } else {
            return null;

        }
    }

}
