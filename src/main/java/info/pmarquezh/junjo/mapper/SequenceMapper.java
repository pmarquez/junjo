package info.pmarquezh.junjo.mapper;

//   Standard Libraries Imports

//   Third Party Libraries Imports
import lombok.extern.java.Log;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

//   ns Framework Imports

//   Domain Imports
import info.pmarquezh.junjo.model.sequence.SequenceRec;
import info.pmarquezh.junjo.model.sequence.SequenceDTO;

/**
 * SequenceMapper.java<br><br>
 * Creation Date 2022-04-06 10:39<br><br>
 * <b>DESCRIPTION:</b><br><br>
 * <p></p>
 *
 * <PRE>
 * <table width="90%" border="1" cellpadding="3" cellspacing="2">
 * <tr><th colspan="2">   History   </th></tr>
 *
 * <tr>
 * <td width="20%">Version 1.0<br>
 * Version Date: 2022-04-06 10:39<br>
 *
 * @author pmarquezh </td>
 * <td width="80%"><p>Creation</p></td>
 * </tr>
 * </table>
 * </PRE>
 * @author pmarquezh
 * @version 1.0 - 2022-04-06 10:39
 */
@Log
@Data
@NoArgsConstructor
@Builder
@Component
public class SequenceMapper {

    public SequenceDTO toDto ( SequenceRec sequence ) {

        return SequenceDTO.builder ( ).sequenceName ( sequence.getSequenceName ( ) )
                                      .pattern ( sequence.getPattern ( ) )
                                      .currentNumericSequence ( sequence.getCurrentNumericSequence ( ) )
                                      .currentAlphaSequence ( sequence.getCurrentAlphaSequence ( ) )
                                      .priorityType ( sequence.getPriorityType ( ) )
                                      .currentAlphaRepresentation ( sequence.getCurrentAlphaRepresentation ( ) )
                                      .build ( );

    }

    public SequenceRec toSequence ( SequenceDTO sequenceDTO ) {

        return SequenceRec.builder ( ).sequenceName ( sequenceDTO.getSequenceName ( ) )
                                      .pattern ( sequenceDTO.getPattern ( ) )
                                      .currentNumericSequence ( sequenceDTO.getCurrentNumericSequence ( ) )
                                      .currentAlphaSequence ( sequenceDTO.getCurrentAlphaSequence ( ) )
                                      .priorityType ( sequenceDTO.getPriorityType ( ) )
                                      .currentAlphaRepresentation ( sequenceDTO.getCurrentAlphaRepresentation ( ) )
                                      .build ( );
    }

}
