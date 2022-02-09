package info.pmarquezh.junjo.service;

//   Standard Libraries Imports

//   Third Party Libraries Imports

//   FENIX Framework Imports

//   Domain Imports

import info.pmarquezh.junjo.model.sequence.SequenceRec;

/**
 * SequenceService.java<br><br>
 * Creation Date 2022-02-08 16:57<br><br>
 * <b>DESCRIPTION:</b><br><br>
 * <p></p>
 *
 * <PRE>
 * <table width="90%" border="1" cellpadding="3" cellspacing="2">
 * <tr><th colspan="2">   History   </th></tr>
 *
 * <tr>
 * <td width="20%">Version 1.0<br>
 * Version Date: 2022-02-08 16:57<br>
 *
 * @author pmarquezh </td>
 * <td width="80%"><p>Creation</p></td>
 * </tr>
 * </table>
 * </PRE>
 * @author pmarquezh
 * @version 1.0 - 2022-02-08 16:57
 */
public interface SequenceService {

    /**
     * Persists a new Sequence [C].
     * @param sequence
     * @return
     */
    String persistSequence(SequenceRec sequence);

    /**
     * Retrieves a sequence [R]
     * @param sequenceId
     * @return SequenceRec The requested sequence record or null if not found.
     */
    SequenceRec retrieveSequence(String sequenceId);
}
