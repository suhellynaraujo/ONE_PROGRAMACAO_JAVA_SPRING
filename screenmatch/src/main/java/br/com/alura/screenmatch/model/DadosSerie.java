package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
// @JsonAlias usa- se para dar apelidos aos argumentos, para serem reconhecidos como são passados nos parametros da resposta da api
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating")String avaliacao) {
}
