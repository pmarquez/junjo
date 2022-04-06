package info.pmarquezh.junjo.model.sequence;

//   Standard Libraries Imports

//   Third Party Libraries Imports
import lombok.extern.java.Log;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//   ns Framework Imports

//   Domain Imports


/**
 * SequenceDTO.java<br><br>
 * Creation Date 2022-04-06 10:31<br><br>
 * <b>DESCRIPTION:</b><br><br>
 * <p></p>
 *
 * <PRE>
 * <table width="90%" border="1" cellpadding="3" cellspacing="2">
 * <tr><th colspan="2">   History   </th></tr>
 *
 * <tr>
 * <td width="20%">Version 1.0<br>
 * Version Date: 2022-04-06 10:31<br>
 *
 * @author pmarquezh </td>
 * <td width="80%"><p>Creation</p></td>
 * </tr>
 * </table>
 * </PRE>
 * @author pmarquezh
 * @version 1.0 - 2022-04-06 10:31
 */
@Log
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SequenceDTO {
    String id;
    String sequenceName;
    String pattern;
    int    currentNumericSequence;
    int    currentAlphaSequence;
    String currentAlphaRepresentation;
    String priorityType;
}