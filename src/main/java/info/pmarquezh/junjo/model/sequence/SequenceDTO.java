package info.pmarquezh.junjo.model.sequence;

//   Standard Libraries Imports
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

//   Third Party Libraries Imports
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SequenceDTO {
    @NotBlank ( message = "{validation.sequenceName}" )
    String sequenceName;
    @NotBlank ( message = "{validation.pattern}" )
    String pattern;
    @Min( value = 0, message="{validation.currentNumericSequence}" )
    int    currentNumericSequence = 0;
    @Min( value = 0, message="{validation.currentAlphaSequence}" )
    int    currentAlphaSequence   = 0;
    String currentAlphaRepresentation = "";
    @NotBlank ( message = "{validation.priorityType}" )
    String priorityType;
}