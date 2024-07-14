package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// @JsonAlias usa- se para dar apelidos aos argumentos, para serem reconhecidos como são passados nos parametros da resposta da api
//@JsonIgnoreProperties ignora todos os demais argumentos não listados
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating")String avaliacao) {
}
