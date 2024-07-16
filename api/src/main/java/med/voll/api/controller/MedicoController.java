package med.voll.api.controller;


import med.voll.api.medico.DadosCadastroMedico;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @RestController classe que será rest
@RequestMapping("medicos") // vai mapear essa classe quando for chamar o endpoint medicos
public class MedicoController {

    //cadastro de médicos

//    @PostMapping // para enviar dados de requisição para api
//    public void cadastrar(@RequestBody  String json){ // RequestBody vai pegar do corpo da requisição da api e retornar na aplicação
//        System.out.println(json);
//
//    }

    // receber os campos separados
    @PostMapping // para enviar dados de requisição para api
    public void cadastrar(@RequestBody DadosCadastroMedico dados){ // RequestBody vai pegar do corpo da requisição da api e retornar na aplicação
        System.out.println(dados);

    }
}
