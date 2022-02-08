package info.pmarquezh.junjo.repository;

//   Third party Imports
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * SequenceRepository.java<br><br>
 * Creation Date 2022-02-08 17:11<br><br>
 * <b>DESCRIPTION:</b><br><br>
 * <p>Class for Database Configuration, this class must be removed and use a safer way to implement DB connections.</p>
 * <p>Useful for prototyping and initial testing, when deciding what the final server will be, you know what to do..</p>
 *
 *<PRE>
 *<table width="90%" border="1" cellpadding="3" cellspacing="2">
 *<tr><th colspan="2">   History   </th></tr>
 *
 *<tr>
 *<td width="20%">Version 1.0<br>
 * Version Date: 2022-02-08 17:11<br>
 * @author Paulo Márquez Herrero</td>
 *<td width="80%"><p>Creation</p></td>
 *</tr>
 *</table>
 *</PRE>
 * @author Paulo Márquez Herrero
 * @version 1.0 - 2022-02-08 17:11
 */
@Configuration
@EnableMongoRepositories ( basePackages = "info.pmarquezh.junjo" )
public class SequenceRepository {

}
