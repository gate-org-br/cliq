package cliq;

import gate.error.AppException;

public class Validador
{

	public static final int MAX_TEXT_SIZE = 1024 * 1024;

	public static void validarDescricao(String descricao) throws AppException
	{
		if (descricao != null && descricao.length() > MAX_TEXT_SIZE)
			throw new AppException("Campos de descrição devem possuir no máximo 1 megabyte");
	}
}
