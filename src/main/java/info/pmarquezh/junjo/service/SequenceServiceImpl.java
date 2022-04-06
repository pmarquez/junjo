package info.pmarquezh.junjo.service;

//   Standard Libraries Imports
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//   Third Party Libraries Imports
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//   ns Framework Imports

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
@Log
@Service
public class SequenceServiceImpl implements SequenceService {

    private static final String PRIORITY_NUMERIC = "numeric";

    @Value("${info.pmarquezh.junjo.numericPattern}")
    private String      numericPatternString;
    @Value("${info.pmarquezh.junjo.alphaPattern}")
    private String      alphaPatternString;
    @Value("${info.pmarquezh.junjo.yearPattern}")
    private String      yearPatternString;
    @Value ( "${info.pmarquezh.junjo.defaultNumericPadChar}" )
    private String      defaultNumericPadChar;

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
     * Retrieves all sequences in storage [R]
     *
     * @return
     */
    @Override
    public List<SequenceRec> retrieveSequences ( ) {

        Iterable<SequenceRec> iSequences = sequenceRepository.findAll ( );

        List<SequenceRec> sequences = new ArrayList<> ( );

        iSequences.forEach ( sequences::add );

        return sequences;
    }

    /**
     * Retrieves a sequence by ID [R]
     * @param sequenceId
     * @return
     */
    @Override
    public SequenceRec retrieveSequence ( String sequenceId ) {

        Optional<SequenceRec> sequenceWrapper = sequenceRepository.findById ( sequenceId );

        return ( sequenceWrapper.isPresent ( ) ) ? sequenceWrapper.get ( ) : null;

    }

