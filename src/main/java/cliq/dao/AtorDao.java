package cliq.dao;

import cliq.type.Ator;
import gate.base.Dao;
import gate.sql.condition.Condition;
import gate.sql.select.Select;
import gate.type.ID;
import java.util.List;
import java.util.Map;

public class AtorDao extends Dao
{

	public List<Map<String, Object>> search(String filter)
	{
		return Select.of(getClass().getResource("AtorDao/search(String).sql"))
			.where(Condition.of("sigla").eq(String.class, filter)
				.or("email").eq(String.class, filter)
				.or("nome").lk(filter))
			.build()
			.connect(getLink())
			.fetchMapList(ID.class, Ator.class, String.class, String.class, String.class);
	}
}
