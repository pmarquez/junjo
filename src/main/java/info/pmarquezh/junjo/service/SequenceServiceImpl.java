package info.pmarquezh.junjo.service;

//   Standard Libraries Imports
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//   Third Party Libraries Imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${info.pmarquezh.junjo.numericPattern}")
    private String      numericPatternString;
    @Value("${info.pmarquezh.junjo.alphaPattern}")
    private String      alphaPatternString;

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
                                                         .pattern( sequence.getPattern( ) )
                                                         .currentNumericSequence ( sequence.getCurrentNumericSequence ( ) )
                                                         .currentAlphaSequence ( sequence.getCurrentAlphaSequence ( ) )
                                                         .priorityType ( sequence.getPriorityType ( ) )
                                                         .build ( );

        sequenceRepository.save ( newSequence );

        return newSequence.getId ( );

    }

    /**
     * Retrieves a sequence by ID [R]
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
            dbSequence.setPattern( sequence.getPattern( ) );
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

    /**
     * Generates the next element in the sequence.
     *
     * @param sequenceId
     * @return String The generated element from a sequence or null (if sequenceId is not valid/found).
     */
    @Override
    public String getNextInSequence ( String sequenceId ) {

        SequenceRec sequence = this.retrieveSequence ( sequenceId );

        String template = sequence.getPattern( );

        if ( sequence.getPriorityType ( ).equals ( "numeric" ) ) {
            template = this.retrieveNumericPattern ( sequence, template );  //   NUMERIC PATTERN
            template = this.retrieveAlphaPattern ( sequence, template );    //   ALPHA PATTERN

        } else {
            template = this.retrieveAlphaPattern ( sequence, template );    //   ALPHA PATTERN
            template = this.retrieveNumericPattern ( sequence, template );  //   NUMERIC PATTERN

        }

        sequenceRepository.save ( sequence );

        return template;
    }

    /**
     *
     * @param sequence
     * @param template
     */
    private String retrieveNumericPattern ( SequenceRec sequence, String template ) {

        Pattern numericPattern = Pattern.compile ( numericPatternString );
        Matcher numericMatcher = numericPattern.matcher ( template );

        while ( numericMatcher.find ( ) ) {
            String numericGroup = numericMatcher.group ( );
            template = template.replace ( numericGroup, this.processNumericGroup ( sequence, numericGroup, true ) );
        }

        return template;
    }

    /**
     *
     * @param sequence
     * @param template
     */
    private String retrieveAlphaPattern ( SequenceRec sequence, String template ) {

        Pattern alphaPattern   = Pattern.compile ( alphaPatternString );
        Matcher alphaMatcher   = alphaPattern.matcher ( template );

        while ( alphaMatcher.find ( ) ) {
            String alphaGroup = alphaMatcher.group ( );

            if ( sequence.getCurrentNumericSequence ( ) == 1 ) {
                template = template.replace ( alphaGroup, this.processAlphaGroup ( sequence, alphaGroup, true ) );
            } else {
                template = template.replace ( alphaGroup, this.processAlphaGroup ( sequence, alphaGroup, false ) );
            }

        }

        return template;
    }

    /**
     *
     * @param sequence
     * @param numericGroup
     * @return nextNumberStr
     */
    private String processNumericGroup ( SequenceRec sequence, String numericGroup, boolean increment ) {

        int maxDigitsAllowed = numericGroup.length ( ) - 2;

        int nextNumber =  ( increment ) ? ( sequence.getCurrentNumericSequence ( ) + 1 ) : sequence.getCurrentNumericSequence ( );
        if ( getDigitsCount ( nextNumber ) > maxDigitsAllowed ) { nextNumber = 1; }

        String nextNumberStr = Integer.toString ( nextNumber );
        while ( nextNumberStr.length ( ) < maxDigitsAllowed ) { nextNumberStr = "0" + nextNumberStr; }

        sequence.setCurrentNumericSequence ( nextNumber );

        return nextNumberStr;

    }

    /**
     *
     * @param sequence
     * @param alphaGroup
     * @return
     */
    private String processAlphaGroup ( SequenceRec sequence, String alphaGroup, boolean increment ) {

        String nextAlphaStr = "";

        int currentAlphaSequence = ( increment ) ? sequence.getCurrentAlphaSequence ( ) + 1 : sequence.getCurrentAlphaSequence ( );

        sequence.setCurrentAlphaSequence ( currentAlphaSequence );

        return transformSequenceToRepresentation ( currentAlphaSequence );
    }

    /**
     *
     * @param alphaSequence
     * @return
     */
    private String transformSequenceToRepresentation ( int alphaSequence ) {
        return alphaSequence < 0 ? "" : transformSequenceToRepresentation (( alphaSequence / 26 ) - 1 ) + ( char ) ( 65 + alphaSequence % 26 );
    }

    /**
     *
     * @param num
     * @return
     */
    private int getDigitsCount ( int num ) {
        String numAsString = Integer.toString ( num );
        return numAsString.length ( );
    }

}