    /**
     * Updates a sequence [U]
     *
     * @param sequenceId
     * @param sequence
     * @return SequenceRec The sequence record to update.
     */
    @Override
    public String updateSequence ( String sequenceId, SequenceRec sequence ) {

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

    /*********************************************************************/
    /*********************************************************************/
    /******* ACTUAL FUNCTIONALITY - BEGIN ********************************/
    /*********************************************************************/
    /*********************************************************************/

    private SequenceRec sequence;
    private String      template;

    private boolean     numericRollover = false;

    /**
     * Generates the next element in the sequence.
     *
     * @param sequenceId
     * @return String The generated element from a sequence or null (if sequenceId is not valid/found).
     */
    @Override
    public String getNextInSequence ( String sequenceId ) {

        sequence = this.retrieveSequence ( sequenceId );
        template = sequence.getPattern( );

        //   YEAR PATTERN
        if ( sequence.getPriorityType ( ).equals ( PRIORITY_NUMERIC ) ) {
            template = this.retrieveNumericPattern ( );  //   NUMERIC PATTERN
            template = this.retrieveAlphaPattern ( );    //   ALPHA PATTERN

        } else {
            template = this.retrieveAlphaPattern ( );    //   ALPHA PATTERN
            template = this.retrieveNumericPattern ( );  //   NUMERIC PATTERN

        }
        template = this.retrieveYearPattern    ( );  //   YEAR PATTERN

        sequenceRepository.save ( sequence );

        return template;
    }

    /**
     * Generates the next elements in the sequence [D]
     *
     * @param sequenceId
     * @param quantity
     * @return List<String> The list of generated elements from a sequence or null (if sequenceId is not valid/found).
     */
    @Override
    public List<String> getNextElementsInSequence ( String sequenceId, int quantity ) {

        sequence = this.retrieveSequence ( sequenceId );

        List<String> elements = new ArrayList<> ( );

        if ( sequence.getPriorityType ( ).equals ( PRIORITY_NUMERIC ) ) {
            for (int i = 0; i < quantity; i++) {
                template = sequence.getPattern ( );
                template = this.retrieveNumericPattern ( );  //   NUMERIC PATTERN
                template = this.retrieveAlphaPattern   ( );  //   ALPHA PATTERN
                template = this.retrieveYearPattern    ( );  //   YEAR PATTERN

                elements.add ( template );

                numericRollover = false;
            }

        } else {
            for (int i = 0; i < quantity; i++) {
                template = sequence.getPattern( );
                template = this.retrieveAlphaPattern   ( );  //   ALPHA PATTERN
                template = this.retrieveNumericPattern ( );  //   NUMERIC PATTERN
                template = this.retrieveYearPattern    ( );  //   YEAR PATTERN

                elements.add ( template );

                numericRollover = false;
            }
        }

        sequenceRepository.save ( sequence );

        return elements;
    }

/*  NUMERIC GROUP - BEGIN */

    /**
     *
     * @return
     */
    private String retrieveNumericPattern ( ) {

        Pattern numericPattern = Pattern.compile ( numericPatternString );
        Matcher numericMatcher = numericPattern.matcher ( template );

        while ( numericMatcher.find ( ) ) {
            String numericGroup = numericMatcher.group ( );
            template = template.replace ( numericGroup, this.processNumericGroup ( numericGroup, true ) );
        }

        return template;
    }

    /**
     *
     * @param numericGroup
     * @return nextNumberStr
     */
    private String processNumericGroup ( String numericGroup, boolean increment ) {

        log.info ( "numericGroup: " + numericGroup );

        int maxDigitsAllowed = numericGroup.length ( ) - 2;

        int nextNumber =  ( increment ) ? ( sequence.getCurrentNumericSequence ( ) + 1 ) : sequence.getCurrentNumericSequence ( );
        if ( getDigitsCount ( nextNumber ) > maxDigitsAllowed ) {
            nextNumber = 1;
            numericRollover = true;
        }

        String nextNumberStr = Integer.toString ( nextNumber );
               nextNumberStr = StringUtils.leftPad ( nextNumberStr, maxDigitsAllowed, defaultNumericPadChar );

        sequence.setCurrentNumericSequence ( nextNumber );

        return nextNumberStr;

    }

    /*  NUMERIC GROUP - END */

    /*  ALPHA GROUP - BEGIN */

    /**
     *
     * @return
     */
    private String retrieveAlphaPattern ( ) {

        Pattern alphaPattern   = Pattern.compile ( alphaPatternString );
        Matcher alphaMatcher   = alphaPattern.matcher ( template );

        while ( alphaMatcher.find ( ) ) {
            String alphaGroup = alphaMatcher.group ( );
            template = template.replace ( alphaGroup, this.processAlphaGroup ( alphaGroup, numericRollover ) );
        }

        return template;
    }

    /**
     *
     * @param alphaGroup
     * @return
     */
    private String processAlphaGroup ( String alphaGroup, boolean increment ) {

        log.info ( "alphaGroup: " + alphaGroup );

        int currentAlphaSequence = 0;

        if ( sequence.getPriorityType ( ).equals ( PRIORITY_NUMERIC ) ) {
            currentAlphaSequence = ( increment ) ? sequence.getCurrentAlphaSequence ( ) + 1 : sequence.getCurrentAlphaSequence ( );

        } else {
            currentAlphaSequence = sequence.getCurrentAlphaSequence ( ) + 1;

        }

        sequence.setCurrentAlphaSequence ( currentAlphaSequence );

        return transformSequenceToRepresentation ( currentAlphaSequence );
    }

    /*  ALPHA GROUP - END */

    /*  YEAR GROUP - BEGIN */

    /**
     * Creates the necessary matcher to "match" a YEAR pattern in a sequence template.
     * @return
     */
    private String retrieveYearPattern ( ) {

        Pattern yearPattern    = Pattern.compile ( yearPatternString );
        Matcher yearMatcher   = yearPattern.matcher ( template );

        while ( yearMatcher.find ( ) ) {
            String yearGroup = yearMatcher.group ( );
            template = template.replace ( yearGroup, this.processYearGroup ( yearGroup ) );
        }

        return template;
    }

    /**
     * Returns current year as a String.
     * Either a LONG_YEAR (4 digits year, e.g.: 2022) or a SHORT_YEAR (2 digits year, e.g.: 22).
     * Defaults to LONG_YEAR.
     * @param yearGroup
     * @return
     */
    private String processYearGroup ( String yearGroup ) {
        log.info ( "yearGroup: " + yearGroup );

        String yearRepresentation = String.valueOf ( LocalDate.now ( ).getYear ( ) );

        if ( ( yearGroup.length ( ) - 2 ) == 2 ) {
            yearRepresentation = yearRepresentation.substring ( 2, 4 );
        }

        return yearRepresentation;
    }

    /*  YEAR GROUP - END */


    /**
     *
     * @param num
     * @return
     */
    private int getDigitsCount ( int num ) {
        String numAsString = Integer.toString ( num );
        return numAsString.length ( );
    }

    private static final int    CHAR_FOR_A            = 65;
    private static final int    NUM_CHARS_FROM_A_TO_Z = 26;
    private static final int    NUMBER_ONE            =  1;
    private static final String BLANK                 =  "";

    /**
     *
     * @param alphaSequence
     * @return
     */
    private String transformSequenceToRepresentation ( int alphaSequence ) {
        return alphaSequence < 0 ? BLANK : transformSequenceToRepresentation (( alphaSequence / NUM_CHARS_FROM_A_TO_Z ) - NUMBER_ONE ) + ( char ) ( CHAR_FOR_A + alphaSequence % NUM_CHARS_FROM_A_TO_Z );
    }

}
