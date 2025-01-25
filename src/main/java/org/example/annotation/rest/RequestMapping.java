package org.example.annotation.rest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** @RETENTION
 * A anotação @Retention define por quanto tempo a anotação personalizada estará disponivel.
 * RetentionPolicy.RUNTIME significa que a anotação estará disponível em tempo de execução.
 * RetentionPolicy.SOURCE significa que a anotação é descartada após o tempo de compilação
 * RetentionPolicy.CLASS significa que a anotação é armazenada no arquivo .class mas não carregada na JVM (não disponivel em tempo de execução)

 * @TARGET
 * A anotação @Target define onde a anotação personalizada pode ser aplicada
 * ElementType.method significa que a anotaçãso @RequestMapping só pode ser usadas em metodos
 * ElementType.type significa que é para classes, interfaces e enums.
 * ElementType.field significa que é para campos (variáveis de instância)
 * ElementType.parameter singifica que é para parâmetros de métodos
 * ElementType.constructor para construtores

 * @INTERFACE
 * É usado para criar uma anotação
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {

    String path() default "";
    String method() default "GET";
}
