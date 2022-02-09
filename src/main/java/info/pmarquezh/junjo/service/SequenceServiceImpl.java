package info.pmarquezh.junjo.service;

//   Standard Libraries Imports
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//   Third Party Libraries Imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//   FENIX Framework Imports

//   Domain Imports
import info.pmarquezh.junjo.model.sequence.SequenceRec;
import info.pmarquezh.junjo.repository.SequenceRepository;


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
     * Retrieves a sequence by ID [R]
     * @param sequenceId
     * @return
     */
    @Override
    public SequenceRec retrieveSequence ( String sequenceId ) {

//        for ( SequenceRec seq : sequenceRepository.findAll ( ) ) {
//            System.out.println ( seq.getId ( ) + " - " + seq.getSequenceName ( ) );
//        }

        if ( sequenceRepository.existsById ( sequenceId ) ) {
            Optional<SequenceRec> sequenceWrapper = sequenceRepository.findById ( sequenceId );

            return sequenceWrapper.get ( );

        } else {
            return null;

        }

    }

    /**
     * Updates a sequence [U]
     *
     * @param sequenceId
     * @param sequence
     * @return SequenceRec The sequence record to update.
     */
    @Override
    public String updateSequence(String sequenceId, SequenceRec sequence) {

        SequenceRec dbSequence = this.retrieveSequence ( sequenceId );

        if ( dbSequence != null ) {

            dbSequence.setSequenceName ( sequence.getSequenceName ( ) );
            dbSequence.setFormat ( sequence.getFormat ( ) );
            dbSequence.setCurrentAlphaSequence ( sequence.getCurrentAlphaSequence ( ) );
            dbSequence.setCurrentNumericSequence ( sequence.getCurrentNumericSequence ( ) );
            dbSequence.setPriorityType ( sequence.getPriorityType ( ) );

            sequenceRepository.save ( dbSequence );

            return dbSequence.getId ( );

        } else {
            return null;

        }
    }

    /**
     * Deletes a sequence [D]
     *
     * @param sequenceId
     * @return SequenceRec The sequence record to delete or null if not found (null is only informative).
     */
    @Override
    public String deleteSequence ( String sequenceId ) {

        SequenceRec dbSequence = this.retrieveSequence ( sequenceId);

        if ( dbSequence != null ) {
            sequenceRepository.delete ( dbSequence );

            return dbSequence.getId ( );

        } else {
            return null;

        }

    }

//   WIP

    /**
     * Generates the next element in the sequence.
     *
     * @param sequenceId
     * @return String The generated element from a sequence or null (if sequenceId is not valid/found).
     */
    @Override
    public String getNextInSequence ( String sequenceId ) {

        SequenceRec sequence = this.retrieveSequence ( sequenceId );

        String s = sequence.getFormat ( );

        for ( SequenceRec seq : sequenceRepository.findAll ( ) ) {
            System.out.println ( seq.getId ( ) + " - " + seq.getSequenceName ( ) );
        }

        Pattern numericPattern = Pattern.compile ( "\\{[N]+\\}" );
        Pattern alphaPattern   = Pattern.compile ( "\\{[A]+\\}" );

        Matcher numericMatcher = numericPattern.matcher ( s );
        Matcher alphaMatcher   = alphaPattern.matcher ( s );

        while ( numericMatcher.find ( ) ) {
            String numericGroup = numericMatcher.group ( );
            System.out.println ( "NUMERIC GROUP: " + numericGroup );
        }

        while ( alphaMatcher.find ( ) ) {
            String alphaGroup = alphaMatcher.group ( );
            System.out.println ( "ALPHA GROUP: " + alphaGroup );
        }

        return s;
    }


    int startStringIdx = 18278;
    //int endStringIdx   = 18288;
    int endStringIdx   = 475253;

    int startingNum  = 1000;
    int endingNum    = 9999;

    int currentNum = startingNum;

    /**
     *
     * @param i
     * @return
     */
    String str (int i) {

        return i < 0 ? "" : str((i / 26) - 1) + (char)(65 + i % 26);
    }

    /**
     *
     * @param solicitud
     */
    void generateSkus ( int solicitud ) {

        System.out.println ( "QUIERO GENERAR: " + solicitud + " SKUs." );

        boolean done = false;
        int count = 0;

        for (int i = startStringIdx; i <= endStringIdx; i++) {
            if ( done ) break;

            String out = str ( i );

            for (int j = currentNum; j <= endingNum; j++) {
                count++;
//                System.out.println ( "     count: " + count + " => " + out + "-" + j );
                System.out.println ( out + "-" + j );
                if ( count >= solicitud ) {
                    done = true;
                    break;
                }
            }
        }

    }
//   WIP

}
