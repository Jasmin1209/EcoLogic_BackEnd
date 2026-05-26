package br.com.ifba.ecologic_back_end.exception;

/**
 * Exceção para regras de negócio violadas.
 * Exemplos: email duplicado, dados inválidos, etc.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String mensagem) {
        super(mensagem);
    }
}
