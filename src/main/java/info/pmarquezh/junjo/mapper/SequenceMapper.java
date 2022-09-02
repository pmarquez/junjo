package info.pmarquezh.junjo.mapper;

//   Standard Libraries Imports

//   Third Party Libraries Imports
import lombok.extern.java.Log;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    private ModelMapper modelMapper;

    @Autowired
    public SequenceMapper ( ModelMapper modelMapper ) {
        this.modelMapper = modelMapper;
    }

    /**
     * Converts a validated DTO to an entity.
     * @param registrationDto
     * @return
     */
    public SequenceRec convertDtoToEntity ( SequenceDTO registrationDto ) {
        return modelMapper.map ( registrationDto, SequenceRec.class );
    }

    /**
     * Converts an entity to a DTO.
     * @param sequence
     * @return
     */
    public SequenceDTO convertEntityToDTO ( SequenceRec sequence ) {
        return modelMapper.map ( sequence, SequenceDTO.class );
    }

}
