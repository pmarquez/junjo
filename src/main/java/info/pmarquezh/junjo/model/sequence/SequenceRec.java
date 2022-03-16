package info.pmarquezh.junjo.model.sequence;

//   Standard Libraries Imports

//   Third Party Libraries Imports
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//   FENIX Framework Imports

//   Domain Imports


/**
 * SequenceRec.java<br><br>
 * Creation Date 2022-02-08 16:47<br><br>
 * <b>DESCRIPTION:</b><br><br>
 * <p></p>
 *
 * <PRE>
 * <table width="90%" border="1" cellpadding="3" cellspacing="2">
 * <tr><th colspan="2">   History   </th></tr>
 *
 * <tr>
 * <td width="20%">Version 1.0<br>
 * Version Date: 2022-02-08 16:47<br>
 *
 * @author pmarquezh </td>
 * <td width="80%"><p>Creation</p></td>
 * </tr>
 * </table>
 * </PRE>
 * @author pmarquezh
 * @version 1.0 - 2022-02-08 16:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document( collection = "sequences" )
public class SequenceRec {
    @Id
    String id;
    String sequenceName;
    String pattern;
    int    currentNumericSequence;
    int    currentAlphaSequence;
    String currentAlphaRepresentation;
    String priorityType;
}
