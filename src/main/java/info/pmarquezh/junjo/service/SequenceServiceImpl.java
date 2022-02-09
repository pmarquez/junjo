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
                                                         .format ( sequence.getFormat ( ) )
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

    /**
     * Generates the next element in the sequence.
     *
     * @param sequenceId
     * @return String The generated element from a sequence or null (if sequenceId is not valid/found).
     */
    @Override
    public String getNextInSequence ( String sequenceId ) {

        SequenceRec sequence = this.retrieveSequence ( sequenceId );

        String seqElementFormat = sequence.getFormat ( );

        if ( sequence.getPriorityType ( ).equals ( "numeric" ) ) {
            //   NUMERIC PATTERN
            Pattern numericPattern = Pattern.compile ( numericPatternString );
            Matcher numericMatcher = numericPattern.matcher ( seqElementFormat );

            while ( numericMatcher.find ( ) ) {
                String numericGroup = numericMatcher.group ( );
                seqElementFormat = seqElementFormat.replace ( numericGroup, this.processNumericGroup ( sequence, numericGroup, true ) );
            }

            //   ALPHA PATTERN
            Pattern alphaPattern   = Pattern.compile ( alphaPatternString );
            Matcher alphaMatcher   = alphaPattern.matcher ( seqElementFormat );

            while ( alphaMatcher.find ( ) ) {
                String alphaGroup = alphaMatcher.group ( );

                if ( sequence.getCurrentNumericSequence ( ) == 1 ) {
                    seqElementFormat = seqElementFormat.replace ( alphaGroup, this.processAlphaGroup ( sequence, alphaGroup, true ) );
                } else {
                    seqElementFormat = seqElementFormat.replace ( alphaGroup, this.processAlphaGroup ( sequence, alphaGroup, false ) );
                }

            }

        } else {
            //   ALPHA PATTERN
            Pattern alphaPattern   = Pattern.compile ( alphaPatternString );
            Matcher alphaMatcher   = alphaPattern.matcher ( seqElementFormat );

            while ( alphaMatcher.find ( ) ) {
                String alphaGroup = alphaMatcher.group ( );
                seqElementFormat = seqElementFormat.replace ( alphaGroup, this.processAlphaGroup ( sequence, alphaGroup, true ) );
            }

            //TODO   FINISH THIS
            if ( true ) {
                //   NUMERIC PATTERN
                Pattern numericPattern = Pattern.compile ( numericPatternString );
                Matcher numericMatcher = numericPattern.matcher ( seqElementFormat );

                while ( numericMatcher.find ( ) ) {
                    String numericGroup = numericMatcher.group ( );
                    seqElementFormat = seqElementFormat.replace ( numericGroup, this.processNumericGroup ( sequence, numericGroup, false ) );
                }
            }
        }

        sequenceRepository.save ( sequence );

        return seqElementFormat;
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
        return alphaSequence < 0 ? "" : str((alphaSequence / 26) - 1) + (char)(65 + alphaSequence % 26);

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

}
